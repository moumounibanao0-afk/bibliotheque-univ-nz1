package com.bibliotheque.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveScheduler {

    @Value("${app.url:https://bibliotheque-univ-nz1-5.onrender.com}")
    private String appUrl;

    @Scheduled(fixedDelay = 600000) // toutes les 10 minutes
    public void keepAlive() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(appUrl, String.class);
            System.out.println("Keep-alive ping envoyé à " + appUrl);
        } catch (Exception e) {
            // Ignorer les erreurs
        }
    }
}
