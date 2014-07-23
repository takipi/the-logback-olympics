package com.takipi.benchmark.logging.tests;

import org.slf4j.MDC;

public class SiftingAppenderTest extends BaseTest 
{
	
	String logId = null;
	String message = "Sift appender test.";
	String logLine = "2014-07-21 12:38:38,641 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.SiftingAppenderTest - Sift appender test.";

	public SiftingAppenderTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		if(logId == null) {
			switch(Thread.currentThread().getName().substring(Thread.currentThread().getName().length()-1)) {
			case "0": case "1": case "2":
				logId = "1";
				break;
			case "3": case "4": case "5":
				logId = "2";
				break;
			case "6": case "7":
				logId = "3";
				break;
			case "8": case "9": default:
				logId = "4";
				break;
			}
			MDC.put("logid", logId);
		}
		logger.info(message);
	}
}
