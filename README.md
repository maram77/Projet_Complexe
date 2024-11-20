
# Projet Complexe

This repository contains a full-stack ecommerce platform built with modern frameworks and tools. The project includes two main parts:

1. **Mobile Application**: A Flutter-based app for delivery workers.
2. **Web Application**: A web-based platform with a backend built using Spring Boot and a frontend developed with Angular. The application uses MySQL as its database.


## Technologies Used

### Mobile Application
- **Framework**: Flutter
- **Purpose**: Dedicated to delivery workers for managing orders and deliveries.

### Web Application
- **Frontend**: Angular
- **Backend**: Spring Boot
- **Database**: MySQL

## Setup Instructions

### Prerequisites
- Java 11 or later for Spring Boot
- Node.js and npm for Angular
- Flutter installed for the mobile app
- MySQL server for the database

### Web Application Setup

1. **Backend (Spring Boot)**
   - Navigate to the `web/ecommerce_app/back/` directory.
   - Configure the `application.properties` file with your MySQL credentials.
   - Run the application using:
     ```bash
     ./mvnw spring-boot:run
     ```

2. **Frontend (Angular)**
   - Navigate to `web/ecommerce_app/front/ecommerce-sophia-new/`.
   - Install dependencies:
     ```bash
     npm install
     ```
   - Run the Angular application:
     ```bash
     ng serve
     ```

3. Access the web application at `http://localhost:4200`.

### Mobile Application Setup

1. Navigate to the `mobile/` directory.
2. Install Flutter dependencies:
   ```bash
   flutter pub get
3. Run the app on an emulator or physical device:
   ```bash
   flutter run


