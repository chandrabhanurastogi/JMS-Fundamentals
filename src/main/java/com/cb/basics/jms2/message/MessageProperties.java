package com.cb.basics.jms2.message;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageProperties {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        Queue reqQueue = (Queue) context.lookup("queue/requestQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()
        ){
            JMSProducer producer = jmsContext.createProducer();
            TextMessage textMessage = jmsContext.createTextMessage("This message will have some custom properties");
            textMessage.setBooleanProperty("HasCustomProp", true);
            textMessage.setFloatProperty("floatProperty", 3.19F);
            producer.send(reqQueue, textMessage);

            TextMessage receiveBody = (TextMessage) jmsContext.createConsumer(reqQueue).receive();
            System.out.println(receiveBody.getText());
            System.out.println(receiveBody.getFloatProperty("floatProperty"));
            System.out.println(receiveBody.getBooleanProperty("HasCustomProp"));
        }
    }
}
