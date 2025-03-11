package domain.api;

import domain.model.Hotel;

public interface KafkaSender {
	void sendMessage(String message, Hotel hotel);
}
