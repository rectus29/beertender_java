package com.rectus29.beertender.entities.payment;

import com.rectus29.beertender.entities.BasicGenericEntity;
import com.rectus29.beertender.entities.Order;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 19/12/2018 16:42               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "payment")
public class Payment extends BasicGenericEntity<Payment> {
		
	@OneToOne
	private Order order;

}