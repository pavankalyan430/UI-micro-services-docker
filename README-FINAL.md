# SmartBazaar - Microservices-Based E-commerce Platform

SmartBazaar project demonstrates microservices architecture, Docker-based deployment, and service discovery with Eureka, all managed with Docker Compose.

---

## 🧠 WHY, WHAT, HOW?

### 🔹 WHY this project?
- To demonstrate **real-time microservices architecture** using the latest versions of Java, Spring Boot, and Gradle.
- To build a complete **e-commerce platform** that is modular, scalable, and Dockerized.
- To learn how to integrate frontend (React) and backend (Java microservices) using an **API Gateway**.
- To simplify deployment using **Docker Compose**.

### 🔹 WHAT does it include?
- Java-based microservices using Spring Boot 3.5.0 and Gradle 8.14.1
- React.js frontend using Node 18 and Nginx
- API Gateway with Spring Cloud Gateway
- Eureka Discovery Server
- MySQL 8 for database
- Fully containerized using Docker and Docker Compose

### 🔹 HOW is it built?
- Each microservice is a Spring Boot application managed using Gradle.
- Services are discovered through Eureka and routed via API Gateway.
- React UI communicates via API Gateway.
- Everything is built and deployed using Docker Compose.

---

## 🏗 Project Overview

### 🔧 Technologies Used

| Component         | Technology                  |
| ----------------- | --------------------------- |
| Language          | Java 21, JavaScript (React) |
| Backend Framework | Spring Boot 3.5.0           |
| Frontend          | React.js (Node 18)          |
| Build Tool        | Gradle 8.14.1               |
| Service Registry  | Netflix Eureka              |
| API Gateway       | Spring Cloud Gateway        |
| Database          | MySQL 8                     |
| Containerization  | Docker & Docker Compose     |
| Web Server        | Nginx                       |

---

## 📐 Architecture Diagram

```text
                                       ┌────────────────────────────┐
                                       |      SmartBazaar UI        |
                                       |   (React + Nginx, Port 3000) |
                                       └────────────▲─────────────┘
                                                    |
                                                    ▼
                                       ┌────────────────────────────┐
                                       |      API Gateway           |
                                       |    (Spring Cloud, 8080)    |
                                       └────────────▲─────────────┘
                                                    |
     ┌────────────────────────────┬─────────────────┴─────────────────┬────────────────────────────┐
     |                            |                                     |                            |
     ▼                            ▼                                     ▼                            ▼
┌───────────────┐       ┌────────────────────┐              ┌────────────────────┐       ┌────────────────────┐
| ProductService|       |   PriceService     |              |  Discovery Server   |       |     MySQL DB        |
|  (8081)       |       |   (8082)           |              |  (Eureka - 8761)    |       |  (3306 internal)    |
└───────────────┘       └────────────────────┘              └────────────────────┘       └────────────────────┘
```

---

## 🗃 Project Modules Structure

```
Microservices+docker
├── discovery-server       --> Eureka registry
├── product-service        --> Spring Boot service for product
├── price-service          --> Spring Boot service for pricing
├── api-gateway            --> Gateway to route requests
├── shared-library         --> Common reusable code
├── docker-compose.yml     --> Manages all services

Microservices+docker-UI
└── smartbazaar-ui         --> React frontend
```

---

## 🧩 Shared Library

**WHY:** To avoid code duplication and promote reusability.

**WHAT:** Contains common DTOs, utility classes, exception handlers, etc.

**HOW TO USE:**
1. Add `shared-library` to `settings.gradle` in the root folder.
2. In every other microservice’s `build.gradle`, add:
```gradle
implementation project(':shared-library')
```
3. Create shared classes under `shared-library/src/main/java`

---

## 🧾 settings.gradle

```gradle
rootProject.name = 'smartbazaar'

include 'shared-library'
include 'product-service'
include 'price-service'
include 'api-gateway'
include 'discovery-server'
```

> ✅ This file must be placed inside `Microservices+docker/` and all module names must match their actual folders.

---

## 🚀 Setup Instructions

### 1. 🧱 Prerequisites

Ensure the following tools are installed on your system:

| Tool           | Version |
| -------------- | ------- |
| Java           | 21      |
| Node.js        | 18+     |
| Gradle         | 8.14.1  |
| Docker         | 24.x    |
| Docker Compose | v2.24+  |

---

### 2. 📁 Folder Structure Setup

```text
C:\Users\LOGIN\Documents\Pavan\
├── Microservices+docker            --> Main project root (backend)
│   ├── discovery-server
│   ├── product-service
│   ├── price-service
│   ├── api-gateway
│   ├── shared-library
│   ├── settings.gradle
│   ├── docker-compose.yml
├── Microservices+docker-UI
│   └── smartbazaar-ui             --> React frontend
```

---

### 3. 📦 Docker Compose (docker-compose.yml)

Ensure this file is in `Microservices+docker` folder.
Ensure this file is in `Microservices+docker` folder.

```yaml
version: '3.8'

services:
  discovery-server:
    build:
      context: ./discovery-server
    container_name: discovery-server
    ports:
      - "8761:8761"

  mysql-db:
    image: mysql:8.0
    container_name: mysql-db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: smartbazaar
    ports:
      - "3307:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  product-service:
    build:
      context: ./product-service
    container_name: product-service
    depends_on:
      - mysql-db
      - discovery-server
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/smartbazaar
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: admin

  price-service:
    build:
      context: ./price-service
    container_name: price-service
    depends_on:
      - mysql-db
      - discovery-server
    ports:
      - "8082:8082"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/smartbazaar
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: admin

  api-gateway:
    build:
      context: ./api-gateway
    container_name: api-gateway
    depends_on:
      - discovery-server
      - product-service
      - price-service
    ports:
      - "8080:8080"

  smartbazaar-ui:
    build:
      context: ../Microservices+docker-UI/smartbazaar-ui
      args:
        REACT_APP_API_URL: http://localhost:8080
    container_name: smartbazaar-ui
    ports:
      - "3000:80"
    depends_on:
      - api-gateway

volumes:
  mysql-data:
```

---

### 4. 🌐 API Gateway Configuration (application.yml)

```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway

  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/product/**
        - id: price-service
          uri: lb://price-service
          predicates:
            - Path=/price/**

eureka:
  client:
    service-url:
      defaultZone: http://discovery-server:8761/eureka
```

---


### 5. 🧪 How to Run Entire Project

```bash
# Navigate to root backend folder
cd Microservices+docker

# Build and start all containers
docker-compose up --build
```

⏳ Wait until you see all services registered in Eureka: `http://localhost:8761`

🌐 Access UI at: `http://localhost:3000`

---

### 6. 🧾 UI Environment Variable Setup

No `.env` file is required because the `REACT_APP_API_URL` is passed at build time in the Dockerfile:

**smartbazaar-ui/Dockerfile**

```dockerfile
<unchanged>
```

---

## ✅ Features Completed

- Product CRUD
- Price CRUD
- API Gateway routing
- Eureka service registration
- Docker-based deployment
- Shared library integration

---

## 📌 Important Notes

- All services **register in Eureka**, including UI-based calls to Gateway.
- React app calls are **compiled** with a fixed `REACT_APP_API_URL`, which is passed during Docker build.
- MySQL runs on port `3307` externally to avoid conflicts with local MySQL.

---