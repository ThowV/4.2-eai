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
import org.apache.activemq.command.ActiveMQTextMessage;

import javafx.scene.text.Text;

public class Producer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // private static String url = "failover://tcp://localhost:8081";
    // private static String url = "tcp://localhost:8081";

    // Hier de naam van de destination invullen
    private static String subject = "busInfo";

    private Session session;
    private Connection connection;
    private MessageProducer producer;

    public Producer() {
    }

    /**
     * Send a message to the ActiveMQ broker.
     * 
     * @param bericht The message in XML format.
     */
    public void sendBericht(String bericht) {
        try {
            createConnection();
            sendTextMessage(bericht);
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void createConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url); // ConnectionServiceJSONSingletonBEANZMethodFactoryFactory

        // Maak de connection aan
        connection = connectionFactory.createConnection();
        connection.start();

        // Maak de session aan
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Maak de destination aan (gebruik de subject variabele als naam)
        Destination destination = session.createQueue(subject);

        // Maak de producer aan
        producer = session.createProducer(destination);
    }

    private void sendTextMessage(String message) throws JMSException {
        // Maak de message aan
        TextMessage msg = new ActiveMQTextMessage();
        msg.setText(message);
        producer.send(msg);
    }
}
