package com.rectus29.beertender.service.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.rectus29.beertender.entities.search.ISearchable;
import com.rectus29.beertender.hibernate.search.monitor.BeerTenderIndexingProgressMonitor;
import com.rectus29.beertender.service.IserviceSearch;
import com.rectus29.beertender.tools.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.shiro.subject.Subject;
import org.hibernate.CacheMode;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("serviceSearch")
public class serviceSearch implements IserviceSearch {

	private static final Logger log = LogManager.getLogger(serviceSearch.class);
	private Multimap<Class, Field> indexedFieldByClassMap = LinkedListMultimap.create();
	private SessionFactory sessionFactory;

	public serviceSearch() {
		//create cache of the indexed file
		log.debug("create cache of searchable field");
		Reflections reflections = new Reflections("com.rectus29.beertender.entities");
		Set<Class<? extends ISearchable>> classes = reflections.getSubTypesOf(ISearchable.class);
		for (Class tempClass : classes) {
			log.debug("find class " + tempClass.getSimpleName() + " to add to index cache");
			for (Field tempField : tempClass.getDeclaredFields()) {
				log.debug("add field " + tempField.getName() + " to index cache");
				if (tempField.isAnnotationPresent(org.hibernate.search.annotations.Field.class)) {
					this.indexedFieldByClassMap.put(tempClass, tempField);
				}
			}
		}
	}



	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<ISearchable> search(String searchPattern) {
		List<ISearchable> result = null;
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
			Set<String> allFieldsAsString = new HashSet<>();
			for (Field temp : this.indexedFieldByClassMap.values()) {
				allFieldsAsString.add(temp.getName());
			}
			MultiFieldQueryParser parser = new MultiFieldQueryParser(allFieldsAsString.toArray(new String[]{}), new FrenchAnalyzer());
//			QueryParser parser = new QueryParser("name", new FrenchAnalyzer());
			Query query;
			//Si on ne met pas de criteres, on renvoie tout
			if (StringUtils.isNotBlank(searchPattern)) {
				query = parser.parse(searchPattern);
			} else {
				query = new MatchAllDocsQuery();
			}
			FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query);
			result = fullTextQuery.list();
		} catch (ParseException e) {
			log.warn(e);

		}

		return result;
	}


	public List<ISearchable> search(String searchPattern, Class<? extends ISearchable> classToSearch, Subject user) throws ParseException {

		List<ISearchable> result;
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());

		List<String> fieldNameList = new ArrayList<>();
		for(Field temp : this.indexedFieldByClassMap.get(classToSearch)){
			fieldNameList.add(temp.getName());
		}

		MultiFieldQueryParser parser = new MultiFieldQueryParser(fieldNameList.toArray(new String[]{}), new FrenchAnalyzer());
		//MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_31, new String[]{"id", "name", "meta", "resourceType.name"}, new StandardAnalyzer(Version.LUCENE_31));
		Query query;

		//Si on ne met pas de criteres, on renvoie tout
		if (searchPattern != null)
			if (searchPattern.length() != 0)
				query = parser.parse(searchPattern);
			else
				query = new MatchAllDocsQuery();
		else
			query = new MatchAllDocsQuery();
		org.hibernate.query.Query hibQuery = fullTextSession.createFullTextQuery(query, classToSearch);
		result = hibQuery.list();

		return result;

	}

	public void initIndex() {
		Reflections reflections = new Reflections("com.rectus29.beertender.entities");
		Set<Class<? extends ISearchable>> classes = reflections.getSubTypesOf(ISearchable.class);
		this.indexedFieldByClassMap.clear();
		for (Class tempClass : classes) {
			//for each searchable class retrieve annotated field
			for(Field temp : tempClass.getDeclaredFields()){
				if(temp.isAnnotationPresent(org.hibernate.search.annotations.Field.class)){
					this.indexedFieldByClassMap.put(tempClass, temp);
				}
			}
			try {
				initialIndex(tempClass);
			} catch (InterruptedException e) {
				log.error("Error while init lucene index", e);
			}
		}
	}

	public void initialIndex(Class<? extends ISearchable> classToIndex) throws InterruptedException {
		//purge the index for the given entity type
		purgeIndexFor(classToIndex);
		//reload entity to index
			FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
			fullTextSession
				.createIndexer(classToIndex)
				.batchSizeToLoadObjects(25)
				.cacheMode(CacheMode.NORMAL)
				.threadsToLoadObjects(12)
				.idFetchSize(150)
				.progressMonitor(new BeerTenderIndexingProgressMonitor())
				.startAndWait();
		//optimize index
		optimizeIndex(classToIndex);
		fullTextSession.clear();
	}

	private void purgeIndexFor(Class<? extends ISearchable> classToIndex) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		Transaction tx = fullTextSession.beginTransaction();
		fullTextSession.purgeAll(classToIndex);
		tx.commit();
	}

	public void optimizeIndex(Class<? extends ISearchable> classToIndex) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		fullTextSession.getSearchFactory().optimize(classToIndex);
	}

//	@Override
//	public List<Resource> searchLuceneResource(String searchPattern, Subject user) throws ParseException {
//		List<Resource> result = null;
//		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
//
//		DirectoryProvider[] directoryProviders = fullTextSession.getSearchFactory().getDirectoryProviders(Resource.class);
//		ReaderProvider readerProvider = fullTextSession.getSearchFactory().getReaderProvider();
//		IndexReader reader = readerProvider.openReader(directoryProviders[0]);
//		Collection<String> fieldNames = reader.getFieldNames(IndexReader.FieldOption.ALL);
//
//		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_31, fieldNames.toArray(new String[fieldNames.size()]), new FrenchAnalyzer(Version.LUCENE_31));
//		//MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_31, new String[]{"id", "name", "meta", "resourceType.name"}, new StandardAnalyzer(Version.LUCENE_31));
//		Query query;
//
//		//Si on ne met pas de criteres, on renvoie tout
//		if (searchPattern != null)
//			if (searchPattern.length() != 0)
//				query = parser.parse(searchPattern);
//			else
//				query = new MatchAllDocsQuery();
//		else
//			query = new MatchAllDocsQuery();
//		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Resource.class);
//		result = hibQuery.list();
//
//		return result;
//	}
//
//	@Override
//	public List<SearchHit> searchLuceneActivity(String searchPattern, Subject user) throws ParseException {
//		List<Activity> result = null;
//		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
//
//		DirectoryProvider[] directoryProviders = fullTextSession.getSearchFactory().getDirectoryProviders(Activity.class);
//		ReaderProvider readerProvider = fullTextSession.getSearchFactory().getReaderProvider();
//		IndexReader reader = readerProvider.openReader(directoryProviders[0]);
//		Collection<String> fieldNames = reader.getFieldNames(IndexReader.FieldOption.ALL);
//
//		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_31, fieldNames.toArray(new String[fieldNames.size()]), new FrenchAnalyzer(Version.LUCENE_31));
//		//MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_31, new String[]{"id", "name", "meta", "resourceType.name"}, new StandardAnalyzer(Version.LUCENE_31));
//		Query query;
//
//		//Si on ne met pas de criteres, on renvoie tout
//		if (searchPattern != null)
//			if (searchPattern.length() != 0)
//				query = parser.parse(searchPattern);
//			else
//				query = new MatchAllDocsQuery();
//		else
//			query = new MatchAllDocsQuery();
//		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Activity.class);
//		result = hibQuery.list();
//
//		/** Highlighter **/
//		QueryScorer scorer = new QueryScorer(query);
//		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>", "</b>");
//		Highlighter highlighter = new Highlighter(formatter, scorer);
//		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 100));
//
//
//		int maxNumFragmentsRequired = 3;
//		Analyzer analyzer = new FrenchAnalyzer(Version.LUCENE_31);
//
//		ArrayList<SearchHit> hits = new ArrayList<SearchHit>(result.size());
//
//		for (Activity a : this.filterActivityResultsByAuthorization(result, user)) {
//			String text = a.getCurrentRevision().getText().getText().replaceAll("<(/?)([A-Z][A-Z0-9]*)\\b[^>]*>", "");
//			TokenStream tokenStream = analyzer.tokenStream("description", new StringReader(text));
//
//			String res = null;
//			try {
//				res = highlighter.getBestFragments(tokenStream, text, maxNumFragmentsRequired, " ...");
//			} catch (IOException e) {
//				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//			} catch (InvalidTokenOffsetsException e) {
//				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//			}
//			assert res != null : "null result";
//			assert res.length() > 0 : "0 length result";
//			//log.debug(res);
//			hits.add(new SearchHit(a, res));
//
//		}
//
//		return hits;
//	}
}
