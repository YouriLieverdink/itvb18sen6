package nl.hanze.demo1;

public class Runner {

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public static void main(String[] args) throws Exception {
		boolean topic = false;
		boolean getAll = true;

		System.out.println("Produce, wait, consume");
		thread(new HelloWorldProducer(), false);
		thread(new HelloWorldProducer(), false);
		thread(new HelloWorldProducer(topic), false);
		thread(new HelloWorldProducer(topic), false);
		Thread.sleep(5000);
		thread(new HelloWorldConsumer(), false);
		thread(new HelloWorldConsumer(), false);
		thread(new HelloWorldConsumer(topic, getAll), false);

		Thread.sleep(5000);
		System.out.println("Consume, wait, produce, topics");
		thread(new HelloWorldConsumer(topic, getAll), false);
		thread(new HelloWorldConsumer(topic, getAll), false);
		thread(new HelloWorldConsumer(topic, !getAll), false);
		Thread.sleep(1000);
		thread(new HelloWorldProducer(topic), false);
		thread(new HelloWorldProducer(topic), false);
		thread(new HelloWorldProducer(topic), false);
		thread(new HelloWorldProducer(topic), false);
		Thread.sleep(2500);

		System.out.println("Consume, wait, produce, Queues");
		thread(new HelloWorldConsumer(true, getAll), false);
		thread(new HelloWorldConsumer(true, getAll), false);
		thread(new HelloWorldConsumer(), false);
		Thread.sleep(1000);
		thread(new HelloWorldProducer(), false);
		thread(new HelloWorldProducer(), false);
		thread(new HelloWorldProducer(), false);
		thread(new HelloWorldProducer(), false);
	}
}
