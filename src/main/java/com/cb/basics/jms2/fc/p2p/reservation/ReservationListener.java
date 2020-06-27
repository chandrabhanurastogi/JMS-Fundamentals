package com.cb.basics.jms2.fc.p2p.reservation;

import com.cb.basics.jms2.fc.p2p.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReservationListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        ObjectMessage objMsg = (ObjectMessage) message;
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()
        ){
            InitialContext context = new InitialContext();
            Queue responseQueue = (Queue) context.lookup("queue/responseQueue");
            Passenger passenger = (Passenger) objMsg.getObject();
            System.out.println("Passenger name is: "+passenger.getFirstName());

            MapMessage mapMessage = jmsContext.createMapMessage();
            if(passenger.getFirstName().contains("Blue")){
//                if(passenger.getCopay() > 40d || passenger.getAmountToBePaid() > 200d){
//                    mapMessage.setBoolean("eligible", true);
//                }else
//                    mapMessage.setBoolean("eligible", false);
            }
            Thread.sleep(5000);
            JMSProducer producer = jmsContext.createProducer();
            producer.send(responseQueue, mapMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
