# API Loans
![technology Java](https://img.shields.io/badge/technology-java-blue.svg)
![tag jdk8](https://img.shields.io/badge/tag-jdk8-orange.svg)

API Loans expone servicios para consultar, crear y borrar usuarios. 
Además, hay usuarios preexistentes almacenados que pueden contar con prestamos asociados.

## Spring Boot APP for JAVA 8
Pre-requisitos:

    Maven
    Java JDK 1.8

##Configure su proyecto
Luego de clonar el repositorio desde Github, sitúese en el directorio root del proyecto 
y ejecute el siguiente comando desde la consola `mvn clean install` para poder buildear el proyecto y descargar todas sus dependencias.
A continuación, puede ejecutar la aplicación con el comando `mvn spring-boot:run` y disfrute ;)

##Uso de la API

###Consultar un usuario por ID

`GET` http://localhost:8080/users/**id**

#####Success Response
**Status**: `200 OK`
```json
{
    "id": 3,
    "firstName": "Leandro",
    "lastName": "Sanchez",
    "email": "leandrosanchez@gmail.com",
    "loans": [
        {
            "id": 14,
            "total": 1000,
            "userId": 3
        }
    ]
}
```
####Error Response Format
**Status**: `404 Not Found`
```json
{
  "error": "User not found Exception",
  "message": "The user with ID 5 does not exists",
  "status": 404
}
```

###Crear un usuario nuevo
**IMPORTANT:** En la creación de un usuario no se puede dar de alta nuevos préstamos (**loans**), 
los préstamos solamente son válidos para usuarios pre-existentes.

`POST` http://localhost:8080/users

`BODY`
```json
{
    "firstName": "Alejandro",
    "lastName": "Martinez",
    "email": "alejandromartinez@test.com"
}
```

#####Success Response
**Status**: `200 OK`
```json
{
  "id": 5,
  "firstName": "Alejandro",
  "lastName": "Martinez",
  "email": "alejandromartinez@test.com",
  "loans": []
}
```
####Error Response Format
**IMPORTANT:** 
Para la creación de un usuario los campos **"firstName"**, **"lastName"** and **"email"** son obligatorios.

**Status**: `400 Bad Request`
```json
{
  "message": "Method Argument Not Valid",
  "errors": [
    "Please provide a firstName attribute in JSON request"
  ],
  "status": 400
}
```

###Eliminar un usuario

`DELETE` http://localhost:8080/users/**id**

####Success Response
**Status**: `200 OK`
```
User deleted successfully!
```
####Error Response Format

**Status**: `404 Not Found`
```json
{
  "error": "Empty Result Data Exception",
  "message": "No class com.example.loans.domain.User entity with id 10 exists!",
  "status": 404
}
```

###Consulta de Préstamos 

Se puede consultar por todos los prestamos existentes por medio de una búsqueda páginada, 
indicando obligatoriamente el tamaño de resultados en una página (**size**) y el número de la página a consultar (**page**).
Opcionalmente, se puede realizar una búsqueda filtrando por id de usuario (**user_id**) si se agrega el parámetro.

Búsqueda páginada de todos los préstamos solicitando la primer página (índice 1 es la primera)
y la cantidad de resultados a mostrar por página:

`GET` http://localhost:8080/loans?**page**=1&**size**=5

Opcional: Búsqueda páginada de los prestamos filtrando por id de usuario (**user_id**):

`GET` http://localhost:8080/loans?**page**=1&**size**=5&**user_id**=3









