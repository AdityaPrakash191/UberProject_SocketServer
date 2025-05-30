package com.example.UberSocketServer.producers;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate template){
        this.kafkaTemplate = template;
    }

    public void publishMessage(String topic,String message){
        kafkaTemplate.send(topic,message);
    }
}
