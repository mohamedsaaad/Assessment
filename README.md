# User Account Management System

## 🚀 Overview

Welcome to the **User Account Management System**! This project is built using **Spring Boot 3** and integrates several technologies to provide a secure and efficient solution for managing user accounts. Key features include user registration, email verification, account management, and secure resource access.

## 🛠 Technologies Used

- **Spring Boot 3**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **AOP (Aspect-Oriented Programming)**
- **OpenAPI** for API documentation
- **JUnit 5** and **Mockito** for unit testing
- **PostgreSQL** with **Spring Data**
- **Log4j2** for logging
- **Java Mail Sender** for email functionalities

## 🔍 Features

### Authentication Controller

1. **Register API**: Allows users to register and sends a verification code to their email.
2. **Verify API**: Verifies the provided code. If expired, directs to the Resend API.
3. **Resend API**: Resends the verification code to the user’s email.
4. **Signin API**: Authenticates users and issues JWT tokens.

### Account Controller

1. **Add Account API**: Allows users to add a new account.
2. **Delete Account API**: Allows users to delete an existing account.

### Logging and Monitoring

- **Log4j2**: Comprehensive logging for tracking application events.
- **AOP**: Logs the start and end of important methods in the controllers.

### Exception Handling

- **Global Exception Handler**: Manages exceptions with a unified response structure.
- **Custom Exceptions**: Delegated to a `GlobalExceptionHandler` annotated with `@ControllerAdvice`.

### Documentation

- **Swagger**: API documentation and interactive testing available.

### Email Functionality

- **Java Mail Sender**: Handles sending of verification emails.

### Security

- **JWT**: Secures API resources with token-based authentication.

## 🚧 Future Improvements

- **Dockerize the Application**: Containerize for easier deployment and scalability.
- **Complete Unit Testing**: Enhance coverage and reliability of tests.
- **Refresh Token Mechanism**: Address JWT token revocation and improve expiry control.

## 📋 Getting Started

1. **Clone the Repository**:

    ```bash
    git clone <repository-url>
    ```

2. **Navigate to the Project Directory**:

    ```bash
    cd <project-directory>
    ```

3. **Set Up the Database**:
   - Configure PostgreSQL with the necessary settings.

4. **Build the Project**:

    ```bash
    ./mvnw clean install
    ```

5. **Run the Application**:

    ```bash
    ./mvnw spring-boot:run
    ```

6. **Access API Documentation**:
   - Swagger UI is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## 🧪 Testing

- Run unit tests with:

    ```bash
    ./mvnw test
    ```

## 📝 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.
