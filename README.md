## 📚 Popis tříd a jejich funkcí

### 1. **UserController.java**
- **Umístění:** `src/main/java/com/genesisresources/controller/UserController.java`
- **Popis:** Řídí všechny HTTP požadavky související s uživateli.
- **Hlavní funkce:**
  - **Vytvoření uživatele:** `POST /api/v1/users`
  - **Získání informací o uživateli:** `GET /api/v1/users/{id}`
  - **Aktualizace uživatele:** `PUT /api/v1/users`
  - **Smazání uživatele:** `DELETE /api/v1/users/{id}`

---

### 2. **UserService.java**
- **Umístění:** `src/main/java/com/genesisresources/service/UserService.java`
- **Popis:** Obsahuje obchodní logiku aplikace. Pracuje s daty z databáze a rozhoduje, jak budou zpracována.
- **Hlavní funkce:**
  - **Validace personId**
  - **Generování UUID pro nové uživatele**
  - **Načítání, aktualizace a mazání uživatelů z databáze**

---

### 3. **UserRepository.java**
- **Umístění:** `src/main/java/com/genesisresources/repository/UserRepository.java`
- **Popis:** Zajišťuje komunikaci s databází. Obsahuje SQL dotazy pro práci s tabulkou `users`.
- **Hlavní funkce:**
  - Vytváření nových záznamů v databázi.
  - Načítání uživatelů podle ID.
  - Aktualizace a mazání záznamů.

---

### 4. **User.java**
- **Umístění:** `src/main/java/com/genesisresources/model/User.java`
- **Popis:** Reprezentuje model uživatele v systému.
- **Atributy:**
  - `id` – Jedinečné ID uživatele.
  - `name` – Jméno uživatele.
  - `surname` – Příjmení uživatele.
  - `personId` – Unikátní identifikátor osoby (např. rodné číslo).
  - `uuid` – Globálně jedinečný identifikátor.

---

### 5. **GlobalExceptionHandler.java**
- **Umístění:** `src/main/java/com/genesisresources/exceptions/GlobalExceptionHandler.java`
- **Popis:** Spravuje chyby a výjimky napříč aplikací.
- **Hlavní funkce:**
  - Vrací přátelské chybové zprávy při neplatných datech (`400 Bad Request`).
  - Zpracovává neočekávané chyby (`500 Internal Server Error`).

---

## 🚀 Jak projekt spustit

```bash
mvn clean install
mvn spring-boot:run






