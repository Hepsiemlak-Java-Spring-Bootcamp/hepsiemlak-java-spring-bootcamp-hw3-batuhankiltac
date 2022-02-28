package com.emlakburada.emlakburada.client;

import com.emlakburada.emlakburada.client.request.BannerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feign-client", url = "http://localhost:8083")
public interface BannerClient {

    @PostMapping(value = "/banners")
    void saveBanner(@RequestBody BannerRequest bannerRequest);
}