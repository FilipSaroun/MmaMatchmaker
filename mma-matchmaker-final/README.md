# MMA Matchmaker API


Aplikace sloužící pro správu databáze MMA bojovníků a generování zápasů. Projekt byl vytvořen jako semestrální práce s důrazem na čistou architekturu, bezpečnost a kvalitní dokumentaci.


## Technický popis
Projekt využívá **Spring Boot 3** a vrstvenou architekturu:
* **Controller:** Zpracovává HTTP požadavky a vrací DTO objekty.
* **Service:** Obsahuje hlavní business logiku (matchmaking, validace).
* **Repository:** Zajišťuje komunikaci s databází (Spring Data JPA).
* **Bezpečnost:** Implementováno pomocí Spring Security (Basic Authentication).
* **Ošetření chyb:** Globální obsluha výjimek (`GlobalExceptionHandler`) pro konzistentní API odpovědi.


## Použité technologie
* Java 21, Spring Boot 3
* Spring Data JPA (Hibernate)
* H2 Database (in-memory)
* Spring Security
* Swagger (OpenAPI 3)
* SLF4J (Logování)



## Webové rozhraní aplikace je dostupné na adrese: 

**http://localhost:8080/**.



## Dokumentace API
Po spuštění aplikace je dokumentace dostupná na adrese:
`http://localhost:8080/swagger-ui/index.html`



## Spuštění projektu
Projekt lze spustit pomocí terminálu v kořenovém adresáři:


1. **Build a spuštění:**
   ```bash
   ./mvnw spring-boot:run


2. Spuštění testů:
Bash
./mvnw test


## Zabezpečení
Pro čtení dat (GET) není vyžadováno přihlášení. Modifikace dat (POST, PUT, DELETE) jsou omezeny na roli ADMIN.

Username: admin

Password: admin123