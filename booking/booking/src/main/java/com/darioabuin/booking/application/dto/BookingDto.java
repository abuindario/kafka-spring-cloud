package com.darioabuin.booking.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookingDto {
	@Schema(name="customerName", example="Aitor Tilla")
	private String customerName;
	@Schema(name="dni", example="12312312A")
	private String dni;
	@Schema(name="numberOfSeats", example="12")
	private int numberOfSeats;
	@Schema(name="idHotel", example="1")
	private Long idHotel;
	@Schema(name="idVuelo", example="1")
	private Long idVuelo;

	public BookingDto(String customerName, String dni, int numberOfSeats, Long idHotel, Long idVuelo) {
		super();
		this.customerName = customerName;
		this.dni = dni;
		this.numberOfSeats = numberOfSeats;
		this.idHotel = idHotel;
		this.idVuelo = idVuelo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getDni() {
		return dni;
	}

	public Long getIdHotel() {
		return idHotel;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public Long getIdVuelo() {
		return idVuelo;
	}

	@Override
	public String toString() {
		return "BookingDto [customerName=" + customerName + ", dni=" + dni + ", numberOfSeats=" + numberOfSeats
				+ ", idHotel=" + idHotel + ", idVuelo=" + idVuelo + "]";
	}

}
