package com.rectus29.beertender.entities;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 13/06/2018 17:10                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.enums.State;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends GenericEntity<Category>{

    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @ManyToMany
    private List<Product> productList = new ArrayList<>();

    private Boolean isRoot = false;

    private State state = State.ENABLE;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

	@Override
	public int compareTo(Category object) {
		return defaultCompareTo(object);
	}
}
