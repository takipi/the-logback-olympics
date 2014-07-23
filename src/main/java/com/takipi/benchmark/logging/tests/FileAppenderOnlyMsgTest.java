package com.takipi.benchmark.logging.tests;

public class FileAppenderOnlyMsgTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "File appender test";

	public FileAppenderOnlyMsgTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
