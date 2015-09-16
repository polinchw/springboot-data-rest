package com.bpe.springboot.data.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Camel route for processing incoming files.
 * 
 * @author polinchakb
 *
 */
@Component
public class FileReaderRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file://{{inbox}}?delete=true").to("file://{{outbox}}");
	}

}
