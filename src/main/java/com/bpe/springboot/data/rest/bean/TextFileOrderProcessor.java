package com.bpe.springboot.data.rest.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bpe.springboot.data.rest.dao.OrderDao;
import com.bpe.springboot.data.rest.entity.Order;

/**
 * A bean for creating and updating orders based on the context found in a file.
 * 
 * @author polinchakb
 *
 */
public class TextFileOrderProcessor implements OrderProcessor {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
	
	private final static Logger logger = Logger.getLogger(TextFileOrderProcessor.class.getName());
	
	private OrderDao orderDao;
        
    private EmailAttachmentReceiver emailReciever;
    
    private String createOrderOutbox;   
      
	private CamelContext context;

    @Autowired
    public TextFileOrderProcessor(CamelContext camelContext, OrderDao orderDao, EmailAttachmentReceiver emailAttachmentReceiver, String createOrderOutbox) {
    	context = camelContext;
    	this.orderDao = orderDao;
    	this.emailReciever = emailAttachmentReceiver;
    	this.createOrderOutbox = createOrderOutbox;
    }
    
    /* (non-Javadoc)
	 * @see com.bpe.springboot.data.rest.bean.OrderProcessor#checkOrders()
	 */
	@Override
	public void checkOrders(){
		emailReciever.downloadEmailAttachments();
	}
	
	/* (non-Javadoc)
	 * @see com.bpe.springboot.data.rest.bean.OrderProcessor#updateOrder(byte[])
	 */
	@Override
	public void updateOrder(byte[] fileContents) {
		logger.info("updateOrder invoked");
		String decrypted = new String(fileContents);
		logger.info("file contents: "+decrypted);
	}

	/* (non-Javadoc)
	 * @see com.bpe.springboot.data.rest.bean.OrderProcessor#sendOrders()
	 */
    @Override
	public void sendOrders() {
       List<Order> orders = orderDao.findByDateSentIsNull();
       StringBuilder content = new StringBuilder();
       for(Order order : orders) {
           content.append("Order : "+order.getId()+" information: "+order.getOrderInfo()); 
           content.append("\n");
           order.setDateSent(Calendar.getInstance().getTime());
           orderDao.save(order);
       }
       if(orders.size() == 0) {
    	   logger.info("There are no new orders to send.");
    	   return;
       }
       BufferedWriter bw = null;
       try {			
            String timestamp = dateFormat.format(Calendar.getInstance().getTime());
			File file = new File(createOrderOutbox+"/order.txt."+timestamp);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content.toString());
			bw.close();

			logger.info("Done writing order.");
			logger.info("Emailing the order....");
			// create an exchange with a normal body and attachment to be produced as email
			Endpoint endpoint = context.getEndpoint("smtps://springboot.data.rest@smtp.gmail.com?password=whsvarpcodgenjjl");
			 
			// create the exchange with the mail message that is multipart with a file and a Hello World text/plain message.
			Exchange exchange = endpoint.createExchange();
			Message in = exchange.getIn();
			in.setBody("Hello World");
			Map<String, Object> headers = new HashMap<>();
			headers.put("from", "springboot.data.rest@gmail.com");
			headers.put("to", "polinchw@netscape.net");
			headers.put("subject", "New Orders");
			headers.put("contentType", "text/plain;charset=UTF-8");
			in.setHeaders(headers);
			in.setBody("See attachement for new orders.");

//			in.addAttachment("order.txt", new DataHandler(new FileDataSource(createOrderOutbox+"/order.txt"+timestamp)));
			in.addAttachment("order.txt", new DataHandler(new FileDataSource(file)));
			
			 
			// create a producer that can produce the exchange (= send the mail)
			Producer producer = endpoint.createProducer();
			// start the producer
			producer.start();
			// and let it go (processes the exchange by sending the email)
			producer.process(exchange);
            logger.info("Done sending emails.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}    
}
