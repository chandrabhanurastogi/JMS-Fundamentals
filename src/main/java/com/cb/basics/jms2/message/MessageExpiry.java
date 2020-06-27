package com.cb.basics.jms2.message;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageExpiry {

    public static void main(String[] args) throws NamingException, InterruptedException {
        InitialContext context = new InitialContext();
        Queue reqQueue = (Queue) context.lookup("queue/requestQueue");
        Queue expiryQueue = (Queue) context.lookup("queue/expiryQueue");


        try(
                ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                JMSContext jmsContext = cf.createContext();
        ){
            JMSProducer producer = jmsContext.createProducer();
            producer.setTimeToLive(2000);
            producer.send(reqQueue, "Hi, Let's test message expiry");
            Thread.sleep(5000);

            JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
            Message receivedMessage = consumer.receive(2000);
            System.out.println(receivedMessage);

            System.out.println(jmsContext.createConsumer(expiryQueue).receive());
        }
    }
}
