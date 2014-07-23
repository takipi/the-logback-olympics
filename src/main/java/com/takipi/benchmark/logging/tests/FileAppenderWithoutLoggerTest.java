package com.takipi.benchmark.logging.tests;

public class FileAppenderWithoutLoggerTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "2014-07-21 12:38:32,754 [pool-1-thread-10] INFO - File appender test";

	public FileAppenderWithoutLoggerTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
