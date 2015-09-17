package com.bpe.springboot.data.rest.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bpe.springboot.data.rest.entity.Order;
import com.bpe.springboot.data.rest.repository.OrderRepository;

/**
 * A bean for creating and updating orders based on the context found in a file.
 * 
 * @author polinchakb
 *
 */
public class OrderProcessor {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private final static Logger logger = Logger.getLogger(OrderProcessor.class.getName());
	
	private OrderRepository orderDao;
        
    private EmailAttachmentReceiver emailReciever;
    
    private String createOrderOutbox;   

    @Autowired
    public OrderProcessor(OrderRepository orderDao, EmailAttachmentReceiver emailAttachmentReceiver, String createOrderOutbox) {
    	this.orderDao = orderDao;
    	this.emailReciever = emailAttachmentReceiver;
    	this.createOrderOutbox = createOrderOutbox;
    }
    
    /**
     * Check email for order updates.
     */
	public void checkOrders(){
		emailReciever.downloadEmailAttachments();
	}
	
	/**
	 * Updates the orders.  Read the contexts of the file for information.
	 * @param fileContents
	 */
	public void updateOrder(byte[] fileContents) {
		logger.info("updateOrder invoked");
		String file = Arrays.toString(fileContents);
		logger.info("file contents: "+file);
	}

	/**
	 * Check the database for new orders.  Write the new order to an out bound file.
	 */
    public void sendOrders() {
       List<Order> orders = orderDao.findByDateSentIsNull();
       StringBuilder content = new StringBuilder();
       for(Order order : orders) {
           content.append("Order : "+order.getId()+" information: "+order.getOrderInfo());       
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

		} catch (IOException e) {
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
