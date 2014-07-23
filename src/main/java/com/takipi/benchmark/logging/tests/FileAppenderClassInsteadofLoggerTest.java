package com.takipi.benchmark.logging.tests;

public class FileAppenderClassInsteadofLoggerTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "2014-07-21 12:38:25,380 [pool-1-thread-10] INFO com.takipi.benchmark.logging.tests.FileAppenderClassInsteadofLoggerTest - File appender test";

	public FileAppenderClassInsteadofLoggerTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
