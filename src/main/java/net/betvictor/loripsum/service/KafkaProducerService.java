package net.betvictor.loripsum.service;

import net.betvictor.loripsum.setting.KafkaSettings;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaSettings kafkaSettings;

    public void produce(String message) {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaSettings.getBootstrapServer());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> firstProducer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaSettings.getTopic(), message);
        firstProducer.send(record);
        firstProducer.flush();
        firstProducer.close();
    }

}
