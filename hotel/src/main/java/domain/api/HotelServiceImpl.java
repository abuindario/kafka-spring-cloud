package domain.api;

import java.sql.SQLException;
import java.util.List;

import application.dto.HotelDto;
import domain.model.Hotel;
import domain.spi.HotelRepositoryPort;

public class HotelServiceImpl implements HotelService {

	private HotelRepositoryPort hotelRepositoryPort;

	public HotelServiceImpl(HotelRepositoryPort hotelRepositoryPort) {
		this.hotelRepositoryPort = hotelRepositoryPort;
	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepositoryPort.findAll();
	}

	@Override
	public Hotel findByName(String hotelName) {
		return hotelRepositoryPort.findByName(hotelName);
	}

	@Override
	public Hotel postHotel(HotelDto hotelDto) throws SQLException {
		if(!isValidHotelDto(hotelDto)) {
			return null;
		}
		return hotelRepositoryPort.findById(hotelRepositoryPort.postHotel(hotelDto));
	}

	private boolean isValidHotelDto(HotelDto hotelDto) {
		if (hotelDto == null || hotelDto.getName() == null || hotelDto.getCategory() == null || hotelDto.getPrice() == null
				|| hotelDto.getName().isBlank() || hotelDto.getCategory().isBlank()
				|| hotelDto.getPrice().doubleValue() <= 0) {
			return false;
		} else {
			return true;
		}
	}

}
