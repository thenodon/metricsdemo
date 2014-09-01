package com.ingby.metricstest;


import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;


/**
 * Invoice class to do invoice things
 */
public class Invoice {

	private static final Logger LOGGER = LoggerFactory.getLogger(Invoice.class);
	private static AtomicLong count = new AtomicLong(0);
	
	private long id;
		
	public Invoice(long id) {
		this.id = id;
	}
	
	/**
	 * Do invoice stuff
	 * @param id invoice id
	 */
	void doInvoiceJMX() {
		try {
			doValidate();
			doSave();
		} catch (InvoiceException ie) {
			LOGGER.warn("Invoice {} id executed failed", id)	;
		} finally {
			count.incrementAndGet();
		}
	}
	
	
	/**
	 * 
	 * @param id
	 * @throws InvoiceException
	 */
	void doInvoiceMetric() {
		final Timer responses = MBeanManager.getRegister().timer(MetricRegistry.name(Invoice.class,"responses time"));
		final Timer.Context context = responses.time();
		final Counter failedInvoices = MBeanManager.getRegister().counter(MetricRegistry.name(Invoice.class,"failedInvoices"));
		try {
			doValidate();
			doSave();
		} catch (InvoiceException ie) {
			failedInvoices.inc();
			LOGGER.warn("Invoice {} id executed failed", id)	;
		} finally {
	        long time = context.stop()/1000000;
	        LOGGER.info("Invoice {} id executed in {} ms", id, time);
	        count.incrementAndGet();
		}
	}
	

	public static long getInvoiceCount() {
		return count.get();
	}
	
	
	private void doSave() throws InvoiceException {
		if (Math.random()*100 > 92) {
			throw new InvoiceException();
		}
	}
	
	private void doValidate() {
		try {
			Thread.sleep((long) (Math.random()*2000));
		} catch (InterruptedException e) {}
	}
}
