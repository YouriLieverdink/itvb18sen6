package nl.hanze.demo2;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author Pieter, refactored by Bart
 */

public class Consumer {
    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Name of the queue we will receive messages from
    private static String subject = "TESTQUEUE1";

    private Connection connection;
    private Session session;
    private Message message;

    public Consumer() {

    }

    private void demoMethod() {
        try {
            openConnection();
            receiveMessages();
            showMessages();
            createPerson();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void openConnection() throws JMSException {
        // Getting JMS connection from the server
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();

        // Creating session for seding messages
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private void receiveMessages() throws JMSException {
        // Getting the queue 'TESTQUEUE'
        Destination destination = session.createQueue(subject);

        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);

        // Here we receive the message.
        // By default this call is blocking, which means it will wait
        // for a message to arrive on the queue.
        message = consumer.receive();
    }

    private void showMessages() throws JMSException {
        // There are many types of Message and TextMessage
        // is just one of them. Producer sent us a TextMessage
        // so we must cast to it to get access to its .getText()
        // method.
        System.out.println("Consumer receiving messages: " + new Date());
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message '"
                    + textMessage.getText() + "'");
        }
    }

    private void createPerson() throws JMSException {
        XStream xstream = new XStream();
        // xstream.alias("Person", Person.class);
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            Person p2 = (Person) xstream.fromXML(msg.getText());
            System.out.println("Person met naam: " + p2.getName() + " en leeftijd: " + p2.getAge());
        }
    }

    public static void main(String[] args) throws JMSException {
        Consumer cons = new Consumer();
        cons.demoMethod();

    }
}
