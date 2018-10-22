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

import com.rectus29.beertender.enums.State;
import org.hibernate.type.BlobType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends GenericEntity<Product> {

	@ManyToOne
	private ProductDefinition productDefinition;

	@Column(precision = 12, scale = 3)
	private BigDecimal price = BigDecimal.ZERO;

	@ManyToOne
	private Packaging packaging;

	@ManyToMany(mappedBy = "productList")
	private List<Category> categoryList = new ArrayList<>();

	@Lob
	@Column(columnDefinition = "BLOB")
	private byte[] fileImage;


	private State state = State.ENABLE;

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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName(){
		return this.getProductDefinition().getName();
	}

	public String getDescription(){
		return this.getProductDefinition().getDescription();
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public byte[] getFileImage() {
		return fileImage;
	}

	public void setFileImage(byte[] fileImage) {
		this.fileImage = fileImage;
	}

	@Override
	public int compareTo(Product object) {
		return defaultCompareTo(object);
	}
}
