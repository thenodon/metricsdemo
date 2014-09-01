package com.ingby.metricstest;

import java.util.Map;
import java.util.SortedMap;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;


public class InvoiceProcess implements InvoiceProcessMBean, NotificationListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceProcess.class);
	
	private static MBeanManager mbsMgr;
	
	public static void main(String[] args) throws Exception {

		InvoiceProcess invProc = new InvoiceProcess();
		
		mbsMgr = new MBeanManager(invProc,"InvoiceProcess:type=Process");
	    mbsMgr.registerMBeanserver();
	    
	    MBeanManager.getHealthCheckRegistry().register("invoice", new InvoiceHealthCheck());

	    InvoiceProcessNotification.createMonitor(mbsMgr.getMBeanServer());
	    
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
		    LOGGER.debug("Health check \"{}\"", entry.getKey());
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
	
	
	@Override
	public double getFailedRatio() {
		SortedMap<String, Counter> counters = MBeanManager.getRegister().getCounters();
		long failed = counters.get("com.ingby.metricstest.Invoice.failedInvoices").getCount();
		
		SortedMap<String, Timer> timers = MBeanManager.getRegister().getTimers();
		long total = timers.get("com.ingby.metricstest.Invoice.responses time").getCount();
		
		double ratio = 0;
		if (total != 0) {
			ratio = (double) failed / (double) total;
		} 
		
		return ratio;
	}
	
	@Override
	public void handleNotification(Notification arg0, Object arg1) {
		
    	LOGGER.info("==================== Called notification {} {}",arg0.toString(), arg1.toString());
		
	}

	
	
	
}
