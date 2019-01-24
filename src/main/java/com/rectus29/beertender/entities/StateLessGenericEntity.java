package com.rectus29.beertender.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/11/2018 13:46               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@MappedSuperclass
public abstract class StateLessGenericEntity<T> extends GenericEntity<T>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
