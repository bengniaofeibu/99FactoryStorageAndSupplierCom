package com.qiyuan.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component //同样这里是必须的
public class KafkaCustomer {

//    @KafkaListener(topics = {"my-topic1"})
    public void receive(String message){
        System.out.println("topic========topic");
        System.out.println(message);
    }

    public static void main(String[] args) {
        KafkaCustomer customer=new KafkaCustomer();
        customer.receive("aaaa");
    }
}
