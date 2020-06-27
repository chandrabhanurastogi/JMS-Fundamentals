package com.cb.basics.jms2;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsContextDemo {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        JMSContext jmsContext = cf.createContext()){

            jmsContext.createProducer().send(queue,"Arise awake and stop not till the goal is reach");
            String msgReceived = jmsContext.createConsumer(queue).receiveBody(String.class);

            System.out.println("Msg Received: "+msgReceived);
        }
    }
}
