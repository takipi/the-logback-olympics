package com.takipi.benchmark.logging.tests;

public class PrudentModeAppenderTest extends BaseTest 
{
	String message = "Prudent mode test.";
	String logLine = "2014-07-21 13:38:20,472 [pool-1-thread-2] INFO com.takipi.benchmark.logging.tests.PrudentModeAppenderTest - Prudent mode test.";

	public PrudentModeAppenderTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
