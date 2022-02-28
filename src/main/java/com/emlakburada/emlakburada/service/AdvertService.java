package com.emlakburada.emlakburada.service;

import com.emlakburada.emlakburada.client.BannerClient;
import com.emlakburada.emlakburada.client.request.BannerRequest;
import com.emlakburada.emlakburada.config.EmailConfig;
import com.emlakburada.emlakburada.dto.request.AdvertRequest;
import com.emlakburada.emlakburada.dto.response.AdvertResponse;
import com.emlakburada.emlakburada.model.Advert;
import com.emlakburada.emlakburada.model.RealEstate;
import com.emlakburada.emlakburada.model.User;
import com.emlakburada.emlakburada.queue.QueueService;
import com.emlakburada.emlakburada.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final QueueService queueService;
    private final BannerClient bannerClient;

    @Autowired
    public AdvertService(AdvertRepository advertRepository, QueueService queueService, BannerClient bannerClient) {
        this.advertRepository = advertRepository;
        this.queueService = queueService;
        this.bannerClient = bannerClient;
    }

    private static int advertNo = 38164784;
    private static final BannerRequest bannerRequest = new BannerRequest();

    public AdvertResponse convertToAdvertResponse(Advert savedAdvert) {
        AdvertResponse response = new AdvertResponse();
        response.setTitle(savedAdvert.getTitle());
        response.setPrice(savedAdvert.getPrice());
        response.setAdvertNo(savedAdvert.getAdvertNo());
        return response;
    }

    public Advert convertToAdvert(AdvertRequest request) {
        Advert advert = new Advert(new RealEstate(), new User(), advertNo, request.getTitle());
        advertNo++;
        advert.setAdvertNo(advertNo);
        advert.setTitle(request.getTitle());
        advert.setPrice(request.getPrice());
        return advert;
    }

    public AdvertResponse createAdvert(AdvertRequest advertRequest) {
        Advert savedAdvert = advertRepository.saveAdvert(convertToAdvert(advertRequest));
        EmailService emailService = new EmailService(new EmailConfig());
        queueService.sendMessage(emailService);
        bannerClient.saveBanner(bannerRequest);
        return convertToAdvertResponse(savedAdvert);
    }

    public List<AdvertResponse> getAllAdverts() {
        List<AdvertResponse> advertList = new ArrayList<>();
        for (Advert advert : advertRepository.findAllAdverts()) {
            advertList.add(convertToAdvertResponse(advert));
        }
        return advertList;
    }

    public AdvertResponse getAdvertByAdvertNo(int advertNo) {
        Advert advert = advertRepository.findAdvertByAdvertNo(advertNo);
        return convertToAdvertResponse(advert);
    }
}