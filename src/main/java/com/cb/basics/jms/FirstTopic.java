package com.cb.basics.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession();
        MessageProducer producer = session.createProducer(topic);
        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);

        TextMessage message = session.createTextMessage("All the power is within me. I can do anything");
        producer.send(message);
        connection.start();

        TextMessage message1 = (TextMessage) consumer1.receive();
        System.out.println("----------Consumer 1 message received: "+message1.getText());

        TextMessage message2 = (TextMessage) consumer2.receive();
        System.out.println("----------Consumer 2 message received: "+message2 .getText());

        connection.close();
        initialContext.close();
    }
}
