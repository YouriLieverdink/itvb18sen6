package nl.hanze.demo1;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class HelloWorldConsumer implements Runnable, ExceptionListener {
	private boolean queue = true;
	private boolean getAll = false;
	private static int counter = 0;
	private int id;
	private String type = "queue";

	public HelloWorldConsumer() {
		this.id = counter++;
	}

	public HelloWorldConsumer(boolean queue, boolean getAll) {
		this.queue = queue;
		this.type = (queue) ? "queue" : "topic";
		this.getAll = getAll;
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

			connection.setExceptionListener(this);

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = (queue) ? session.createQueue("QUEUE.FOO") : session.createTopic("TOPIC.FOO");

			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageConsumer consumer = session.createConsumer(destination);

			boolean newMessage = true;
			// consumer.setMessageListener(new HelloWorldListener("Consumer"));
			// Wait for a message
			while (newMessage) {
				Message message = consumer.receive(2000);

				newMessage = false;
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("Consumer(" + type + "): " + id + " Received: " + text);
					newMessage = getAll;
				} else {
					System.out.println("Consumer(" + type + "): " + id + " Received: " + message);
				}
			}
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured.  Shutting down client.");
	}
}