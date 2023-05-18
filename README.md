# vet clinic

This project is a simple solution for the clinic and its clients for easy interaction.
This is a platform where a business announces an event and receives information about its visitors, and the user
You can find out more information about it and make an appointment. the user can make an 
appointment and see the information he needs about his pet, and the clinic, in turn, 
maintains information about the treatment of pets.

## Database

Application use PostgreSQL database. For start the application you need Postgres server (jdbc:postgresql://localhost:
5432/eventPlanner) with created database 'eventsDB'. Database contains four tables.

* Table _owner_ - contains information about application users;
* Table _doctor_ - contains information about doctors;
* Table _pet_ - contains information about the pet;
* Table _vetCard_ - contains information about pet treatments;
* Table _l_pet_doctor_ - a table of links containing information about pets and the doctor who treated him;

## Available endpoints for unauthorized users

* http://localhost:8080/owner - POST method, registration user
* http://localhost:8080/pet - POST method, registration pet

## Available endpoints for users

* http://localhost:8080/owner/{id} - GET method, info about user 
* http://localhost:8080/owner/myPets/{id} - GET method, info about pets for users
* http://localhost:8080/owner/consult - POST method, registration user for consultation
* http://localhost:8080/owner - PUT method, change current user
* http://localhost:8080/owner/recCons/ - PUT method registers the user for an appointment with a doctor
* http://localhost:8080/owner{id} - DELETE method, delete current user's account 

* http://localhost:8080/card/{id} - GET method, info about ambulance card pet users

* http://localhost:8080/pet/{id} - GET method, info about pet  for users
* http://localhost:8080/pet - PUT method, change current pet users

## Available endpoints for doctor

* http://localhost:8080/owner/{id} - GET method, info about user
* http://localhost:8080/owner/consult - POST method, registration user for consultation

* http://localhost:8080/card/{id} - GET method, info about ambulance card pet users
* http://localhost:8080/card - POST method, create ambulance card pet
* http://localhost:8080/card - PUT method, edit ambulance card pet
* http://localhost:8080/event/{id} - DELETE method, delete ambulance card

* http://localhost:8080/pet - PUT method, change current pet users

* http://localhost:8080/doctor/{id} - GET method, info about doctor
* http://localhost:8080/doctor - GET method, info about all doctors doctor/createVK
* http://localhost:8080/doctor/createVK - POST method, create vetcard pet doctors
* http://localhost:8080/doctor - PUT method, change current doctors
* http://localhost:8080/doctor/change/{id}/{changeStatus} - PUT method, change status pet
* http://localhost:8080/doctor/{id} - DELETE method, delete doctor

## Available endpoints for admins

* http://localhost:8080/owner/{id} - GET method, info about user
* http://localhost:8080/owner/myPets/{id} - GET method, info about pets for users
* http://localhost:8080/owner/consult - POST method, registration user for consultation
* http://localhost:8080/owner - PUT method, change current user
* http://localhost:8080/owner/recCons/ - PUT method registers the user for an appointment with a doctor
* http://localhost:8080/owner{id} - DELETE method, delete current user's account

* http://localhost:8080/card/{id} - GET method, info about ambulance card pet users
* http://localhost:8080/card - POST method, create ambulance card pet
* http://localhost:8080/card - PUT method, edit ambulance card pet
* http://localhost:8080/event/{id} - DELETE method, delete ambulance card

* http://localhost:8080/pet/{id} - GET method, info about pet 
* http://localhost:8080/pet - GET method, info about all pets
* http://localhost:8080/pet - POST method, registration pet
* http://localhost:8080/pet - PUT method, change current pet users
* http://localhost:8080/pet/{id} - DELETE method, delete pet

* http://localhost:8080/doctor/{id} - GET method, info about doctor
* http://localhost:8080/doctor - GET method, info about all doctors doctor/createVK
* http://localhost:8080/doctor - POST method, create doctor or admin
* http://localhost:8080/doctor/createVK - POST method, create vetcard pet doctors
* http://localhost:8080/doctor - PUT method, change current doctors
* http://localhost:8080/doctor/change/{id}/{changeStatus} - PUT method, change status pet
* http://localhost:8080/doctor/{id} - DELETE method, delete doctor


