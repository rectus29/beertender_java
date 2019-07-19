package com.rectus29.beertender.entities;

/*-----------------------------------------------------*/
/*                      Rectus29                       */
/*                Date: 08/06/2016 19:59               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.entities.search.ISearchable;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

@Entity
//@Indexed(interceptor = BasicGenericEntityInterceptor.class)
@Analyzer(impl = FrenchAnalyzer.class)
@Table(name = "productdefinition", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class ProductDefinition extends BasicGenericEntity<ProductDefinition> implements ISearchable {

	@Field
	private String name;

	@Field
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductDefinition that = (ProductDefinition) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description);
	}
}
