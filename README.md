# Indetex code challenge

Author: Ramón José París González

### Technologies used

- Java 21 LTS.
- Gradle
- Spring Boot 3.4.1
- Spring Reactive (webflux, r2dbc)
- Cucumber for acceptance / integration tests
- JUnit5 and Mockito for unit testing
- H2 database

### Execute the project

Make sure that you have installed Java 21 to run the project.

- Run the project

   ```bash
   ./gradlew clean bootRun
   ```

- Run unit tests

   ```bash
   ./gradlew test
   ```

- Run integration tests

   ```bash
   ./gradlew integrationTest
   ```
  This executes a cucumber CLI for running Integration Tests. It outputs a report that can be found at
  `target/cucumber-report.html`
  <img src="assets/cucumber.png" alt="drawing" width="300"/>


- Build the project

   ```bash
   ./gradlew clean build
   ```

### Architecture decisions

- **Hexagonal Architecture principles**:
    - Separation of `infrastructure` and `core` layer
    - Usage of primary and secondary adapter as well as UseCases and Ports for communication with infrastructure
    - Creation of domain object and mappings for not coupling Infrastructure objects with Domain objects
![arch.png](assets/arch.png)


- **Criteria pattern for database filtering**:
    - Thinking in the scalability of the solution, I implemented a Criteria pattern for filtering in the database, which
      allow us to maintain better, scale better and follow Open/Closed principle from SOLID


- **Auto import of schema and data in H2 DB**:
    - Usage of Spring for creating schemas and inserting data in H2 DB for project and integration test executions


- **Spring Reactive**:
  - Usage of Spring Reactive. With special focus in non-blocking behaviors.
  - Usage of `Webflux` for handling `Monos` and `Fluxes`
  - Usage of `r2dbc` for non blocking database operations


- **Developed using TDD principles**


- **Clean Code mindset 🤓**
  