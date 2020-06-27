package com.cb.basics.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class QueueBrowserDemo {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            TextMessage message1 = session.createTextMessage("message 1");
            TextMessage message2= session.createTextMessage("message 2");
            TextMessage message3 = session.createTextMessage("message 3");
            MessageProducer producer = session.createProducer(queue);
            producer.send(message1);
            producer.send(message2);
            producer.send(message3);

            QueueBrowser browser = session.createBrowser(queue);
            Enumeration messages = browser.getEnumeration();
            while (messages.hasMoreElements()){
                TextMessage msg = (TextMessage) messages.nextElement();
                System.out.println("Browsing: "+msg.getText());
            }
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            TextMessage msgReceived1 = (TextMessage) consumer.receive();
            System.out.println("message received: "+msgReceived1.getText());
            TextMessage msgReceived2 = (TextMessage) consumer.receive();
            System.out.println("message received: "+msgReceived2.getText());
            TextMessage msgReceived3 = (TextMessage) consumer.receive();
            System.out.println("message received: "+msgReceived3.getText());
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(initialContext != null){
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
