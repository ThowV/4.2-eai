package infoborden;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ListenerStarter implements Runnable, ExceptionListener {
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String subject = "infoborden";

	private String selector = "";
	private Infobord infobord;
	private Berichten berichten;

	public ListenerStarter() {
	}

	public ListenerStarter(String selector, Infobord infobord, Berichten berichten) {
		this.selector = selector;
		this.infobord = infobord;
		this.berichten = berichten;
	}

	public void run() {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

			// Maak de connection aan
			Connection connection = connectionFactory.createConnection();
			connection.start();
			connection.setExceptionListener(this);

			// Maak de session aan
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Maak de destination aan
			Destination destination = session.createTopic(subject);

			// Maak de consumer aan
			MessageConsumer consumer = session.createConsumer(destination, selector);
			System.out.println("Produce, wait, consume " + selector);

			// Maak de Listener aan
			MessageListener listener = new QueueListener("infobordListener", infobord, berichten);
			consumer.setMessageListener(listener);
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured.  Shutting down client.");
	}
}