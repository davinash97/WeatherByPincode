# Freight Fox.ai

### **1. Requirements Breakdown**

**Input:**

- **pincode** (e.g., `411014`)
- **for_date** (e.g., `2020-10-15`)

**Output:**

- Weather data for the given pincode and date, optimized for subsequent calls.

**Actions:**

1. Convert pincode to latitude and longitude using **Geocoding API**.
2. Retrieve weather information using **OpenWeather API**.
3. Save:
    - Pincode, latitude, longitude.
    - Weather information (e.g., temperature, humidity, etc.) along with the date.
4. On subsequent API calls:
    - Check DB for existing data.
    - If available, return cached data.
    - Else, fetch from external APIs, save, and return.

---

### **2. High-Level Architecture**

**Layers:**

1. **Controller**: Handles incoming REST requests.
2. **Service**: Business logic for pincode-to-weather processing.
3. **DAO (Repository)**: Manages database interactions.
4. **External API Client**: For Geocoding and Weather API calls.

**Key Design Patterns:**

- **Repository Pattern**: For database operations.
- **Singleton**: For API client management.
- **Cache-aside**: Use a DB as the cache to minimize external API calls.

---

### **3. Technology Stack**

- **Spring Boot**: For RESTful API.
- **H2 Database**: Lightweight RDBMS for development/testing.
- **JPA**: ORM to manage DB entities.
- **RestTemplate**: To call external APIs.
- **JUnit & Mockito**: For testing.
- **Swagger**: For API documentation.

---

### **4. Database Design**

**Tables**:

1. **Pincode_Info**:
    - `pincode` (PK)
    - `latitude`
    - `longitude`
2. **Weather_Info**:
    - `id` (PK)
    - `pincode` (FK)
    - `for_date`
    - `temperature`
    - `humidity`
    - `other_details` (JSON blob for extensibility)

---

### **5. API Workflow**

### **Endpoint**:

**GET** `/api/weather?pincode=411014&for_date=2020-10-15`

**Steps**:

1. **Input Validation**: Check for valid pincode and date format.
2. **Database Lookup**:
    - Check if weather data for the given `pincode` and `for_date` exists.
3. **External API Call** *(if DB miss)*:
    - Fetch latitude and longitude for pincode (Geocoding API).
    - Fetch weather data for lat/long (OpenWeather API).
    - Save both to the database.
4. **Response**: Return weather data to the client.

---

### **6. Unit Test Plan**

**Test Cases**:

1. Valid input → Weather data from DB (cached).
2. Valid input → External API hit + DB save.
3. Invalid input (e.g., invalid pincode/date format).
4. External API failure simulation.

---

### **7. Next Steps**

1. Codebase setup: Spring Boot application.
2. Database schema creation with JPA entities.
3. Integration with Geocoding and OpenWeather APIs.
4. Unit tests with mocking (e.g., Mockito for API clients).
5. Swagger setup for Postman testing.