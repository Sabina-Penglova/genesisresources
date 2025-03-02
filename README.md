# GenesisResources

Spring Boot aplikace pro správu uživatelů s REST API, zabezpečením a testy.

## Požadavky
- Java 17
- MySQL (nastavení v `application.properties`)
- Maven

## Spuštění
1. Nainstaluj MySQL a vytvoř databázi `genesisresources`.
2. Spusť SQL skript `src/main/resources/schema.sql` pro vytvoření tabulky `users` a iniciačních dat.
3. Spusť aplikaci pomocí `mvn spring-boot:run` nebo `java -jar target/genesisresources.jar`.

## Testování
Testy jsou implementovány v balíčku `com.genesisresources.service`. Spusť je pomocí JUnit 5.

## API Endpointy
- `/api/v1/users` – CRUD operace pro uživatele
- Zabezpečení: HTTP Basic Auth (uživatel: admin, heslo: password123)