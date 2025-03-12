package com.darioabuin.booking.application.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class HotelDto {
	@Schema(name="idHotel", example="1")
	private Long idHotel;
	@Schema(name="name", example="Zenit")
	private String name;
	@Schema(name="category", example="3_stars")
	private String category;
	@Schema(name="price", example="50.22", description="value must be greater than 0")
	private BigDecimal price;
	@Schema(name="available", example="false")
	private Boolean available;

	public HotelDto(Long idHotel, String name, String category, BigDecimal price, Boolean available) {
		super();
		this.idHotel = idHotel;
		this.name = name;
		this.category = category;
		this.price = price;
		this.available = available;
	}
	
	public HotelDto() {}

	public Long getIdHotel() {
		return idHotel;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "HotelDto [idHotel=" + idHotel + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", available=" + available + "]";
	}

	public Boolean getAvailable() {
		return available;
	}

}
