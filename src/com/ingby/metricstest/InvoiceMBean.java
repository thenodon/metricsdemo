package com.ingby.metricstest;

/**
 * MBean interface for {@link Invoice}
 *
 */
public interface  InvoiceMBean {
	/**
	 * The total number of invoices 
	 * processed
	 * @return the total number 
	 */
	long getInvoiceCount();
}


