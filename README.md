User Account Management System
Overview
This project is a user account management system built with Spring Boot 3, incorporating various technologies and practices to ensure a robust and secure application. The application provides functionalities for user registration, email verification, account management, and secure resource access.

Technologies Used
Spring Boot 3
Spring Security
JWT (JSON Web Tokens)
AOP (Aspect-Oriented Programming)
OpenAPI for API documentation
JUnit 5 and Mockito for unit testing
PostgreSQL with Spring Data
Log4j2 for logging
Java Mail Sender for email functionalities
Features
Authentication Controller
Register API: Allows users to register and sends a verification code to their email.
Verify API: Verifies the provided code. If expired, directs to the Resend API.
Resend API: Resends the verification code to the user’s email.
Signin API: Authenticates users and issues JWT tokens.
Account Controller
Add Account API: Allows users to add a new account.
Delete Account API: Allows users to delete an existing account.
Logging and Monitoring
Log4j2 is used for comprehensive logging.
AOP is utilized to log the start and end of important methods in the controllers.
Exception Handling
A Global Exception Handler manages exceptions and provides a unified response structure.
Custom exceptions are delegated to a GlobalExceptionHandler annotated with @ControllerAdvice.
Documentation
Swagger is used for API documentation and testing.
Email Functionality
Java Mail Sender is used for sending verification emails.
Security
JWT is used to secure API resources.
Future Improvements
Dockerize the Application: Containerize the application for easier deployment and scalability.
Complete Unit Testing: Enhance unit tests for better coverage and reliability.
Implement Refresh Token Mechanism: Add a refresh token mechanism to address JWT token revocation and improve token expiry control.
