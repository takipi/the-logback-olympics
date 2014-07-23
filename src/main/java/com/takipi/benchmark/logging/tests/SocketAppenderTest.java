package com.takipi.benchmark.logging.tests;

public class SocketAppenderTest extends BaseTest 
{
	String message = "Socket appender test";
	String logLine = "2014-07-21 12:39:06,853 [pool-2-thread-10] INFO com.takipi.benchmark.logging.tests.SocketAppenderTest - Socket appender test";

	public SocketAppenderTest() 
	{
		message = updateMessage(message, logLine);
	}

	@Override
	protected void test() 
	{
		logger.info(message);
	}
}
