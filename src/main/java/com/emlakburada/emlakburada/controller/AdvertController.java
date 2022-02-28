package com.emlakburada.emlakburada.controller;

import com.emlakburada.emlakburada.dto.request.AdvertRequest;
import com.emlakburada.emlakburada.dto.response.AdvertResponse;
import com.emlakburada.emlakburada.model.Advert;
import com.emlakburada.emlakburada.service.AdvertService;
import com.emlakburada.emlakburada.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class AdvertController {
    private final AdvertService advertService;
    private final UserService userService;

    @Autowired
    public AdvertController(AdvertService advertService, UserService userService) {
        this.advertService = advertService;
        this.userService = userService;
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

    @GetMapping(value = "/adverts/{userId}")
    public ResponseEntity<List<AdvertResponse>> getFavouriteAdverts(@PathVariable int userId) {
        Set<Advert> favouriteAdverts = userService.getUserFavoritesById(userId).getFavorites();
        List<AdvertResponse> adverts = new ArrayList<>();
        for (Advert advert : favouriteAdverts) {
            adverts.add(advertService.convertToAdvertResponse(advert));
        }
        return new ResponseEntity<>(adverts , HttpStatus.OK);
    }
}