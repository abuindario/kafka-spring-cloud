package com.darioabuin.booking.domain.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.darioabuin.booking.application.dto.HotelDto;

@Service
public class KafkaHotelListenerImpl implements KafkaHotelListener {
	private List<HotelDto> hotelList = new ArrayList<>();

	@KafkaListener(topics="hotelsTopic", groupId="myGroup")
	public void listenTopic(HotelDto hotelDto) {
		System.out.println("New Hotel found at topic 'hotelsTopic': " + hotelDto.toString());
		hotelList.add(hotelDto);
	}
	
	@Override
	public List<HotelDto> getHotels() {
		return hotelList;
	}

}
