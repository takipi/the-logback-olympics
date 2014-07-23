package com.takipi.benchmark.logging.tests;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.takipi.benchmark.logging.BM;

public abstract class BaseTest implements Runnable
{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run()
	{
		while (!Thread.currentThread().isInterrupted() && !BM.interrupted)
		{
			test();
			
			doSomethingBetweenLogging();
		}
	}
	
	protected abstract void test();
	
	private void doSomethingBetweenLogging()
	{
		Random rand = new Random();
		int num = rand.nextInt(200);
		
		isPrime(num);
	}
	
	private boolean isPrime(int n)
	{
	    for (int i = 2; i < n; i++) 
	    {
	        if (n % i == 0) 
	        {
	            return false;
	        }
	    }
	    
	    return true;
	}
	
	public String updateMessage(String message, String logLine) 
	{
		if(BM.MESSAGE_LENGTH == 0 || BM.MESSAGE_LENGTH < -1 || 
				message.length() == BM.MESSAGE_LENGTH) 
		{
			return message;
		}
		if (BM.MESSAGE_LENGTH == -1) 
		{
			return message + BM.LONG_STRING.substring(0, BM.EQUAL_LOG_LINE_LENGTH - logLine.length());
		}
		if(message.length() > BM.MESSAGE_LENGTH) 
		{
			return message.substring(0, BM.MESSAGE_LENGTH);
		}
		return message + BM.LONG_STRING.substring(0, BM.MESSAGE_LENGTH - message.length());
	}

}
