package com.darioabuin.booking.domain.api;

import java.util.List;

import com.darioabuin.booking.application.dto.HotelDto;

public interface KafkaHotelListener {
	List<HotelDto> getHotels();
}
