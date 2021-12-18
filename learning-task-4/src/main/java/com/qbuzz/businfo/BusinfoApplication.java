package com.qbuzz.businfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.qbuzz.soapserver.QbuzzPublisher;

@SpringBootApplication
public class BusinfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinfoApplication.class, args);
		QbuzzPublisher.start();
	}

    private static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}
