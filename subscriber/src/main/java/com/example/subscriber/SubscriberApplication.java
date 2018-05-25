package com.example.subscriber;

import com.example.task.TaskCreated;
import com.example.task.TaskDone;
import com.example.task.TaskStarted;
import com.example.message.ProtobufMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;

@SpringBootApplication
public class SubscriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriberApplication.class, args);
	}

	@Bean
	@StreamMessageConverter
	public MessageConverter messageConverter() {
		return new ProtobufMessageConverter();
	}

	@EnableBinding(Sink.class)
	class Subscriber {

		@StreamListener(Sink.INPUT)
		void handle(TaskCreated message) {
			System.out.println(message);
		}

		@StreamListener(Sink.INPUT)
		void handle(TaskStarted message) {
			System.out.println(message);
		}

		@StreamListener(Sink.INPUT)
		void handle(TaskDone message) {
			System.out.println(message);
		}
	}
}
