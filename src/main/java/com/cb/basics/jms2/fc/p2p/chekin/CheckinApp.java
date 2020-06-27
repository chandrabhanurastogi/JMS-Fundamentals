package com.cb.basics.jms2.fc.p2p.chekin;

import com.cb.basics.jms2.fc.p2p.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CheckinApp {
    public static void main(String[] args) throws NamingException, JMSException {
        {
            InitialContext context = new InitialContext();
            Queue reqQueue = (Queue) context.lookup("queue/requestQueue");
            Queue resQueue = (Queue) context.lookup("queue/responseQueue");

            try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                JMSContext jmsContext = cf.createContext()
            ){
                JMSProducer producer = jmsContext.createProducer();
                ObjectMessage objMsg = jmsContext.createObjectMessage();
                Passenger passenger= new Passenger(1, "Bob", "Smith", "bob.smith@gmail.com", 123456789);
                objMsg.setObject(passenger);
                producer.send(reqQueue, objMsg);

                JMSConsumer consumer =  jmsContext.createConsumer(resQueue);
                MapMessage message = (MapMessage) consumer.receive(30000);
                System.out.println("Patient eligibility is: "+message.getBoolean("eligible"));
            }
        }
    }
}
