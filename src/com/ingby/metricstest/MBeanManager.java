package com.ingby.metricstest;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;


public class MBeanManager {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(MBeanManager.class);
    private String objectName;
    private Object register;
    private MBeanServer mbs;
    ObjectName mbeanname;
    
    
    
    
    ////////////////////////////
    private static final String METRICS_DOMAIN = "metrics";
    
    private static final MetricRegistry metricsRegister = new MetricRegistry();
	private static final JmxReporter reporter = JmxReporter.forRegistry(metricsRegister).inDomain(METRICS_DOMAIN).build();
    
    private static final HealthCheckRegistry healthChecks = new HealthCheckRegistry();
    
    static {
    	reporter.start();
    }
    
    public MBeanManager(Object register,String objectName) {
        this.objectName = objectName;
        this.register = register;
    }
    
    public void registerMBeanserver() {
        
        mbs = ManagementFactory.getPlatformMBeanServer();
        
        try {
            mbeanname = new ObjectName(objectName);
        } catch (MalformedObjectNameException e) {
            LOGGER.error("MBean object name failed, {}", e.getMessage(),e);
        } catch (NullPointerException e) {
            LOGGER.error("MBean object name failed, {}", e.getMessage(),e);
        }


        try {
            mbs.registerMBean(register, mbeanname);
        } catch (InstanceAlreadyExistsException e) {
            LOGGER.error("Mbean exception - {}", e.getMessage(),e);
        } catch (MBeanRegistrationException e) {
            LOGGER.error("Mbean exception - {}", e.getMessage(),e);
        } catch (NotCompliantMBeanException e) {
            LOGGER.error("Mbean exception - {}", e.getMessage(),e);
        }
    }
    
    public void unRegisterMBeanserver() {
        try {
            mbs.unregisterMBean(mbeanname);
        } catch (MBeanRegistrationException e) {
            LOGGER.warn("Mbean {} could not be unregistered", mbeanname, e);
        } catch (InstanceNotFoundException e) {
            LOGGER.warn("Mbean {} instance could not be found", mbeanname, e);
        }
    }        
    /////////////////
    public static MetricRegistry getRegister() {
    	return metricsRegister;
    }
    
    public static HealthCheckRegistry getHealthCheckRegistry() {
    	return healthChecks;
    }
	
}
