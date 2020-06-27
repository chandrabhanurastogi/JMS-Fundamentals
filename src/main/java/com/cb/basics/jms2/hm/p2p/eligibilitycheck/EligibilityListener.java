package com.cb.basics.jms2.hm.p2p.eligibilitycheck;

import com.cb.basics.jms2.hm.p2p.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EligibilityListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("----------------");
        ObjectMessage objMsg = (ObjectMessage) message;
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()
        ){
            InitialContext context = new InitialContext();
            Queue responseQueue = (Queue) context.lookup("queue/responseQueue");
            Patient patient = (Patient) objMsg.getObject();
            System.out.println("Patient name is: "+patient.getName());

            MapMessage mapMessage = jmsContext.createMapMessage();
            if(patient.getInsuranceProvider().contains("Blue")){
                if(patient.getCopay() > 40d || patient.getAmountToBePaid() > 200d){
                    mapMessage.setBoolean("eligible", true);
                }else
                    mapMessage.setBoolean("eligible", false);
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
