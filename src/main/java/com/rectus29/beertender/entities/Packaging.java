package com.rectus29.beertender.entities;

import com.rectus29.beertender.enums.State;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 05/09/2018 13:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "packaging")
public class Packaging extends GenericEntity<Packaging>{

	private String name;

	private String shortName;

	private Integer sortOrder = 0;

	@OneToMany(mappedBy = "packaging")
	private List<Product> productList = new ArrayList<>();

	private State state = State.ENABLE;

	public Packaging() {
	}

	public String getName() {
		return name;
	}

	public Packaging setName(String name) {
		this.name = name;
		return this;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public State getState() {
		return state;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int compareTo(Packaging object) {
		return defaultCompareTo(object);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Packaging packaging = (Packaging) o;
		return Objects.equals(name, packaging.name) &&
				Objects.equals(getId(), packaging.getId()) &&
				Objects.equals(shortName, packaging.shortName) &&
				Objects.equals(sortOrder, packaging.sortOrder) &&
				state == packaging.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, shortName, sortOrder, state);
	}
}
