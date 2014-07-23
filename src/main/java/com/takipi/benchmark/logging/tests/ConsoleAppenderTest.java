package com.takipi.benchmark.logging.tests;

public class ConsoleAppenderTest extends BaseTest 
{
	String message = "Console appender test";
	String logLine = "2014-07-21 12:02:57,354 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.ConsoleAppenderTest - Console appender test";

	public ConsoleAppenderTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
