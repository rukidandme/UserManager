/**
 * 
 */
package com.usermanager.utilities;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class Log4J2PropertiesConf {
    private static final Logger logger = LogManager.getLogger(Log4J2PropertiesConf.class.getName());
   
    public static void main(String[] args) {
    	Log4J2PropertiesConf log4J2PropertiesConf=new Log4J2PropertiesConf();
      log4J2PropertiesConf.performSomeTask();
    }    
    
    public void performSomeTask(){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
        
        logger.error("Error Message Logged !!!", new NullPointerException("NullError"));
    }
}