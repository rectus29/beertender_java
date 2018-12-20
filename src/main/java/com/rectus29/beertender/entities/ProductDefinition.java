package com.rectus29.beertender.entities;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 08/06/2016 19:59                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.entities.search.ISearchable;
import org.hibernate.search.annotations.Field;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productdefinition")
public class ProductDefinition extends BasicGenericEntity<ProductDefinition> implements ISearchable {

	private String name;

	@Field(name = "description")
	@Column(columnDefinition = "MEDIUMTEXT")
	private String description;

	public ProductDefinition() {
	}

	public String getName() {
		return name;
	}

	public ProductDefinition setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(ProductDefinition object) {
		return defaultCompareTo(object);
	}
}
