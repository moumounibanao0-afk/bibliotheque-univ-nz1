package com.bibliotheque.controller;

import com.bibliotheque.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/test")
    public ResponseEntity<String> testerNotification(@RequestBody Map<String, String> request) {
        try {
            notificationService.envoyerNotification(
                request.get("email"),
                "Test Notification — Bibliothèque UNZ",
                request.get("message")
            );
            return ResponseEntity.ok("Email envoyé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}
