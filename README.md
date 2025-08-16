# Multi-Store Jewellery Management System

Spring Boot sample showing **multi-store (multi-schema) setup** for a jewellery chain.
Two MySQL schemas are used: `jewellery_pune` and `jewellery_mumbai`.
The same entities are mapped in both schemas via **two DataSources** and **two EntityManagers**.



## Tech Stack

- Core Java 17, Spring Boot 3 (Web, JPA)
- MySQL (two schemas), Hibernate ORM
- Maven

## Setup

1) Ensure MySQL is running. Schemas will be created automatically.
2) Update DB credentials in `src/main/resources/application.yml`.
3) Run with:

```bash
mvn spring-boot:run
```

## Endpoints

- POST `/api/stores/{storeId}/items`  (storeId = `pune` or `mumbai`)
- GET  `/api/stores/{storeId}/items`
- POST `/api/stores/{storeId}/sales`
- GET  `/api/stores/{storeId}/sales`
- GET  `/api/reports/summary`
