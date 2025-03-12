package application.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class HotelDto {

	@Schema(name="name", example="Zenit")
	private String name;
	@Schema(name="category", example="3_stars")
	private String category;
	@Schema(name="price", example="50.22", description="The value must be greater than 0")
	private BigDecimal price;
	@Schema(name="available", example="true", description="This is a booloean parameter")
	private Boolean available;
	
	public HotelDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HotelDto(String name, String category, BigDecimal price, Boolean available) {
		super();
		this.name = name;
		this.category = category;
		this.price = price;
		this.available = available;
	}

	@Override
	public String toString() {
		return "HotelDto [name=" + name + ", category=" + category + ", price=" + price + ", available=" + available
				+ "]";
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

	public Boolean isAvailable() {
		return available;
	}

}
