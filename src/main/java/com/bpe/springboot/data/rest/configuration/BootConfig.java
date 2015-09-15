package com.bpe.springboot.data.rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bpe.springboot.data.rest.bean.EmailAttachmentReceiver;

/**
 * Spring configuration class.
 * 
 * @see http://www.tutorialspoint.com/spring/spring_java_based_configuration.htm
 * @see http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 * 
 * @author polinchakb
 *
 */
@Configuration
public class BootConfig {
	
	@Value("${saveDirectory}")
	private String saveDirectory;
	@Value("${host}")
	private String host;
	@Value("${port}")
	private String port;
	@Value("${userName}")
	private String userName;
	@Value("${password}")
	private String password; 	

	@Bean
    public EmailAttachmentReceiver emailAttachmentReceiver() {
		return new EmailAttachmentReceiver(saveDirectory,host,port,userName,password);
	}
}
