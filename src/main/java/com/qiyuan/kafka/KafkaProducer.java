package com.qiyuan.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.SimpleFormatter;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(){
        System.out.println("send data");
        kafkaTemplate.send("my-topic1","kafka data"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        //发送方式很多种可以自己研究一下
    }

    public static void main(String[] args) {
        KafkaProducer producer=new KafkaProducer();
        producer.send();
    }
}
