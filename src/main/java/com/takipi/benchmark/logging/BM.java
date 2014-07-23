package com.takipi.benchmark.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.net.SimpleSocketServer;

import com.takipi.benchmark.logging.tests.BaseTest;

public class BM
{
	public static boolean interrupted = false;
	static int THREAD_COUNT = 10;
	static long TIMEOUT = 60 * 1000;
	static long TERMINATION_TIMEOUT = 60 * 1000;
	static final int SERVER_PORT = 6000;
	public static final int EQUAL_LOG_LINE_LENGTH = 200;
	public static int MESSAGE_LENGTH = 0;
	public static final String LONG_STRING = "abcdefghijklmnopqrstuvwxyz1234567890"
			+ "abcdefghijklmnopqrstuvwxyz1234567890" + "abcdefghijklmnopqrstuvwxyz1234567890"
			+ "abcdefghijklmnopqrstuvwxyz1234567890" + "abcdefghijklmnopqrstuvwxyz1234567890"
			+ "abcdefghijklmnopqrstuvwxyz1234567890" + "abcdefghijklmnopqrstuvwxyz1234567890";

	static String logFolder = "logs/";
	static String logFile = "test.log";
	static String testNamePrefix = "com.takipi.benchmark.logging.tests.";
	
	public static void main(String[] args)
	{	
		if (args.length < 3)
		{
			System.out.println("Not enough arguments. Please supply [logger name] \n"
					+ "\t[test name (=AsyncAppender1000XTest|AsyncAppender500Test|AsyncAppenderDefaultsTest|\n"
					+ "\t\tConsoleAppenderTest|DebugTest|FileAppenderOnlyLevelAndMsgTest|FileAppenderOnlyMsgTest|\n"
					+ "\t\tFileAppenderTest|FileAppenderWithoutDateTest|FileAppenderWithoutLoggerTest|\n"
					+ "\t\tFileAppenderWithoutThreadTest|HelloWorldTest|PrudentModeAppenderTest|SiftingAppenderTest|\n"
					+ "\t\tSocketAppenderTest|ThrowableTest|ToStringTest)] [test id] [startServer] [quiet] [messageLength (=80|160|equalLogLineLength)]");
			System.exit(0);
		}

	    SimpleSocketServer simpleSocketServer = null;

		try 
		{
			String loggerName = args[0];
			String testName = args[1];
			String logFileName = loggerName+"-"+testName;
			String id = args[2];
			
			boolean quiet = false;
			boolean startServer = false;

			for (int i = 3; i < args.length; i++) {
				if ("quiet".equals(args[i])) {
					quiet = true;
				} else if ("startServer".equals(args[i])) {
					startServer = true;
				} else if ("equalLogLineLength".equals(args[i])) {
					MESSAGE_LENGTH = -1;
					logFileName += "_equalLogLineLength";
					if (!quiet) {
						System.out.println("Running with equal log line length");
					}
				} else {
					try {
						MESSAGE_LENGTH = Integer.parseInt(args[i]);
						logFileName += "_msgLength_"+MESSAGE_LENGTH;
						if (!quiet) {
							System.out.println("Running with message length = " + MESSAGE_LENGTH);
						}
					} catch (NumberFormatException e) {

					}
				}
			}

			if (startServer) {
				if(!quiet) 
				{
					System.out.println("Starting socket server");
				}
			    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			    JoranConfigurator configurator = new JoranConfigurator();
			    loggerContext.reset();
			    configurator.setContext(loggerContext);
			    configurator.doConfigure(BM.class.getClassLoader().getResourceAsStream("logback-server.xml"));
			    simpleSocketServer = new SimpleSocketServer(loggerContext, SERVER_PORT);
			    simpleSocketServer.start();
			}
			
			if("PrudentModeAppenderTest".equals(testName)) {
				THREAD_COUNT = 5;
				System.out.println("Running 5 threads for PrudentModeAppenderTest");
			}
			
			String className = testNamePrefix + ((testName.equals("TimeThreadTest")) ? "ToStringTest" : testName); 
			
			@SuppressWarnings("unchecked")
			Class<BaseTest> testClass = (Class<BaseTest>) Class.forName(className);
						
			ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
			
			for (int j = 0; j < THREAD_COUNT; j++)
			{
				threadPool.execute(testClass.newInstance());
			}
			// Pause this thread for the tests to run
			Thread.sleep(TIMEOUT);
			// Ask the tests to interrupt
			interrupted = true;
			threadPool.shutdownNow();
			// Wait for the tests to terminate
			threadPool.awaitTermination(TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS);
			// Stop logger context
			if(LoggerFactory.getILoggerFactory() instanceof LoggerContext) 
            {   
				if(!quiet) 
				{
					System.out.println("Stopping logger context");
				}
                LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                loggerContext.stop();
            }
			
			if(simpleSocketServer!= null) {
				if(!quiet) 
				{
					System.out.println("Stopping socket server");
				}
				simpleSocketServer.close();
				simpleSocketServer.join(TERMINATION_TIMEOUT);
			}
			
			saveLogFile(logFileName + "-" + id, quiet);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void saveLogFile(String name, boolean quiet)
	{
		copyFile(logFolder + logFile, logFolder + name);
		clearFileContent(logFolder + logFile);
		if(!quiet) 
		{
			System.out.println("Saving " + name);
		}
	}
	
	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void copyFile(String source, String dest)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
		
		try
		{
			File sourceFile = new File(source);
			File destFile = new File(dest);
			
			inStream = new FileInputStream(sourceFile);
			outStream = new FileOutputStream(destFile);
			
			byte[] buffer = new byte[1024];
			int length;

			while ((length = inStream.read(buffer)) > 0)
			{
				outStream.write(buffer, 0, length);
			} 
			
			inStream.close();
			outStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void clearFileContent(String filename)
	{
		PrintWriter writer = null;
		
		try
		{
			writer = new PrintWriter(filename);
			writer.print("");
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}