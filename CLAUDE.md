# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a collection of example projects demonstrating the **Opensabre framework** - a Spring Boot-based microservices framework (v0.2.0). The examples showcase different capabilities including REST APIs, caching, and RPC communication.

## Build System

- **Build Tool**: Gradle 9.3.0 with Spring Boot plugin 3.4.1
- **Java Version**: Java 17 (source/target), Java 21 toolchain
- **Dependencies**: Managed via Maven Central and local Maven repository (`mavenLocal()`)

### Common Commands

```bash
# Navigate to a specific sample directory first (each sample is independent)
cd sample-boot  # or sample-cache, sample-cloud, sample-provider

# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Run a specific test class
./gradlew test --tests com.opensabre.sample.SomeTest
```

## Project Structure

This repository contains **four independent sample modules**, each with its own Gradle wrapper:

1. **sample-boot** - Basic Spring Boot application with REST APIs and OAuth2 Resource Server
   - Demonstrates: REST endpoints, Swagger documentation, sensitive data logging
   - Main class: `com.opensabre.sample.SampleApplication`

2. **sample-cache** - Cache operations demonstration
   - Uses: `opensabre-starter-cache`
   - Demonstrates: Cache get, update, delete operations
   - Main class: `com.opensabre.sample.SampleApplication`

3. **sample-cloud** - RPC consumer/client example
   - Uses: `opensabre-starter-rpc`
   - Demonstrates: RPC communication with provider service
   - Main class: `com.opensabre.sample.SampleApplication`

4. **sample-provider** - RPC service provider
   - Uses: `opensabre-starter-rpc`
   - Demonstrates: Exposing RPC services, `@EnableFeignClients` for Feign client support
   - Main class: `com.opensabre.sample.provider.SampleProviderApplication`

**Important**: Each sample is a standalone project. Always navigate to the specific sample directory before running commands. The opensabre framework components are published to Maven local, so `mavenLocal()` repository is required.

## Architecture

### Opensabre Framework Components

The examples demonstrate these Opensabre starters (all v0.2.0):

- **opensabre-starter-boot** - Base boot configuration with common utilities
- **opensabre-starter-cache** - Cache abstraction layer
- **opensabre-starter-rpc** - RPC communication for microservices

### Key Features Demonstrated

1. **Sensitive Data Logging** - Automatic masking of sensitive fields (idCard, mobile, address, email)
   ```
   opensabre.sensitive.log.enabled=true
   opensabre.sensitive.log.rules=idCard,mobile,address,email
   ```

2. **Swagger/OpenAPI Documentation** - Auto-generated API docs
   - Access at: `/swagger-ui.html`
   - Configured via `opensabre.rest.swagger.*` properties

3. **Property Encryption** - Sensitive values encrypted with `ENC()` prefix
   ```
   test.password=ENC(3iIE/7wbUTBB0g9WuUHIFbduqiLHafNVnB+Oo+SP6uFGntypXzyZsd1SPlwJchgv)
   ```

4. **Management Endpoints** - Actuator endpoints exposure
   ```
   management.endpoints.web.exposure.include=*
   ```

### Source Code Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/opensabre/sample/
│   │       ├── SampleApplication.java    # Main entry point (most samples)
│   │       ├── config/                    # Configuration classes
│   │       └── rest/                      # REST controllers
│   └── resources/
│       ├── application.properties         # Main configuration
│       └── bootstrap.properties          # Bootstrap configuration (optional)
└── test/                                 # JUnit 5 tests
```

## Configuration Notes

- All projects use UTF-8 encoding and the `-parameters` compiler flag
- Each sample has its own `application.properties` with feature-specific configuration
- Default application port is 8080 (configurable in bootstrap.properties)
- The `logs/` directory at the root is used for application logs

## Dependencies

Common dependencies across samples:
- **Spring Boot**: 3.4.1
- **Lombok**: 1.18.30 (annotation processor)
- **JUnit 5**: For testing (spring-boot-starter-test)

Security dependencies (sample-boot only):
- **spring-boot-starter-oauth2-resource-server**

## Testing

Tests use JUnit 5 with Spring Boot Test. Run tests from the sample directory:
```bash
./gradlew test
```

Each sample contains basic context loading tests that verify Spring application startup.
