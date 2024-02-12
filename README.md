this microservice makes user registration and searches for them by sorted pagination, or by email. Im using postgres as DB for user data in this ms.
in the search by email, a search for address is made in an externa API for detailed address information, Im using feing client and circuit breaker(resilience4j). 
after the registration a message is sent to a broker (rabbitMQ) and another microservice sends an email confirming the registration. Im using spring-boot-starter-email and mongoDB to save the info sent into email. 

for postgres and rabbitMQ:

- install docker
- go to project root folder
- on terminal run: docker compose -f stack.yml up
