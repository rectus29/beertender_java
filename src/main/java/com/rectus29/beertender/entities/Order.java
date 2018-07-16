package com.rectus29.beertender.entities;


import com.rectus29.beertender.enums.State;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rectus_29
 * Date: 17/01/13
 * Time: 15:13
 */
@Entity
@Table(name = "bills")
public class Order extends GenericEntity {

    @OneToMany(mappedBy = "referenceOrder")
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    @ManyToOne
    private User user;

    @Column
    private State state = State.PENDING;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String paymentToken="";

    @Column(columnDefinition = "MEDIUMTEXT")
    private String paymentRequest="";

    @Column(columnDefinition = "MEDIUMTEXT")
    private String paymentResponse="";

    @ManyToOne(targetEntity = TimeFrame.class)
	@JoinColumn(name = "timeframe_id", nullable = false)
	private TimeFrame timeFrame;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }


    public BigDecimal getOrderPrice() {
        BigDecimal res = new BigDecimal(0);

        for (OrderItem orderItem : orderItemList)
            res = res.add(orderItem.getOrderItemPrice());
        return res;
    }
    public BigDecimal getOrderTax() {
        BigDecimal res = new BigDecimal(0);

        for (OrderItem orderItem : orderItemList)
            res = res.add(orderItem.getOrderItemTax());
        return res;
    }

    public BigDecimal getOrderPriceWithTax() {
        BigDecimal res = new BigDecimal(0);

        for (OrderItem orderItem : orderItemList)
            res = res.add(orderItem.getOrderItemPriceWithTax());
        return res;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }
      public String getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(String paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(String paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public Order() {
    }
}
