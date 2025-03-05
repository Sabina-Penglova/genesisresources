# Genesis Resources API
A simple Spring Boot REST API for managing users, built as a school project.

## Setup
1. Install MySQL and create a `genesisresources` database.
2. Run the SQL script in `schema.sql` to set up the table.
3. Start the application with `mvn spring-boot:run`.
4. Use the provided Postman collection to test the API.

## Endpoints
- **POST /api/v1/users** - Create a user (requires Basic Auth: `admin/password123`)
- **GET /api/v1/users** - List all users
- **GET /api/v1/users/{id}** - Get a user by ID
- **PUT /api/v1/users/{id}** - Update a user
- **DELETE /api/v1/users/{id}** - Delete a user

## Authentication
- Uses HTTP Basic Authentication with username `admin` and password `password123`.