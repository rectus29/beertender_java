package com.rectus29.beertender.service.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.rectus29.beertender.entities.search.ISearchable;
import com.rectus29.beertender.service.IserviceSearch;
import com.rectus29.beertender.tools.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.shiro.subject.Subject;
import org.hibernate.*;
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
	private static Multimap<Class, Field> indexedFieldByClassMap = LinkedListMultimap.create();
	private SessionFactory sessionFactory;

	public serviceSearch() {
		//create cache of the indexed file
		log.debug("create cache of searchable field");
		Reflections reflections = new Reflections("com.rectus29.beertender.entities");
		Set<Class<? extends ISearchable>> classes = reflections.getSubTypesOf(ISearchable.class);
		for (Class tempClass : classes) {
			for (Field tempField : tempClass.getDeclaredFields()) {
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

		List<ISearchable> result = null;
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());

		MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"id", "name", "description"}, new FrenchAnalyzer());
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
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, classToSearch);
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
			initialIndex(tempClass);
		}
	}

	public void initialIndex(Class<? extends ISearchable> classToIndex) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		fullTextSession.setFlushMode(FlushMode.MANUAL);
		fullTextSession.setCacheMode(CacheMode.IGNORE);

		fullTextSession.purgeAll(classToIndex);
		fullTextSession.getSearchFactory().optimize(classToIndex);

		ScrollableResults results = fullTextSession.createCriteria(classToIndex).scroll(ScrollMode.FORWARD_ONLY);

		//on processe les ressources par batch de 1000 pour ne pas dÃ©passer la mÃ©moire de la VM
		int index = 0;
		int BATCH_SIZE = 1000;
		while (results.next()) {
			index++;
			fullTextSession.index(results.get(0));
			if (index % BATCH_SIZE == 0) {
				fullTextSession.flushToIndexes();
				fullTextSession.clear();
			}
		}
		//ne pas oublier les derniers
		fullTextSession.flushToIndexes();
		fullTextSession.clear();
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
