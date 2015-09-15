package com.bpe.springboot.data.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bpe.springboot.data.rest.bean.EmailAttachmentReceiver;

@Configuration
public class BootConfig {

	@Bean
    public EmailAttachmentReceiver emailAttachmentReceiver() {
		return new EmailAttachmentReceiver("c:\\");
	}
}
