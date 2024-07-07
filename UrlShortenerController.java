package com.inu.url_shortner_service.controller;

import com.inu.url_shortner_service.exceptions.InvalidUrlException;
import com.inu.url_shortner_service.request.UrlRequest;
import com.inu.url_shortner_service.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/getShortUrl")
    public ResponseEntity<?> shortenUrl(@RequestBody UrlRequest urlRequest){
        try {
            String shortUrl  = urlShortenerService.shortenUrl(urlRequest.getLongUrl());
            return ResponseEntity.ok().body(shortUrl);
        }catch (InvalidUrlException exception){
            return ResponseEntity.badRequest().build();
        }
    }

}
