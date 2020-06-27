package com.cb.basics.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            TextMessage message = session.createTextMessage("I am the creator of my destiny");
            MessageProducer producer = session.createProducer(queue);
            producer.send(message);
            System.out.println("-----------------------Message send: "+message.getText());

//            MessageConsumer consumer = session.createConsumer(queue);
//            connection.start();
//            TextMessage msgReceived = (TextMessage) consumer.receive();
//            System.out.println("------------------Message Received : "+msgReceived.getText());
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
