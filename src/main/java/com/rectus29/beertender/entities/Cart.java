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
            if(cartRowList.get(product.getId()).getQuantity() + qte>=0){
                cartRowList.get(product.getId()).setQuantity(cartRowList.get(product.getId()).getQuantity() + qte);
            }else{
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

    public class CartRow {
        private Product product;
        private int quantity = 0;
        private double sum = 0d;

        CartRow(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
            updateSum();
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        void setQuantity(int quantity) {
            this.quantity = quantity;
            updateSum();
        }

        public double getSum() {
            return sum;
        }

        void updateSum() {
            //method stub
            //this.sum = product.getPrice().multiply(quantity);
        }
    }


}
