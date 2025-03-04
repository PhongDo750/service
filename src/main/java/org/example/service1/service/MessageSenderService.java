package org.example.service1.service;

import jakarta.jms.*;
import lombok.AllArgsConstructor;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.service1.dto.ApiResponse;
import org.example.service1.entity.MessageEntity;
import org.example.service1.exceptionhandler.AppException;
import org.example.service1.exceptionhandler.ErrorCode;
import org.example.service1.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MessageSenderService {
    //URL of the JMS server.
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String jmsQueue = "example_QUEUE_10";

    private final MessageRepository messageRepository;

    @Transactional
    public void sendMessage() throws JMSException {
        // Getting JMS connection from the server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Creating a session to send/receive JMS message.
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        Destination destination = session.createQueue(jmsQueue);

        MessageProducer producer = session.createProducer(destination);

//        Topic topic = session.createTopic(jmsQueue);
//        MessageProducer producer = session.createProducer(topic);
//        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // We will send a small text message with small Json'
        TextMessage message = session
                .createTextMessage("Tin nhắn");

        message.setIntProperty("orderId", 1);
        // Here we are sending our message!
        for (int i = 0; i < 4; i++) {
            MessageEntity messageEntity = MessageEntity.builder()
                    .message(message.getText())
                    .build();
            messageRepository.save(messageEntity);
            producer.send(message);
            System.out.println("JMS Message Sent successfuly:: " + message.getText());
        }
        session.commit();
    }

    @Transactional
    public String getMessage() throws JMSException {
        return messageRepository.findAll().get(0).getMessage();
    }
}
