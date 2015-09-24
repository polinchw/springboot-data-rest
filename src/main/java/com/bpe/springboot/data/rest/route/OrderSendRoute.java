package com.bpe.springboot.data.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Camel route for sending new orders out.
 * 
 * @author polinchakb
 *
 */
@Component
public class OrderSendRoute extends RouteBuilder {

	/**
	 * We'll update this in future to email the attachments.
	 */
	@Override
	public void configure() throws Exception {
//		from("file://{{create-order-outbox}}?delete=true")
//		  .to("file://{{create-order-outbox-processed}}");
		
	}

}
