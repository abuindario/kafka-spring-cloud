package domain.spi;

import java.sql.SQLException;
import java.util.List;

import application.dto.HotelDto;
import domain.model.Hotel;

public interface HotelRepositoryPort {

	List<Hotel> findAll();

	Hotel findByName(String hotelName);

	Long postHotel(HotelDto hotel) throws SQLException;

	Hotel findById(Long hotelId) throws SQLException;
}
