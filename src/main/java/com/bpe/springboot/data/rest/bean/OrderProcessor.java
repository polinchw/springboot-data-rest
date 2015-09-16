package com.bpe.springboot.data.rest.bean;

import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * A bean for updating orders based on the context found in a file.
 * 
 * @author polinchakb
 *
 */
public class OrderProcessor {
	
	private final static Logger logger = Logger.getLogger(OrderProcessor.class.getName());
	
	public void updateOrder(byte[] fileContents) {
		logger.info("updateOrder invoked");
		String file = Arrays.toString(fileContents);
		logger.info("file contents: "+file);
	}

}
