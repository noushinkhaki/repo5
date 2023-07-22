package net.betvictor.loripsum.service;

import net.betvictor.loripsum.model.Message;
import net.betvictor.loripsum.repository.MessageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {

    private static final Logger LOGGER = LogManager.getLogger(KafkaMessageConsumer.class);

    @Autowired
    MessageRepository messageRepository;

    @KafkaListener(topics = "words.processed", groupId = "test-consumer-group")
    public void consumeMessage(String message) {
        LOGGER.info("consuming the message: " + message);
        var messageEnt = new Message();
        messageEnt.setValue(message);
        // insert messages to h2 db
        messageRepository.save(messageEnt);
    }
}
