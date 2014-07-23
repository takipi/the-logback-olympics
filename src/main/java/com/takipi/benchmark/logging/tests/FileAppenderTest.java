package com.takipi.benchmark.logging.tests;


public class FileAppenderTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "2014-07-21 12:03:06,328 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.FileAppenderTest - File appender test";

	public FileAppenderTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
