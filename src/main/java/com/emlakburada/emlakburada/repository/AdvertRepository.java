package com.emlakburada.emlakburada.repository;

import com.emlakburada.emlakburada.model.Advert;
import com.emlakburada.emlakburada.model.RealEstate;
import com.emlakburada.emlakburada.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdvertRepository {

    private static final List<Advert> advertList = new ArrayList<>();

    private static Advert prepareAdverts(int advertNo, String title) {
        Advert advert = new Advert();
        advert.setRealEstate(prepareRealEstate());
        advert.setAdvertNo(advertNo);
        advert.setTitle(title);
        advert.setPictures(pictureList());
        advert.setActive(true);
        return advert;
    }

    private static Advert prepareUserFavorites(int advertNo, String title) {
        User user = new User();
        Advert advert = new Advert();
        user.setUserId(5);
        advert.setUser(user);
        advert.setRealEstate(prepareRealEstate());
        advert.setAdvertNo(advertNo);
        advert.setTitle(title);
        advert.setPictures(pictureList());
        advert.setActive(true);
        return advert;
    }

    static {
        advertList.add(prepareAdverts(38164780, "Sahibinden Acil Satılık"));
        advertList.add(prepareAdverts(38164781, "Dosta GİDERRR ACİLLL!!!"));
        advertList.add(prepareAdverts(38164782, "Metroya Koşarak 5 dk"));
        advertList.add(prepareAdverts(38164783, "Öğrenciye ve Bekar uygun"));
    }

    private static List<String> pictureList() {
        List<String> picture = new ArrayList<>();
        picture.add("https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg");
        picture.add("https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg");
        return picture;
    }

    private static RealEstate prepareRealEstate() {
        return new RealEstate();
    }

    public Advert saveAdvert(Advert advert) {
        advertList.add(advert);
        return advert;
    }

    public List<Advert> findAllAdverts() {
        return advertList;
    }

    public Advert findAdvertByAdvertNo(int advertNo) {
        return advertList.stream().filter(advert -> advert.getAdvertNo() == advertNo).findAny().orElse(new Advert());
    }
}