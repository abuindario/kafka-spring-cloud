package infrastructure.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.dto.HotelDto;
import domain.model.Hotel;
import domain.spi.HotelRepositoryPort;

public class HotelJdbcRepository implements HotelRepositoryPort {

	private Connection conn;

	public HotelJdbcRepository(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public Hotel findByName(String hotelName) {
		String sql = "SELECT * FROM HOTEL WHERE HOTEL.NAME = ?";
		Hotel hotel = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, hotelName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				hotel = populateHotel(rs);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return hotel;
	}

	@Override
	public List<Hotel> findAll() {
		String sql = "SELECT * FROM HOTEL";
		List<Hotel> hotelList = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hotelList.add(populateHotel(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return hotelList;
	}

	private Hotel populateHotel(ResultSet rs) throws SQLException {
		return new Hotel(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getBoolean(5));
	}

	@Override
	public Long postHotel(HotelDto hotelDto) throws SQLException {
		String sql = "INSERT INTO HOTEL(name, category, price, available) VALUES (?, ?, ?, ?)";
		Long hotelId = null;
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, hotelDto.getName());
			ps.setString(2, hotelDto.getCategory());
			ps.setBigDecimal(3, hotelDto.getPrice());
			ps.setBoolean(4, hotelDto.isAvailable());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				hotelId = rs.getLong(1);
			}
		}
		return hotelId;
	}
	
	@Override
	public Hotel findById(Long hotelId) throws SQLException {
		String sql = "SELECT * FROM HOTEL WHERE HOTEL.ID = ?";
		Hotel hotel = null;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, hotelId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				hotel = populateHotel(rs);
			}
		}
		return hotel;
	}

}
