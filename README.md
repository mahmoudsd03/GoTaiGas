# GoTaiGas

GoTaigas is a web-based application for managing and presenting startup ideas. Students can create and manage their own ideas, while investors can browse existing ideas and search for suitable projects.

## Features

- Registration and login for different user types
- User profile management
- Creation and management of startup ideas
- Viewing and filtering existing ideas
- Investor view for discovering interesting projects
- Persistent data storage using PostgreSQL and JPA
- Web interface built with Vaadin
- Optional AI integration via a configurable API endpoint

## Technologies

- Java 17
- Spring Boot
- Vaadin 24
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit

## Requirements

To run the application locally, you need:

- Java 17 or newer
- Maven or the included Maven Wrapper
- PostgreSQL
- A local database for GoTaigas

## Configuration

Sensitive credentials are not stored in the repository. The application reads database and API configuration from environment variables.

A template is provided in `.env.example`.

Required variables:

```text
DB_URL=jdbc:postgresql://localhost:5432/gotaigas
DB_USERNAME=your_database_user
DB_PASSWORD=your_database_password
DB_SCHEMA=gotaigas
KICONNECT_API_KEY=your_api_key
KICONNECT_API_URL=your_api_url
KICONNECT_MODEL=your_model
```

Never store real passwords or API keys in files that are committed to GitHub.

## Running the Project

Clone the repository:

```bash
git clone https://github.com/YOUR-USERNAME/GoTaigas.git
cd GoTaigas
```

On Linux/macOS:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd spring-boot:run
```

The application is then usually available at:

```text
http://localhost:8080
```

## Running Tests

On Linux/macOS:

```bash
./mvnw test
```

On Windows:

```powershell
mvnw.cmd test
```

## Project Structure

```text
GoTaigas/
├── src/
│   ├── main/
│   │   ├── java/com/gotaigas/
│   │   └── resources/
│   └── test/
│       └── java/com/gotaigas/
├── frontend/
├── pom.xml
├── .env.example
└── README.md
```

## Security

- Do not store credentials in the repository.
- Do not commit `.env` files or local configuration files.
- Provide API keys only through environment variables.
- Any keys that have already been published or accidentally committed should be revoked and regenerated immediately.

## License

An appropriate open-source license can be added before public distribution if needed.
