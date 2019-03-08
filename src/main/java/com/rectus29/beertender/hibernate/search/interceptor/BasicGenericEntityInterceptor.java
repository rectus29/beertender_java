package com.rectus29.beertender.hibernate.search.interceptor;

import com.rectus29.beertender.entities.BasicGenericEntity;
import com.rectus29.beertender.enums.State;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 28/01/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BasicGenericEntityInterceptor implements EntityIndexingInterceptor<BasicGenericEntity> {

	@Override
	public IndexingOverride onAdd(BasicGenericEntity entity) {
		if (entity.getState() == State.ENABLE) {
			return IndexingOverride.APPLY_DEFAULT;
		}
		return IndexingOverride.SKIP;
	}

	@Override
	public IndexingOverride onUpdate(BasicGenericEntity entity) {
		if (entity.getState() == State.ENABLE) {
			return IndexingOverride.UPDATE;
		}
		return IndexingOverride.REMOVE;
	}

	@Override
	public IndexingOverride onDelete(BasicGenericEntity entity) {
		return IndexingOverride.APPLY_DEFAULT;
	}

	@Override
	public IndexingOverride onCollectionUpdate(BasicGenericEntity entity) {
		return onUpdate(entity);
	}
}
