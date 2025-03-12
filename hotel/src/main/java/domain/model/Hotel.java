package domain.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class Hotel {

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
	
	public Hotel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hotel(Long idHotel, String name, String category, BigDecimal price, Boolean available) {
		super();
		this.idHotel = idHotel;
		this.name = name;
		this.category = category;
		this.price = price;
		this.available = available;
	}

	public Long getIdHotel() {
		return idHotel;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Hotel [idHotel=" + idHotel + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", available=" + available + "]";
	}

	public String getCategory() {
		return category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Boolean isAvailable() {
		return available;
	}

}
