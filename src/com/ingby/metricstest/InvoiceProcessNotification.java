package com.ingby.metricstest;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class InvoiceProcessNotification {

	public static void createMonitor(MBeanServer mbs) throws Exception {
		
		ObjectName inmon = new ObjectName("InvoiceProcess:id=ProcessMonitor");
		mbs.createMBean("javax.management.monitor.GaugeMonitor", inmon);
		
		AttributeList inmal = new AttributeList();
		inmal.add(new Attribute("ObservedObject", new ObjectName("InvoiceProcess:type=Process")));
		inmal.add(new Attribute("ObservedAttribute", "FailedRatio"));
		inmal.add(new Attribute("GranularityPeriod", new Long(10000)));
		inmal.add(new Attribute("NotifyHigh", new Boolean(true)));
		mbs.setAttributes(inmon, inmal);

		mbs.invoke(inmon, "setThresholds", new Object[] { new Double("0.05"),new Double("0.0")}, new String[] {"java.lang.Number","java.lang.Number"});
		mbs.addNotificationListener(inmon, new ObjectName("InvoiceProcess:type=Process"), null, new Object());
		mbs.invoke(inmon, "start", new Object[] {}, new String[] {});
		
	}
}
