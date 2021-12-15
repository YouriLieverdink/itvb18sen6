package bussimulator;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "bus_berichten";
    
    private Session session;
    private Connection connection;
    private MessageProducer producer;
    
    public Producer() {
    }
    
    public void sendBericht(String bericht) {
    	try {
    		createConnection();
    		sendTextMessage(bericht);
            this.connection.close();
    	} 
    	catch (JMSException e) {
    		e.printStackTrace();
    	}
    }
        
    private void createConnection() throws JMSException {
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
    	connection = connectionFactory.createConnection();
    	connection.start();
    	
    	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	Destination destination = session.createQueue(subject);
    	producer = session.createProducer(destination); 
    }
    
    private void sendTextMessage(String themessage) throws JMSException {
    	TextMessage msg = session.createTextMessage(themessage);

        // Here we are sending the message!
        producer.send(msg);
    }    
}
