package com.bpe.springboot.data.rest.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bpe.springboot.data.rest.bean.EmailAttachmentReceiver;
import com.bpe.springboot.data.rest.entity.Person;
import com.bpe.springboot.data.rest.repository.PersonRepository;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private final Logger logger = Logger.getLogger(ScheduledTasks.class.getName());
    
    @Autowired
    PersonRepository dao;
    
    @Autowired
    EmailAttachmentReceiver emailReciever;

    @Scheduled(fixedRate = 5000)
    public void printPeople() {       
        logger.info("The time is now " + dateFormat.format(new Date()));
        Iterable<Person> people = dao.findAll();
        Iterator<Person> peopleIt = people.iterator();
        while(peopleIt.hasNext()) {
            Person p = peopleIt.next();
            logger.info("Person inventory: "+p.getLastName()+", "+p.getFirstName());
        }        
    }
    
    @Scheduled(fixedRate=5000) 
    public void getEmail() {
    	logger.info("Getting email.");
    	emailReciever.downloadEmailAttachments();
    }
}
