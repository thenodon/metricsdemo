package com.ingby.metricstest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;


public class InvoiceProcess implements InvoiceProcessMBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceProcess.class);
	
	private static MBeanManager mbsMgr;
	
	public static void main(String[] args) throws Exception {

		InvoiceProcess invProc = new InvoiceProcess();
		
		mbsMgr = new MBeanManager(invProc,"InvoiceProcess:type=Process");
	    mbsMgr.registerMBeanserver();
	    
	    MBeanManager.getHealthCheckRegistry().register("invoice", new InvoiceHealthCheck());
	    invProc.doInvocing();
		
	}
	
	void doInvocing() {
		long id = 10000L;
		while (true) {
			LOGGER.info("Processing trans id {}", id);
			Invoice invoice = new Invoice(id);
			invoice.doInvoiceMetric();
			id++;
		}
	}

	@Override
	public long getInvoiceCount() {
		return Invoice.getInvoiceCount();
	}

	@Override
	public boolean healthy() {
		final Map<String, HealthCheck.Result> results = MBeanManager.getHealthCheckRegistry().runHealthChecks();
		boolean unhealthy = false;
		for (java.util.Map.Entry<String, Result> entry : results.entrySet()) {
		    if (entry.getValue().isHealthy()) {
		        LOGGER.debug("{} is healthy", entry.getKey());
		    } else {
		    	LOGGER.debug("{} is UNHEALTHY: {}", entry.getKey(), entry.getValue().getMessage());
		        unhealthy = true;
		    }
		}
		
		if (unhealthy) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
