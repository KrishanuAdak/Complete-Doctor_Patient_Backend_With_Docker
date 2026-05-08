package com.example.demo1.configuration;
import java.util.HashMap;
import org.springframework.kafka.annotation.EnableKafka;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import com.example.demo1.deserializer.GenericDeserializer;
import com.example.demo1.model.Patient_Details_From_Admin;
//@Configuration
//@Configuration
//@EnableKafka
//public class KafkaConsumerConfiguration {
//	
//	
//
//	    @Bean
//	    public ConsumerFactory<String, String> consumerFactory() {
//	        Map<String, Object> props = new HashMap<>();
//	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // WSL Kafka broker
//	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group-new");
//	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//	        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // so it reads old messages
//	        return new DefaultKafkaConsumerFactory<>(props);
//	    }
//
//	    @Bean
//	    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//	        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//	        factory.setConsumerFactory(consumerFactory());
//	        return factory;
//	    }
//	}
//
//	
////    @Bean
////    public ConsumerFactory<String, Patient_Details_From_Admin> consumerFactory() {
////        Map<String, Object> props = new HashMap<>();
////        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
////        props.put(ConsumerConfig.GROUP_ID_CONFIG, "admin-group");
////        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GenericDeserializer.class.getName());
////        props.put("spring.deserializer.value.delegate.class", Patient_Details_From_Admin.class.getName());
////
////        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new GenericDeserializer<>(Patient_Details_From_Admin.class));
////    }
////
////    @Bean
////    public ConcurrentKafkaListenerContainerFactory<String,Patient_Details_From_Admin> kafkaListenerContainerFactory() {
////        ConcurrentKafkaListenerContainerFactory<String, Patient_Details_From_Admin> factory = new ConcurrentKafkaListenerContainerFactory<>();
////        factory.setConsumerFactory(consumerFactory());
////        return factory;
////    }
//
//
