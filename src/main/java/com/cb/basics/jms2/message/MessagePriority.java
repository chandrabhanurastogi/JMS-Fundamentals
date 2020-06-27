package com.cb.basics.jms2.message;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagePriority {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        Queue queue = (Queue) context.lookup("queue/myQueue");


        try (
                ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                JMSContext jmsContext = cf.createContext()
        ){
            JMSProducer producer = jmsContext.createProducer();

            String [] messages = new String [] {"m1", "m2", "m3"};

//            producer.setPriority(3);
            producer.send(queue, messages[0]);

//            producer.setPriority(9);
            producer.send(queue, messages[1]);

//            producer.setPriority(1);
            producer.send(queue, messages[2]);

            JMSConsumer consumer = jmsContext.createConsumer(queue);

            Message message = consumer.receive();
            Message message1 = consumer.receive();
            Message message2 = consumer.receive();
            System.out.println("Received: "+message.getBody(String .class));
            System.out.println("Received: "+message1.getBody(String .class));
            System.out.println("Received: "+message2.getBody(String .class));
        }
    }
}
