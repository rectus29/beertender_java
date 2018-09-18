package com.rectus29.beertender.exception;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 18/09/2018 11:26               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderException extends Exception {

	public BeerTenderException() {
	}

	public BeerTenderException(String message) {
		super(message);
	}

	public BeerTenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeerTenderException(Throwable cause) {
		super(cause);
	}

	public BeerTenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
