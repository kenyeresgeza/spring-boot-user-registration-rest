# Spring Boot User Registration REST example

Spring Boot 2.x example how to manage user registration with REST API

## Description

Spring Boot 2.7.5, REST, JPA, Security

## Getting Started

### Dependencies

* Java 11
* Postgres SQL database

### Installing

* Create "sb-user-registration-1.0.0.jar"
```
- sudo update-alternatives --config java (select Java 11)
- gradle clean bootjar 
```

### Executing program

* Run "sb-user-registration" application
```
java -jar sb-user-registration-1.0.0.jar
```
1. Call REST endpoint to register user
```
POST http://localhost:8087/register
{
    "firstName" : "Demo",
    "lastName" : "User",
    "email" : "demo@gmail.com",
    "password" : "password",
    "matchingPassword" : "password"
}
```
2. The security token has been sent to user email address.

3. After that you should call the following REST endpoint.
```
GET http://localhost:8087/verify?token=YOUR_TOKEN
```
* Additional REST endpoint list

1. Resend security token
```
GET  http://localhost:8087/resendToken?token=OLD_TOKEN
```
2. Reset user password
```
POST http://localhost:8087/resetPassword
{
    "email" : "demo@gmail.com"
}
```

## Author

Kenyeres Géza
https://hu.linkedin.com/in/g%C3%A9za-kenyeres-17341631
