package com.bpe.springboot.data.rest.route;

import java.io.FileOutputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EmailReaderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		 // the API is a bit clunky so we need to loop
	     Map<String, DataHandler> attachments = exchange.getIn().getAttachments();
	     if (attachments.size() > 0) {
	         for (String name : attachments.keySet()) {
	             DataHandler dh = attachments.get(name);
	             // get the file name
	             String filename = dh.getName();
	 
	             // get the content and convert it to byte[]
	             byte[] data = exchange.getContext().getTypeConverter()
	                               .convertTo(byte[].class, dh.getInputStream());
	 
	             // write the data to a file
	             FileOutputStream out = new FileOutputStream(filename);
	             out.write(data);
	             out.flush();
	             out.close();
	         }
	     }
		
	}

}
