package com.cb.basics.jms2.hm.p2p.clinicals;

import com.cb.basics.jms2.hm.p2p.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClinicalApp {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        Queue reqQueue = (Queue) context.lookup("queue/requestQueue");
        Queue resQueue = (Queue) context.lookup("queue/responseQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()
        ){
            JMSProducer producer = jmsContext.createProducer();
            ObjectMessage objMsg = jmsContext.createObjectMessage();
            Patient patient = new Patient(1, "Bob", "Blue Cross", 30d, 500d);
            objMsg.setObject(patient);
            producer.send(reqQueue, objMsg);

            JMSConsumer consumer =  jmsContext.createConsumer(resQueue);
            MapMessage message = (MapMessage) consumer.receive(30000);
            System.out.println("Patient eligibility is: "+message.getBoolean("eligible"));
        }
    }
}
