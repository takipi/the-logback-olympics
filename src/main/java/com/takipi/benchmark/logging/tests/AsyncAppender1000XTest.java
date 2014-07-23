package com.takipi.benchmark.logging.tests;


public class AsyncAppender1000XTest extends BaseTest {

	String message = "Async appender test";
	String logLine = "2014-07-21 12:02:57,354 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.AsyncAppender1000XTest - Async appender test";

	public AsyncAppender1000XTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
	
}
