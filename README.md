# users-crud-hex
Hexagonal architecture applied with a simple CRUD microservice

# Technologies
Spring Boot  
Docker  
MongoDB  
Postman

# Microservice Specification

**request body**

- firstName;  
- lastName;  
- birthDate;  
- email.  

**response body**

- id;  
- email;  
- fullName;  
- age.  

**rules**

- email is mandatory and exclusive;
- users under the age of 30 cannot be excluded.
