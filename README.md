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



---

### **Krok 4: Uložení změn**

1. Po přidání textu sjeď dolů na stránce.
2. Do pole **Commit changes** napiš zprávu, například:




3. Vyber možnost **Commit directly to the `master` branch** (pokud chceš změny uložit přímo).
4. Klikni na **Commit changes**.

---

### **Krok 5: Ověř, že je dokumentace vidět**

1. Po uložení změn se automaticky vrátíš na hlavní stránku repozitáře.
2. Měl bys vidět přidanou sekci **"📚 Popis tříd a jejich funkcí"** přímo na úvodní stránce repozitáře.

---

## **2️⃣ (Volitelné) – Vytvoření samostatné složky `docs/`**

Pokud chceš dokumentaci ještě více rozdělit do samostatných souborů, můžeme vytvořit složku **`docs/`**.

---

### **Krok 1: Vytvoření složky `docs/`**

1. Na hlavní stránce repozitáře klikni na **`Add file`** > **`Create new file`**.
2. Do názvu souboru napiš: **`docs/UserController.md`**.

---

### **Krok 2: Přidání dokumentace do `docs/UserController.md`**

1. Do souboru vlož například:

```markdown
# UserController.java

**Umístění:** `src/main/java/com/genesisresources/controller/UserController.java`

## Popis
`UserController` je hlavní REST kontroler pro práci s uživateli. Zajišťuje CRUD operace a směruje HTTP požadavky do `UserService`.

## Metody:
- `createUser(@RequestBody User user)` – Vytvoří nového uživatele.
- `getUserById(@PathVariable Long id, @RequestParam Boolean detail)` – Získá uživatele podle ID.
- `getAllUsers(@RequestParam Boolean detail)` – Vrátí seznam všech uživatelů.
- `updateUser(@RequestBody User user)` – Aktualizuje informace o uživateli.
- `deleteUser(@PathVariable Long id)` – Smaže uživatele podle ID.

## 📄 Dokumentace

- [UserController](docs/UserController.md)
- [UserService](docs/UserService.md)
- [UserRepository](docs/UserRepository.md)
- [User](docs/User.md)
- [GlobalExceptionHandler](docs/GlobalExceptionHandler.md)
## 🧪 Testování API pomocí Postmanu

Pro otestování API můžeš použít [Postman kolekci](postman/genesisresources_postman_collection.json).

1. Otevři Postman.
2. Importuj soubor: `postman/genesisresources_postman_collection.json`.
3. Spusť jednotlivé API požadavky.

## 🗄️ Struktura databáze

ER diagram databáze najdeš zde: [genesis_db_schema.png](database/genesis_db_schema.png).

