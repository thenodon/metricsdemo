package com.ingby.metricstest;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistryListener;
import com.codahale.metrics.Timer;

public class Listner implements MetricRegistryListener {

	@Override
	public void onCounterAdded(String arg0, Counter arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCounterRemoved(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGaugeAdded(String arg0, Gauge<?> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGaugeRemoved(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHistogramAdded(String arg0, Histogram arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHistogramRemoved(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMeterAdded(String arg0, Meter arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMeterRemoved(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerAdded(String arg0, Timer arg1) {
		System.out.println(arg0 + ":"+ arg1);
		
	}

	@Override
	public void onTimerRemoved(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
