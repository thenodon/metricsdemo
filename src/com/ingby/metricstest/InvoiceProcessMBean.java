package com.ingby.metricstest;

public interface InvoiceProcessMBean {
	/**
	 * The total number of invoices 
	 * processed
	 * @return the total number 
	 */
	long getInvoiceCount();
	
	boolean healthy();
}
