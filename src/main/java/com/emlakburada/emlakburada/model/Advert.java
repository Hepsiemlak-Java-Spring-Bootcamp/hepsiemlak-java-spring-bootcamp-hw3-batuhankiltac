package com.emlakburada.emlakburada.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advert {
    private int userId;
    private RealEstate realEstate;
    private User user;
    private int advertNo;
    private String title;
    private long price;
    private List<String> pictures;
    private int duration;
    private boolean isPromotion = false;
    private boolean isChecked = false;
    private boolean isActive = false;
    private Date creationDate;

    public Advert(RealEstate realEstate, User user, int advertNo, String title) {
        this.realEstate = realEstate;
        this.user = user;
        this.advertNo = advertNo;
        this.title = title;
    }
}