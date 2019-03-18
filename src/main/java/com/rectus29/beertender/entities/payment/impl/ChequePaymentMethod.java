package com.rectus29.beertender.entities.payment.impl;

import com.rectus29.beertender.entities.payment.Payment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 11/01/2019 10:45               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

@Entity(name = "ChequePaymentMethod")
@Table(name = "payment_cheque", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
@DiscriminatorValue("CHEQUE")
public class ChequePaymentMethod extends Payment {

	private String chequeNumber;

	protected ChequePaymentMethod() {
	}

	public ChequePaymentMethod(String chequeNumber) {
		super();
		this.chequeNumber = chequeNumber;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public ChequePaymentMethod setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
		return this;
	}
}
