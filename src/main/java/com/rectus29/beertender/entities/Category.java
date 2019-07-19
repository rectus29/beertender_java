package com.rectus29.beertender.entities;

/*-----------------------------------------------------*/
/*                        Rectus29                     */
/*                Date: 13/06/2018 17:10               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.search.ISearchable;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
//@Indexed
@Table(name = "category", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class Category extends BasicGenericEntity<Category> implements ISearchable {

	@Field
	private String name;
    private String shortName;
	@Field
	@Column(columnDefinition = "MEDIUMTEXT")
	private String description;
    @ManyToMany(targetEntity = Product.class, mappedBy = "categoryList")
    private List<Product> productList = new ArrayList<>();
	@Column(nullable = false)
    private Boolean isRoot = false;
	@Column(nullable = false)
	private int seqOrder = 0;


    public Category() {
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getSeqOrder() {
		return seqOrder;
	}

	public Category setSeqOrder(int seqOrder) {
		this.seqOrder = seqOrder;
		return this;
	}

	@Override
	public int compareTo(Category object) {
		return defaultCompareTo(object);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category category = (Category) o;
		return seqOrder == category.seqOrder &&
				Objects.equals(name, category.name) &&
				Objects.equals(shortName, category.shortName) &&
				Objects.equals(description, category.description) &&
				Objects.equals(productList, category.productList) &&
				Objects.equals(isRoot, category.isRoot);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, shortName, description, productList, isRoot, seqOrder);
	}
}
