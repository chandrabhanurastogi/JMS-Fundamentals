package com.cb.basics.jms2.message;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageType {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        Queue reqQueue = (Queue) context.lookup("queue/requestQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()
        ){
            JMSProducer producer = jmsContext.createProducer();

            ObjectMessage objectMessage = jmsContext.createObjectMessage();
            objectMessage.setObject(new Person("chandra", "bhanu"));
            producer.send(reqQueue, objectMessage);


            ObjectMessage receiveObject = (ObjectMessage) jmsContext.createConsumer(reqQueue).receive(5000);
            Person person = (Person) receiveObject.getObject();
            System.out.println(person.getfName());
        }
    }
}
