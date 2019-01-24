package com.rectus29.beertender.entities.payment;

import com.rectus29.beertender.entities.GenericEntity;
import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.StateLessGenericEntity;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.PaymentMethod;
import com.rectus29.beertender.enums.PaymentStatus;
import com.rectus29.beertender.enums.State;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 19/12/2018 16:42               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(
		name = "paymentMethod",
		discriminatorType = DiscriminatorType.INTEGER
)
public abstract class Payment extends StateLessGenericEntity<Payment> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	@Column
	@Enumerated(EnumType.STRING)
	protected PaymentMethod paymentMethod = PaymentMethod.CHEQUE;
	@Column
	protected BigDecimal amount = BigDecimal.ZERO;
	@ManyToOne
	protected User user;
	@ManyToOne
	protected Order order;
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus = PaymentStatus.WAITING;

	protected Payment() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public Payment setUser(User user) {
		this.user = user;
		return this;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public Payment setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Payment setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public Order getOrder() {
		return order;
	}

	public Payment setOrder(Order order) {
		this.order = order;
		return this;
	}
}
