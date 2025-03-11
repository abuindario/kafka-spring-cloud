package application.dto;

import java.math.BigDecimal;

public class HotelDto {

	private String name;
	private String category;
	private BigDecimal price;
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
