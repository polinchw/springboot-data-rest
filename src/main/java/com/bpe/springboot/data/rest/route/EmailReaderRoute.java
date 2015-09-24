package com.bpe.springboot.data.rest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmailReaderRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
//		from("pop3://springboot.data.rest@pop.gmail.com:995?password=springboot"
//			    + "&delete=false&unseen=true&consumer.delay=60000").process(new EmailReaderProcessor());
	
	}

}
