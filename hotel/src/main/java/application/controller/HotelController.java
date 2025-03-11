package application.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import application.dto.HotelDto;
import domain.api.HotelService;
import domain.api.KafkaSender;
import domain.model.Hotel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin
public class HotelController {
	@Value("${topic}")
	private String topic;
	
	@Autowired
	private KafkaSender kafkaSender;
	
	private HotelService hotelService;
	
	public HotelController(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	@Operation(summary="Find all Hotels", description="This method returns a ResponseEntity object that includes in its body a list of all Hotels registered.")
	@GetMapping("/hotels")
	public ResponseEntity<List<Hotel>> getAllHotels() {
		return ResponseEntity.ok(hotelService.getAllHotels());
	}

	@Operation(summary="Find a Hotel filtering by its name",
			responses= {@ApiResponse(responseCode = "200", description="This method returns a ResponseEntity object that includes in its body all the details of a specific Hotel."),
						@ApiResponse(responseCode = "404", description="404 error will be returned if no Hotel was found with the given name.", content=@Content())})
	@GetMapping("/hotels/{name}")
	public ResponseEntity<Hotel> findByName(@PathVariable("name") String hotelName) {
			return ResponseEntity.ofNullable(hotelService.findByName(hotelName));
	}

	@PostMapping("/hotels/new")
	public ResponseEntity<?> postHotel(@RequestBody HotelDto hotelDto) {
		Hotel hotel = null;
		try {
			hotel = hotelService.postHotel(hotelDto);
		} catch(SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error posting Hotel.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(hotel != null) {
			kafkaSender.sendMessage(topic, hotel);
			return ResponseEntity.ok(hotel);
		} else {
			return new ResponseEntity<String>("Invalid Hotel data, please provide a valid Hotel name, category, price and availability.", HttpStatus.BAD_REQUEST);
		}
	}

}
