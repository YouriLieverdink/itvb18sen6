package nl.hanze.demo1;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class HelloWorldListener implements MessageListener {
	private String consumerName;

	public HelloWorldListener(String consumerName) {
		this.consumerName = consumerName;
	}

	public void onMessage(Message message) {
		int id = 0;
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Consumer(" + consumerName + "): " + id + " Received: " + text);
			} else {
				System.out.println("Consumer(" + consumerName + "): " + id + " Received: " + message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
