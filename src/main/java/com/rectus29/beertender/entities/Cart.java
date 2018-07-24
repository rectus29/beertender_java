package com.rectus29.beertender.entities;

import java.util.*;

public class Cart {

	private UUID id = UUID.randomUUID();
	private Map<Long, CartRow> cartRowList = new HashMap<>();

	public UUID getId() {
		return id;
	}

	public List<CartRow> getCartRowList() {
		return new ArrayList<CartRow>(cartRowList.values());
	}

	public Cart addProduct(Product product, int qte) {
		if (cartRowList.get(product.getId()) != null) {
			if (cartRowList.get(product.getId()).getQuantity() + qte >= 0) {
				cartRowList.get(product.getId()).setQuantity(cartRowList.get(product.getId()).getQuantity() + qte);
			} else {
				removeProduct(product);
			}
		} else {
			cartRowList.put(product.getId(), new CartRow(product, qte));
		}
		return this;
	}

	public Cart removeProduct(Product product) {
		cartRowList.remove(product.getId());
		return this;
	}


}
