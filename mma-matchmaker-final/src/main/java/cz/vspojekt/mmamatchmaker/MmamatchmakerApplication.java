package cz.vspojekt.mmamatchmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Hlavní konfigurační třída aplikace, která spouští Spring Boot kontext
@SpringBootApplication
public class MmamatchmakerApplication {

    public static void main(String[] args) {
        // Spuštění aplikace
        SpringApplication.run(MmamatchmakerApplication.class, args);
    }

}