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
    // TODO hier de naam van de destination invullen
    private static String subject = "infoborden";

    private Session session;
    private Connection connection;
    private MessageProducer producer;

    public Producer() {
    }

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

        // TODO maak de connection aan
        connection = connectionFactory.createConnection();
        connection.start();

        // TODO maak de session aan

        // acknowledgeMode - when transacted is false, indicates how messages received
        // by the session will be acknowledged, except in the cases described above when
        // this value is ignored.
        // static final int AUTO_ACKNOWLEDGE = 1;
        // static final int CLIENT_ACKNOWLEDGE = 2;
        // static final int DUPS_OK_ACKNOWLEDGE = 3;
        // static final int SESSION_TRANSACTED = 0;
        // session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // TODO maak de destination aan (gebruik de subject variabele als naam)
        Destination destination = session.createTopic(subject);

        // TODO maak de producer aan
        producer = session.createProducer(destination);
    }

    private void sendTextMessage(String message) throws JMSException {
        // TODO maak de message aan
        TextMessage msg = new ActiveMQTextMessage();
        msg.setText(message);
        producer.send(msg);
    }
}
