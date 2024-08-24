# Digital_Voting_System

## Overview

The Digital Voting System is a secure, user-friendly platform designed to facilitate remote voting processes, ensuring that each vote is counted accurately and efficiently. This system is ideal for organizations, educational institutions, and communities that require a reliable method to conduct elections and polls online.
## Features

- Create, read, update, and delete requests for help.
- User authentication and authorization.
- Secure API endpoints with JWT (JSON Web Token) security.
- Data storage with MySQL.
- Tested with Postman for comprehensive API validation.
- Unit and integration tests using Mockito and JUnit.

## Tech Stack

- Backend: Java Spring Boot
- Database: MySQL
- Security: Spring Security with JWT
- Testing: Postman, Mockito, JUnit

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL Server
- Postman (for testing)

### Installation

1. Clone the repository:

    
    git clone https://github.com/chetanlamani/digital voting system.git
    cd digital voting system
    

2. Configure MySQL:

    - Install MySQL Server if you haven't already.
    - Create a database named online voting system_db.
    - Create a user with appropriate permissions and set the password.

3. Set up environment variables:

    Create a .env file in the root directory and add the following:

    
    SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/online voting system_db
    SPRING_DATASOURCE_USERNAME=<username>
    SPRING_DATASOURCE_PASSWORD=<password>
    SPRING_JPA_HIBERNATE_DDL_AUTO=update
    

4. Build the project:

    
    mvn clean install
    

5. Run the application:

    
    mvn spring-boot:run
    

### API Endpoints

The API is documented using Swagger. Once the application is running, you can access the API documentation at:

http://localhost:8080/swagger-ui.html

### Testing

#### Postman

A Postman collection is included in the repository. Import digital voting system.postman_collection.json into Postman to test the API endpoints.

#### Unit Tests

The project includes unit and integration tests using Mockito and JUnit. To run the tests, use the following command:

mvn test

## Security

The application uses Spring Security with JWT to secure API endpoints. Ensure that you configure proper security measures and handle sensitive data responsibly.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## Contact

If you have any questions or feedback, please reach out to us at chetanlamani3995@gmail.com
