package com.takipi.benchmark.logging.tests;

public class FileAppenderWithoutThreadTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "2014-07-21 12:39:00,938 INFO com.takipi.benchmark.logging.tests.FileAppenderWithoutThreadTest - File appender test";

	public FileAppenderWithoutThreadTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
