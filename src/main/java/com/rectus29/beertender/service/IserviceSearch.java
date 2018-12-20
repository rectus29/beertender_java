package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.search.ISearchable;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.shiro.subject.Subject;

import java.util.List;

/*-----------------------------------------------------*/
/*                     rectus29                        */
/*                                                     */
/*                Date: 20/12/2018 15:09               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface IserviceSearch {

	public List<ISearchable> searchLucene(String searchPattern);

	public List<ISearchable> searchLucene(String searchPattern, Class<? extends ISearchable> classToSearch, Subject user) throws ParseException;

	public void initialIndex(Class<? extends ISearchable> classToIndex);

	public void initIndex();
}
