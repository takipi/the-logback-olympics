package com.takipi.benchmark.logging.tests;

public class AsyncAppenderDefaultsTest extends BaseTest 
{
	String message = "Async appender test";
	String logLine = "2014-07-21 12:02:57,354 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.AsyncAppenderDefaultsTest - Async appender test";

	public AsyncAppenderDefaultsTest() {
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
