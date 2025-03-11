package itest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.connection.ConnectionHolderImpl;
import com.github.database.rider.junit5.DBUnitExtension;

import application.controller.HotelController;
import application.dto.HotelDto;
import domain.api.HotelServiceImpl;
import domain.model.Hotel;
import infrastructure.jdbc.HotelJdbcRepository;

@ExtendWith(DBUnitExtension.class)
@DataSet("initialhotels.yml")
class HotelITest {
	
	@SuppressWarnings("unused")
	private ConnectionHolder connectionHolder;
	private Connection conn;
	private HotelController hotelController;

	@BeforeEach
	void setup() throws SQLException {
		conn = DriverManager.getConnection("jdbc:h2:mem:test;INIT=runscript from 'classpath:hotel-schema.sql'", "sa", "");
		connectionHolder = new ConnectionHolderImpl(conn);
		hotelController = new HotelController(new HotelServiceImpl(new HotelJdbcRepository(conn)));
	}
	
	@Test
	void shouldGetHotelDetails_givenHotelName() {
		// GIVEN
		Hotel expectedHotel = populateHotel(1);
		String hotelName = "RIU PALACE";
		
		// WHEN 
		ResponseEntity<Hotel> response = hotelController.findByName(hotelName);
		Hotel actualHotel = response.getBody();
		
		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(actualHotel);
		assertEquals(expectedHotel.getIdHotel(), actualHotel.getIdHotel());
		assertEquals(expectedHotel.getName(), actualHotel.getName());
		assertEquals(expectedHotel.getCategory(), actualHotel.getCategory());
		assertEquals(0, expectedHotel.getPrice().compareTo(actualHotel.getPrice()));
		assertTrue(actualHotel.isAvailable());
	}
	
	@Test
	void shouldFailGettingHotelDetails_unexistingHotelName() {
		// GIVEN
		String hotelName = "RIO PALACIO";
		
		// WHEN 
		ResponseEntity<Hotel> response = hotelController.findByName(hotelName);
		
		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void shouldFailGettingHotelDetails_unexistingHotelName(String hotelName) {
		// GIVEN
		// provided by annotation
		
		// WHEN 
		ResponseEntity<Hotel> response = hotelController.findByName(hotelName);
		
		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void shouldGetHotelList() {
		// GIVEN
		Hotel h1 = populateHotel(1);
		Hotel h2 = populateHotel(2);
		
		// WHEN
		ResponseEntity<List<Hotel>> response = hotelController.getAllHotels();
		List<Hotel> hotelList = response.getBody();
		
		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(hotelList);
		assertEquals(2, hotelList.size(), "Not the expected result list");
		assertEquals(h1.getIdHotel(), hotelList.get(0).getIdHotel());
		assertEquals(h1.getName(), hotelList.get(0).getName());
		assertEquals(h1.getCategory(), hotelList.get(0).getCategory());
		assertEquals(0, h1.getPrice().compareTo(hotelList.get(0).getPrice()));
		assertEquals(h1.isAvailable(), hotelList.get(0).isAvailable());
		assertEquals(h2.getIdHotel(), hotelList.get(1).getIdHotel());
		assertEquals(h2.getName(), hotelList.get(1).getName());
		assertEquals(h2.getCategory(), hotelList.get(1).getCategory());
		assertEquals(0, h2.getPrice().compareTo(hotelList.get(1).getPrice()));
		assertEquals(h2.isAvailable(), hotelList.get(1).isAvailable());
	}
	
	@Test
	@ExpectedDataSet("expectedHotelsAfterPost.yml")
	void shouldCreateHotel() {
		// GIVEN
		HotelDto hotelDto = populateHotelDto();

		// WHEN
		@SuppressWarnings("unchecked")
		ResponseEntity<Hotel> response = (ResponseEntity<Hotel>) hotelController.postHotel(hotelDto);
		
		// THEN
		assertNotNull(response, "Response wasn't expected to be null");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Hotel actualHotel = response.getBody();
		assertNotNull(actualHotel, "Response body wasn't expected to be null");
		assertEquals(hotelDto.getName(), actualHotel.getName());
		assertEquals(hotelDto.getCategory(), actualHotel.getCategory());
		assertEquals(Double.valueOf(hotelDto.getPrice().toString()), Double.valueOf(actualHotel.getPrice().toString()), 0.001);
		assertEquals(hotelDto.isAvailable(), actualHotel.isAvailable());
	}

	private HotelDto populateHotelDto() {
		HotelDto hotelDto = new HotelDto("Ritz Madrid", "5 stars", BigDecimal.valueOf(139.50), false);
		return hotelDto;
	}
	
	@ParameterizedTest
	@MethodSource("wrongHotelDtoArguments")
	void shouldFailCreateHotel_wrongHotelDto(HotelDto hotelDto) {
		// GIVEN

		// WHEN
		@SuppressWarnings("unchecked")
		ResponseEntity<String> response = (ResponseEntity<String>) hotelController.postHotel(hotelDto);
		
		// THEN
		assertNotNull(response, "Response wasn't expected to be null");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Invalid Hotel data, please provide a valid Hotel name, category, price and availability.", response.getBody());
	}
	
	@ParameterizedTest
	@NullSource
	void shouldFailCreateHotel_nullHotelDto(HotelDto hotelDto) {
		// GIVEN

		// WHEN
		@SuppressWarnings("unchecked")
		ResponseEntity<String> response = (ResponseEntity<String>) hotelController.postHotel(hotelDto);
		
		// THEN
		assertNotNull(response, "Response wasn't expected to be null");
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Invalid Hotel data, please provide a valid Hotel name, category, price and availability.", response.getBody());
	}
	
	@Test
	void shouldFailCreateHotel_DatabaseException() throws SQLException {
		// GIVEN
		HotelDto hotelDto = populateHotelDto();
		HotelJdbcRepository mockHotelJdbcRepository = mock(HotelJdbcRepository.class);
		HotelController hotelController_mockHotelJdbcRepository = new HotelController(new HotelServiceImpl(mockHotelJdbcRepository));

		// WHEN
		when(mockHotelJdbcRepository.postHotel(hotelDto)).thenThrow(SQLException.class);
		@SuppressWarnings("unchecked")
		ResponseEntity<String> response = (ResponseEntity<String>) hotelController_mockHotelJdbcRepository.postHotel(hotelDto);
		
		// THEN
		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Error posting Hotel.", response.getBody());
	}
	
	private Hotel populateHotel(int id) {
		if(id == 1) {
			return new Hotel(Long.valueOf(10), "RIU PALACE", "5 stars", BigDecimal.valueOf(123.50), true);
		} else if(id == 2) {
			return new Hotel(Long.valueOf(20), "IBIS Málaga", "2 stars", BigDecimal.valueOf(49.50), true);
		}
		return null;
	}
	
	private static Stream<Arguments> wrongHotelDtoArguments() {
		return Stream.of(Arguments.of(new HotelDto("Ritz Madrid", null, BigDecimal.valueOf(139.50), false)),
				Arguments.of(new HotelDto("Ritz Madrid", "5 stars", null, false)),
				Arguments.of(new HotelDto(null, "5 stars", BigDecimal.valueOf(139.50), false)),
				Arguments.of(new HotelDto(" ", "5 stars", BigDecimal.valueOf(139.50), false)),
				Arguments.of(new HotelDto("Ritz", " ", BigDecimal.valueOf(139.50), false)),
				Arguments.of(new HotelDto("Ritz", "5 stars", BigDecimal.valueOf(0), false)),
				Arguments.of(new HotelDto("Ritz", "5 stars", BigDecimal.valueOf(-10), false))
				);
	}
}
