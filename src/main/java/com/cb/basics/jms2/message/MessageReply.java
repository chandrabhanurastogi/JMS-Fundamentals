package com.cb.basics.jms2.message;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageReply {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();
        Queue responseQueue = (Queue) initialContext.lookup("queue/responseQueue");
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");


        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()
        ) {
            JMSProducer producer = jmsContext.createProducer();

            TextMessage textMessage = jmsContext.createTextMessage("I am producing this message");
            textMessage.setJMSReplyTo(responseQueue);
            producer.send(requestQueue, textMessage);
            System.out.println(textMessage.getJMSMessageID());

            JMSConsumer reqconsumer = jmsContext.createConsumer(requestQueue);
            Message receive = reqconsumer.receive();
            System.out.println(receive.getJMSMessageID());
            System.out.println("Message Received from request queue: " +receive.getBody(String .class));

            JMSProducer replyProducer = jmsContext.createProducer();
            TextMessage s = jmsContext.createTextMessage("Message was received. Thanks for sending it");
            s.setJMSCorrelationID(receive.getJMSMessageID());
            replyProducer.send(receive.getJMSReplyTo(), s);

            JMSConsumer reqConsumer = jmsContext.createConsumer(responseQueue);
            System.out.println("Message received from response queue: "+ reqConsumer.receive().getJMSCorrelationID());
        }
    }
}
