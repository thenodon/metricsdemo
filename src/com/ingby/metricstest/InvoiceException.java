package com.ingby.metricstest;

public class InvoiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvoiceException() {
		super();
	}

	public InvoiceException(String message) {
		super(message);
	}

	public InvoiceException(String message,Throwable cause) {
		super(message, cause);
	}

	public InvoiceException(Throwable cause) {
		super(cause);
	}

}
