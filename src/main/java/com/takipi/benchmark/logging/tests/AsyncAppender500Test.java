package com.takipi.benchmark.logging.tests;

public class AsyncAppender500Test extends BaseTest 
{
	String message = "Async appender test";
	String logLine = "2014-07-21 12:02:57,354 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.AsyncAppender500Test - Async appender test";

	public AsyncAppender500Test() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
