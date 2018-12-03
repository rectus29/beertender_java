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

import com.rectus29.beertender.entities.translation.TranslatableEntity;
import com.rectus29.beertender.entities.translation.Translation;
import com.rectus29.beertender.enums.State;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "productdefinition")
public class ProductDefinition extends BasicGenericEntity<ProductDefinition>{

	private String name;

	@Column(columnDefinition = "MEDIUMTEXT")
	private String description;

	private State state = State.ENABLE;

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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int compareTo(ProductDefinition object) {
		return defaultCompareTo(object);
	}
}
