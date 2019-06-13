package com.rectus29.beertender.entities;

/*-----------------------------------------------------*/
/*                        Rectus29                     */
/*                Date: 08/06/2016 19:59                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.resource.impl.ImageResource;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class Product extends BasicGenericEntity<Product> {

	@ManyToOne
	private ProductDefinition productDefinition;

	@Column(precision = 12, scale = 3)
	private BigDecimal price = BigDecimal.ZERO;

	@ManyToOne
	private Packaging packaging;

	@ManyToMany
	private List<Category> categoryList = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.ALL)
	private ImageResource fileImage;

	@Column(nullable = false)
	private int seqOrder = 0;

	public Product() {
		this.productDefinition = new ProductDefinition();
	}

	public ProductDefinition getProductDefinition() {
		return productDefinition;
	}

	public void setProductDefinition(ProductDefinition productDefinition) {
		this.productDefinition = productDefinition;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Packaging getPackaging() {
		return packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}

	public String getName() {
		return this.getProductDefinition().getName();
	}

	public String getDescription() {
		return this.getProductDefinition().getDescription();
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public ImageResource getFileImage() {
		return fileImage;
	}

	public void setFileImage(ImageResource fileImage) {
		this.fileImage = fileImage;
	}

	public int getSeqOrder() {
		return seqOrder;
	}

	public Product setSeqOrder(int seqOrder) {
		this.seqOrder = seqOrder;
		return this;
	}

	@Override
	public int compareTo(Product object) {
		return defaultCompareTo(object);
	}
}
