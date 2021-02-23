# API Loans - Spring Boot 
![technology Java](https://img.shields.io/badge/technology-java-blue.svg)
![tag jdk8](https://img.shields.io/badge/tag-jdk8-orange.svg)

API Loans expone servicios para consultar, crear y borrar usuarios. 
Además, hay usuarios preexistentes almacenados que pueden contar con prestamos asociados.

## Pre-requisitos:

    Maven
    Java JDK 1.8

## Configure su proyecto
Luego de clonar el repositorio desde Github, sitúese en el directorio root del proyecto 
y ejecute el siguiente comando desde la consola `mvn clean install` para poder buildear el proyecto y descargar todas sus dependencias.
A continuación, puede ejecutar la aplicación con el comando `mvn spring-boot:run` y disfrute ;)

La aplicación ejecutará en el puerto `8080` de `localhost`.

Una vez que la aplicación este corriendo podra visualizar los usuarios almacenados 
(los pre-existentes y los que se creen) en una base de datos que ejecuta en memoria (Database **H2**).
Para consultar los elementos en la BD puede ingresar desde el navegador a `http://localhost:8080/h2-console` 
e ingresar con las siguientes credenciales y realizar las consultas necesarias:
```
User Name: sa 
Password: password
```

![Schema Database](https://github.com/bmarquezchesko/loans/blob/main/src/main/resources/files/H2%20DB.png)

## Uso de la API

### Consultar un usuario por ID

`GET` http://<i></i>localhost:8080/users/**id**

##### Response
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
#### Error Response Format
**Status**: `404 Not Found`
```json
{
  "error": "User not found Exception",
  "message": "The user with ID 5 does not exists",
  "status": 404
}
```

### Crear un usuario nuevo
**IMPORTANT:** En la creación de un usuario no se puede dar de alta nuevos préstamos (**loans**), 
los préstamos solamente son válidos para usuarios pre-existentes.

`POST` http://<i></i>localhost:8080/users

`BODY`
```json
{
    "firstName": "Alejandro",
    "lastName": "Martinez",
    "email": "alejandromartinez@test.com"
}
```

#### Success Response
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
#### Error Response Format
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

### Eliminar un usuario

`DELETE` http://<i></i>localhost:8080/users/**id**

#### Success Response
**Status**: `200 OK`
```
User deleted successfully!
```
#### Error Response Format

**Status**: `404 Not Found`
```json
{
  "error": "Empty Result Data Exception",
  "message": "No class com.example.loans.domain.User entity with id 10 exists!",
  "status": 404
}
```

### Consulta de Préstamos 

Se puede consultar por todos los prestamos existentes por medio de una búsqueda páginada, 
indicando obligatoriamente el tamaño de resultados en una página (**size**) y el número de la página a consultar (**page**).
Opcionalmente, se puede realizar una búsqueda filtrando por id de usuario (**user_id**) si se agrega el parámetro.

Búsqueda páginada de todos los préstamos solicitando la primer página (índice 1 es la primera)
y la cantidad de resultados a mostrar por página:

`GET` http://<i></i>localhost:8080/loans?**page**=1&**size**=5

#### Success Response
**Status**: `200 OK`
```json
{
    "items": [
        {
            "id": 1,
            "total": 2500,
            "userId": 3
        },
        {
            "id": 2,
            "total": 1500,
            "userId": 3
        },
        {
            "id": 3,
            "total": 1000,
            "userId": 1
        },
        {
            "id": 4,
            "total": 3000,
            "userId": 1
        },
        {
            "id": 5,
            "total": 5000,
            "userId": 1
        }
    ],
    "paging": {
        "page": 1,
        "size": 5,
        "total": 14
    }
}
```

**Opcional**: Búsqueda páginada de los prestamos filtrando por id de usuario (**user_id**):

`GET` http://<i></i>localhost:8080/loans?**page**=1&**size**=5&**user_id**=3

#### Success Response
**Status**: `200 OK`
```json
{
    "items": [
        {
            "id": 13,
            "total": 700,
            "userId": 3
        },
        {
            "id": 14,
            "total": 1000,
            "userId": 3
        }
    ],
    "paging": {
        "page": 1,
        "size": 5,
        "total": 2
    }
}
```

#### Error Response Format
**Status**: `400 Bad Request`
```json
{
    "error": "Missing Parameter Exception",
    "message": "Required Integer parameter 'page' is not present",
    "status": 400
}
```

## Preguntas
* [braianmarquez89@gmail.com](mailto:braianmarquez89@gmail.com)







