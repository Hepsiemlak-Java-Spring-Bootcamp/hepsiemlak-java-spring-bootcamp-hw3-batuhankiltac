package emlakburada.controller;

import emlakburada.dto.request.AdvertRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class AdvertController {
	private final AdvertService advertService;

	@Autowired
	public AdvertController(AdvertService advertService) {
		this.advertService = advertService;
	}

	@PostMapping(value = "/adverts")
	public ResponseEntity<AdvertResponse> createAdvert(@RequestBody AdvertRequest request) {
		return new ResponseEntity<>(advertService.createAdvert(request), HttpStatus.CREATED);
	}

	@GetMapping(value = "/adverts")
	public ResponseEntity<List<AdvertResponse>> getAllAdverts() {
		return new ResponseEntity<>(advertService.getAllAdverts(), HttpStatus.OK);
	}

	@GetMapping(value = "/adverts/{advertNo}")
	public ResponseEntity<AdvertResponse> getAdvertByAdvertNo(@PathVariable(required = false) int advertNo) {
		return new ResponseEntity<>(advertService.getAdvertByAdvertNo(advertNo), HttpStatus.OK);
	}

	@GetMapping(value = "/favorites")
	public ResponseEntity<Set<AdvertResponse>> getAllFavorites() {
		return new ResponseEntity<>(advertService.getAllFavorites(), HttpStatus.OK);
	}

	@GetMapping(value = "/favorites/{userId}")
	public ResponseEntity<Set<AdvertResponse>> getAdvertByUserId(@PathVariable int userId) {
		return new ResponseEntity<>(advertService.getAdvertByUserId(userId), HttpStatus.OK);
	}
}