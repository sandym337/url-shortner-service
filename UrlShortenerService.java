package com.inu.url_shortner_service.service;

import com.inu.url_shortner_service.entity.UrlMapping;
import com.inu.url_shortner_service.exceptions.InvalidUrlException;
import com.inu.url_shortner_service.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    @Autowired
    public UrlRepository urlRepository;

    private final String BASE62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String shortenUrl(String longUrl) throws InvalidUrlException {
        if (!isValidUrl(longUrl)) {
            throw new InvalidUrlException("Invalid URL, Please Check");
        }

        Optional<UrlMapping> existingMapping = urlRepository.findByLongUrl(longUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get().getShortUrl();
        }

        String shortUrl = generateUniqueShortUrl();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setLongUrl(longUrl);

        urlRepository.save(urlMapping);

        return shortUrl;
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String encodeBase62(int value) {
        StringBuilder encodedValue = new StringBuilder();
        while (value > 0) {
            int remainder = value % 62;
            encodedValue.insert(0, BASE62_CHARACTERS.charAt(remainder));
            value /= 62;
        }
        return encodedValue.toString();
    }

    public String generateUniqueShortUrl() {
        String shortUrl;
        do {
            int randomId = new Random().nextInt(Integer.MAX_VALUE);
            shortUrl = "www.inu.com/"+encodeBase62(randomId);
            // www.inu.com/a4R23B
        } while (urlRepository.findByShortUrl(shortUrl).isPresent());

        return shortUrl;
    }


}
