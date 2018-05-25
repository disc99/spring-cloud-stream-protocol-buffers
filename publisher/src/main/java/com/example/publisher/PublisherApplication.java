package com.example.publisher;

import com.example.task.TaskCreated;
import com.example.task.TaskDone;
import com.example.task.TaskStarted;
import com.example.message.ProtobufMessageConverter;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@SpringBootApplication
public class PublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublisherApplication.class, args);
	}

	@Bean
	@StreamMessageConverter
	public MessageConverter messageConverter() {
		return new ProtobufMessageConverter();
	}

	@EnableBinding(Source.class)
	@AllArgsConstructor
	@EnableScheduling
	class Publisher {

		Source source;

		@Scheduled(fixedDelay = 5000)
		public void create() {
			TaskCreated task = TaskCreated.newBuilder()
					.setId(UUID.randomUUID().toString())
					.setName("Task 1")
					.build();
			Message<TaskCreated> message = MessageBuilder.withPayload(task)
					.build();
			source.output().send(message);
			System.out.println("send message: " + message);
		}

		@Scheduled(fixedDelay = 4500)
		public void start() {
			TaskStarted task = TaskStarted.newBuilder()
					.setId(UUID.randomUUID().toString())
					.build();
			Message<TaskStarted> message = MessageBuilder.withPayload(task)
					.build();
			source.output().send(message);
			System.out.println("send message: " + message);
		}

		@Scheduled(fixedDelay = 4800)
		public void done() {
			TaskDone task = TaskDone.newBuilder()
					.setId(UUID.randomUUID().toString())
					.build();
			Message<TaskDone> message = MessageBuilder.withPayload(task)
					.build();
			source.output().send(message);
			System.out.println("send message: " + message);
		}
	}
}


