package nl.hanze.demo1;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class HelloWorldProducer implements Runnable {
	private boolean queue = true;
	private static int counter = 0;
	private int id;
	private String type = "queue";

	public HelloWorldProducer() {
		this.id = counter++;
	}

	public HelloWorldProducer(Boolean queue) {
		this.queue = queue;
		this.type = (queue) ? "queue" : "topic";
		this.id = counter++;
	}

	public void run() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = (queue) ? session.createQueue("QUEUE.FOO") : session.createTopic("TOPIC.FOO");

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a messages
			String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.id;
			TextMessage message = session.createTextMessage(text);

			// Tell the producer to send the message
			System.out.println("Sent message(" + type + "): " + message.hashCode() + " : "
					+ Thread.currentThread().getName() + " id: " + this.id);
			producer.send(message);

			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}