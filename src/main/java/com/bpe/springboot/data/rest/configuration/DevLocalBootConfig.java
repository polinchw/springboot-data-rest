package com.bpe.springboot.data.rest.configuration;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bpe.springboot.data.rest.bean.EmailAttachmentReceiver;
import com.bpe.springboot.data.rest.bean.OrderProcessor;
import com.bpe.springboot.data.rest.bean.TextFileOrderProcessor;
import com.bpe.springboot.data.rest.dao.OrderDao;
import com.bpe.springboot.data.rest.repository.OrderRepository;

/**
 * Spring configuration class.  The properties in this class map to the application.properties file.
 * The values can be overriden at the command line.
 * 
 * java -jar springboot-data-rest.jar --saveDirectory="c:\\Spring"
 * 
 * To run with the prod config file use this statement at the command line.
 * 
 * java -jar springboot-data-rest.jar --spring.profiles.active=prod
 * 
 * @see http://www.tutorialspoint.com/spring/spring_java_based_configuration.htm
 * @see http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 * 
 * @author polinchakb
 *
 */
@Configuration
@Profile("dev-local")
public class DevLocalBootConfig {
	
	@Value("${saveDirectory}")
	private String saveDirectory;
	@Value("${popHost}")
	private String popHost;
	@Value("${popPort}")
	private String popPort;
	@Value("${popUserName}")
	private String popUserName;
	@Value("${popPassword}")
	private String popPassword;
	@Value("${create-order-outbox}")
	private String createOrderOutbox;
	
	@Autowired
	CamelContext camelContext;
	
	@Autowired
	OrderRepository orderDao;

	@Bean(name="emailReceiver")
    public EmailAttachmentReceiver emailAttachmentReceiver() {
		return new EmailAttachmentReceiver(saveDirectory,popHost,popPort,popUserName,popPassword);
	}
	
	@Bean(name="orderProcessor")
	public OrderProcessor orderProcessor() {
		return new TextFileOrderProcessor(orderDao,this.emailAttachmentReceiver(),createOrderOutbox);
	}
	
}
