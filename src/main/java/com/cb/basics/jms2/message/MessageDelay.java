package com.cb.basics.jms2.message;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalTime;

public class MessageDelay {

    public static void main(String[] args) throws NamingException {
        InitialContext context = new InitialContext();
        Queue reqQueue = (Queue) context.lookup("queue/requestQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()
        ){
            JMSProducer producer = jmsContext.createProducer();
            producer.setDeliveryDelay(3000);
            producer.send(reqQueue, "This message will be delivered after 3 second from: "+LocalTime.now());

            String receiveBody = jmsContext.createConsumer(reqQueue).receiveBody(String.class);
            System.out.println(receiveBody+ " received at : "+LocalTime.now());
        }
    }
}
