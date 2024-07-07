package com.inu.url_shortner_service.entity;

import jakarta.persistence.*;

@Entity
public class UrlMapping {

    public  UrlMapping(){

    }

    public  UrlMapping(String shortUrl, String longUrl){
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String shortUrl;

    @Column(unique = true)
    private String longUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}