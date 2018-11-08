package com.rectus29.beertender.web.component.BSPagingNavigator;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 08/11/2018 13:06               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BSPagingNavigator extends PagingNavigator {

	public BSPagingNavigator(String id, IPageable pageable) {
		super(id, pageable);
	}

	public BSPagingNavigator(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
		super(id, pageable, labelProvider);
	}


}
