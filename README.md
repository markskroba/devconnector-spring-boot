# DevConnector Spring Boot API

This is full a rewrite of a server that was built as a part of ["MERN Stack Front-to-Back" course on Udemy](https://www.udemy.com/course/mern-stack-front-to-back/), 
which was written in JavaScript, using Spring Boot and Spring Security. 
It includes all the endpoints that were served in original application, 
including features like JWT authentication using MongoDB database, 
Gravatar support for users avatars, and error handling through custom messages 
sent alongside the HTTP requests. It can be seamlessly used with original database 
and front-end written in React.

## Run

It uses Maven as its build tool, so `mvn spring-boot:run` will run in exploded 
mode on post 5000 (specified in `application.yml`), although throughout its development 
it was mostly run through the IDE.

## Other links

["MERN Stack Front-to-Back" course on Udemy](https://www.udemy.com/course/mern-stack-front-to-back/)\
[Original project done by me (mostly back in 2021)](https://github.com/markskroba/devconnector)