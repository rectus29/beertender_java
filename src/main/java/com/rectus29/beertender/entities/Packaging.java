package com.rectus29.beertender.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 05/09/2018 13:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "packaging")
public class Packaging extends GenericEntity<Packaging>{

	private String name;

	@OneToMany(mappedBy = "packaging")
	private List<Product> productList = new ArrayList<>();

	public Packaging() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	@Override
	public int compareTo(Packaging object) {
		return defaultCompareTo(object);
	}
}
