package com.qbuzz.soapserver;

import javax.xml.ws.Endpoint;

public class QbuzzPublisher {

	public static void start() {
		try {
			//String localHost = InetAddress.getLocalHost().getHostAddress();
			String localHost = "localhost";
			Endpoint.publish("http://"+ localHost + ":8888/QBUZZServices", new QbuzzServices());
			System.out.println("now serving QBUZZ at http://" + localHost + ":8888/QBUZZServices");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
}