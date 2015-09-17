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

	@Override
	public void configure() throws Exception {
		from("file://{{create-order-outbox}}?delete=true")
		  .convertBodyTo(byte[].class, "iso-8859-1")
		  .to("bean:orderProcessor?method=updateOrder")
		  .to("file://{{create-order-outbox-processed}}");
		
	}

}
