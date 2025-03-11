package domain.api;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import domain.model.Hotel;

@Service
public class KafkaSenderImpl implements KafkaSender {
	@SuppressWarnings("rawtypes")
	@Autowired
	KafkaTemplate kafkaTemplate;
	
	@Override
	public void sendMessage(String message, Hotel hotel) {
		@SuppressWarnings("unchecked")
		CompletableFuture<SendResult<String, Hotel>> future = kafkaTemplate.send(message, hotel);
		future.whenComplete((res, error) -> {
			if(error != null) {
				error.printStackTrace();
			} else if(res != null) {
				System.out.println("New Hotel (" + res.getProducerRecord().value().toString() + ") sent to topic " + res.getRecordMetadata().topic());
			}
		});
	}
}
