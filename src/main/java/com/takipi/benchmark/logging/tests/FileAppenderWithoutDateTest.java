package com.takipi.benchmark.logging.tests;

public class FileAppenderWithoutDateTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "[pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.FileAppenderWithoutDateTest - File appender test";

	public FileAppenderWithoutDateTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
