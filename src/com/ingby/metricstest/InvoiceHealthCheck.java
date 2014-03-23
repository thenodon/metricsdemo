package com.ingby.metricstest;

import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck;

public class InvoiceHealthCheck extends HealthCheck {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceHealthCheck.class);
	private final static double RATIO = 0.1; 

	@Override
    public HealthCheck.Result check() throws Exception {
        //Implement some cool checks
		SortedMap<String, Counter> counters = MBeanManager.getRegister().getCounters();
		long failed = counters.get("com.ingby.metricstest.Invoice.failedInvoices").getCount();
		
		SortedMap<String, Timer> timers = MBeanManager.getRegister().getTimers();
		long total = timers.get("com.ingby.metricstest.Invoice.responses time").getCount();
		
		double ratio = (double) failed / (double) total;
		//LOGGER.info("=============================================> Ratio Failed {} Total {} {} > {}",failed, total, ratio, RATIO);
		if (ratio < RATIO) {
			return HealthCheck.Result.healthy();
		} else {
			return HealthCheck.Result.unhealthy("More then " + Math.round(ratio*100) +" percentage failed invoices");
		}	
		
		
    }
}
