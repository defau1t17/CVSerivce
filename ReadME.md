# CVService

## Krainet's Java Developer Trainee test case

### Stack

* Java 17
* Spring Boot 3.1.5
* Spring Data JPA
* PostgreSQL
* Docker
* Swagger 3
* Slf4j

### Released 

* ### Direction

1. [X] Add, modify, display a list of destinations using filtering, sorting, and paginated
   input.


* ### Tests

1. [X] Adding, modifying, displaying a list of tests using filtering, sorting, and paginated input


* ### Candidates

1. [X] Adding, modifying, displaying a list of referrals using filtering, sorting, and paginated
   input.


* #### Candidate Tests

1. [X] Adding, modifying, displaying a list of candidate tests using filtering, sorting and
   paginated input.

Adding and modifying is done by sending an AJAX request to the REST API where it is first validated, if the object
passes validation, it is added to the database. Display and filtering are implemented by using specifications,
The ability to view CV files in a browser by clicking on a link is implemented. All changes in the database are handled by **Spring Data JPA** migrations.


## Deploy
 
* Go to the root project directory 

* Type the command :

`docker compose up -d` 

* Go to url : 

`http://localhost:8080`

