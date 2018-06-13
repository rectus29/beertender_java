package com.rectus29.beertender.entities.core;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * User: rectus_29
 * Date: 05/02/13
 * Time: 14:32
 */
@Entity
@Table(name = "order_item")
public class OrderItem extends GenericEntity {

    @ManyToOne
    private Order referenceOrder;

//    @Column
//    private OrderItemState state = OrderItemState.PENDING;
    @Column
    private String productname;

//    @Column
//    private ProductType type;
//
//    @ManyToOne
//    private Product originalProduct;
//
//    @ManyToOne
//    private Shop shop;

    @Column(precision = 12, scale = 3)
    private BigDecimal productPrice;

    @Column(precision = 12, scale = 3)
    private BigDecimal productTax;

    @Column
    private Long quantity;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductTax() {
        return productTax;
    }

    public void setProductTax(BigDecimal productTax) {
        this.productTax = productTax;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    public BigDecimal getProductPriceWithTax(){
        return getProductPrice().add(getProductPrice().multiply(getProductTax()));
    }

    public BigDecimal getOrderItemPrice() {
        return getProductPrice().multiply(new BigDecimal(getQuantity()));
    }
    public BigDecimal getOrderItemPriceWithTax() {
        return getProductPriceWithTax().setScale(3, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(getQuantity()));
    }
    public BigDecimal getOrderItemTax() {
        return getProductPrice().multiply(getProductTax()).setScale(3, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(getQuantity()));
    }


    public Order getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(Order referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    public OrderItem() {
    }

    public OrderItem(String productname, BigDecimal productPrice, BigDecimal productTax, Long quantity) {
        this.productname = productname;
        this.productPrice = productPrice;
        this.productTax = productTax;
        this.quantity = quantity;
    }
}
