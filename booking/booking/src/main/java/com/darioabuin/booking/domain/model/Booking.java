package com.darioabuin.booking.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class Booking {
	@Schema(name="idReserva", example="1")
	private Long idReserva;
	@Schema(name="customerName", example="Aitor Tilla")
	private String customerName;
	@Schema(name="dni", example="12312312A")
	private String Dni;
	@Schema(name="idHotel", example="1")
	private Long idHotel;
	@Schema(name="idVuelo", example="1")
	private Long idVuelo;

	public Booking() {
		super();
	}

	public Booking(Long idReserva, String customerName, String Dni, Long idHotel, Long idVuelo) {
		super();
		this.idReserva = idReserva;
		this.customerName = customerName;
		this.Dni = Dni;
		this.idHotel = idHotel;
		this.idVuelo = idVuelo;
	}
	
	@Override
	public String toString() {
		return "Booking IdReserva: " + this.idReserva + ", customer: " + this.customerName + ", dni: " + this.Dni + ", idHotel: " + this.idHotel + ", idVuelo: " + this.idVuelo ;
	}

	public Long getIdReserva() {
		return idReserva;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getDNI() {
		return Dni;
	}

	public Long getIdHotel() {
		return idHotel;
	}

	public Long getIdVuelo() {
		return idVuelo;
	}

}
