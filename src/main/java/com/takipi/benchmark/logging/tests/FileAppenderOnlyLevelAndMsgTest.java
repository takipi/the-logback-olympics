package com.takipi.benchmark.logging.tests;

public class FileAppenderOnlyLevelAndMsgTest extends BaseTest 
{
	String message = "File appender test";
	String logLine = "INFO File appender test";

	public FileAppenderOnlyLevelAndMsgTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
