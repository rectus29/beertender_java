package com.rectus29.beertender.entities;

import com.rectus29.beertender.enums.State;

import javax.persistence.*;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/11/2018 13:46               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@MappedSuperclass
public abstract class BasicGenericEntity<T> extends GenericEntity<T>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	protected State state = State.ENABLE;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public T setState(State state) {
		this.state = state;
		return (T) this;
	}

}
