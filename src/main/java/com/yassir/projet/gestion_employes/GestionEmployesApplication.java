package com.yassir.projet.gestion_employes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public  class GestionEmployesApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionEmployesApplication.class, args);

        // Open default browser automatically
        try {
            String url = "http://localhost:8080"; // or "http://localhost:8080"
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}