# Genesis Resources API

## Nastavení
1. Importujte databázi: `mysql -u root -p genesisresources < genesisresources_dump.sql`
2. Aktualizujte `application.properties` s vašimi MySQL přihlašovacími údaji, pokud je to potřeba.
3. Spusťte aplikaci: `mvn spring-boot:run`
4. Testujte endpointy pomocí poskytnuté sbírky Postman (`Genesis_Resources_API.postman_collection.json`).

## Databáze
- Schéma a ukázková data jsou v `genesisresources_dump.sql`.
- Admin přihlašovací údaje: `admin` / `password123`.

## Endpoints
- Všechny endpointy `/api/v1/users/**` vyžadují Basic Auth.
- Příklady naleznete v sbírce Postman.