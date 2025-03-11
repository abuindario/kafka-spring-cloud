package domain.api;

import java.sql.SQLException;
import java.util.List;

import application.dto.HotelDto;
import domain.model.Hotel;

public interface HotelService {

	List<Hotel> getAllHotels();

	Hotel findByName(String hotelName);

	Hotel postHotel(HotelDto hotel) throws SQLException;

}
