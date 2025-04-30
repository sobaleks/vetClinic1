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
* Table _appointment_ - stores appointment records at the vet clinic, linking doctors, pets, and owners with the date, duration, and status of the appointment;
* Table _doctor_schedule_ - stores doctors' work schedules, including dates, appointment times, and availability;
* 
## Available endpoints for unauthorized users

* http://localhost:8080/owner - POST method, registration user
* http://localhost:8080/pet - POST method, registration pet

## Available endpoints for users

* http://localhost:8080/owner/{id} - GET method, info about user 
* http://localhost:8080/owner/myPets/{id} - GET method, info about pets for users
* http://localhost:8080/owner/login - POST method, provides information by mail and password about owner
* http://localhost:8080/owner/consult - POST method, registration user for consultation
* http://localhost:8080/owner - PUT method, change current user
* http://localhost:8080/owner/recCons/ - PUT method registers the user for an appointment with a doctor
* http://localhost:8080/owner/{id} - DELETE method, delete current user's account 

* http://localhost:8080/card/{id} - GET method, info about ambulance card pet users
* http://localhost:8080/card/vaccinations/{id} - GET method, info about vaccinations pet 

* http://localhost:8080/pet/{id} - GET method, info about pet  for users
* http://localhost:8080/pet - PUT method, change current pet users

* http://localhost:8080/doctor/by-specialisation - GET method, info about doctors

* http://localhost:8080/appointment{id} - GET method, info about  appointment
* http://localhost:8080/appointment/pet/{id} - GET method, shows the records of a certain pet
* http://localhost:8080/appointment/owner/{id} - GET method, shows the records of a certain owner
* http://localhost:8080/appointment -  POST method, create appointment
* http://localhost:8080/appointment - PUT method, changes the pet's appointment
* http://localhost:8080/appointment{id} - DELETE method,  delete appointment

* http://localhost:8080/schedule/doctor/{doctor_id} - GET method, info about appointment doctors

## Available endpoints for doctor

* http://localhost:8080/owner/{id} - GET method, info about user
* http://localhost:8080/owner/consult - POST method, registration user for consultation

* http://localhost:8080/card/{id} - GET method, info about ambulance card pet users
* http://localhost:8080/card/vaccinations/{id} - GET method, info about vaccinations pet
* http://localhost:8080/card - POST method, create ambulance card pet
* http://localhost:8080/card - PUT method, edit ambulance card pet
* http://localhost:8080/card/{id} - DELETE method, delete ambulance card

* http://localhost:8080/pet - PUT method, change current pet users

* http://localhost:8080/doctor/{id} - GET method, info about doctor
* http://localhost:8080/doctor - GET method, info about all doctors doctor/createVK
* http://localhost:8080/doctor/search - GET method produces doctors of the specified specialty
* http://localhost:8080/doctor/login - POST method, provides information by mail and password about doctor
* http://localhost:8080/doctor/createVK - POST method, create vetcard pet doctors
* http://localhost:8080/doctor - PUT method, change current doctors
* http://localhost:8080/doctor/change/{id}/{changeStatus} - PUT method, change status pet
* http://localhost:8080/doctor/{id} - DELETE method, delete doctor

* http://localhost:8080/appointment{id} - GET method, info about  appointment
* http://localhost:8080/appointment/doctor/{id} - GET method, shows the records of a certain doctor
* http://localhost:8080/appointment/pet/{id} - GET method, shows the records of a certain pet

* http://localhost:8080/schedule/{id} - GET method, info about  doctor schedule
* http://localhost:8080/schedule/doctor/{doctor_id} - GET method, info about appointment doctors
* http://localhost:8080/schedule/day/{dayOfWeek} - GET method, shows entries for a specific day
* http://localhost:8080/schedule/doctor/{id}/active - GET method,shows active doctor's records
* http://localhost:8080/schedule/date/{date} - GET method, shows active records of the specified date

## Available endpoints for admins

* http://localhost:8080/owner/{id} - GET method, info about user
* http://localhost:8080/owner/myPets/{id} - GET method, info about pets for users
* http://localhost:8080/owner/login - POST method, provides information by mail and password
* http://localhost:8080/owner/consult - POST method, registration user for consultation
* http://localhost:8080/owner - PUT method, change current user
* http://localhost:8080/owner/recCons/ - PUT method registers the user for an appointment with a doctor
* http://localhost:8080/owner/{id} - DELETE method, delete current user's account

* http://localhost:8080/card/{id} - GET method, info about ambulance card pet users
* http://localhost:8080/card/vaccinations/{id} - GET method, info about vaccinations pet
* http://localhost:8080/card - POST method, create ambulance card pet
* http://localhost:8080/card - PUT method, edit ambulance card pet
* http://localhost:8080/card/{id} - DELETE method, delete ambulance card

* http://localhost:8080/pet/{id} - GET method, info about pet 
* http://localhost:8080/pet - GET method, info about all pets
* http://localhost:8080/pet - POST method, registration pet
* http://localhost:8080/pet - PUT method, change current pet users
* http://localhost:8080/pet/{id} - DELETE method, delete pet

* http://localhost:8080/doctor/{id} - GET method, info about doctor
* http://localhost:8080/doctor - GET method, info about all doctors doctor/createVK
* http://localhost:8080/doctor/by-specialisation - GET method, info about doctors
* http://localhost:8080/doctor/login - POST method, provides information by mail and password about doctor
* http://localhost:8080/doctor/search - GET method produces doctors of the specified specialty
* http://localhost:8080/doctor - POST method, create doctor or admin
* http://localhost:8080/doctor/createVK - POST method, create vetcard pet doctors
* http://localhost:8080/doctor - PUT method, change current doctors
* http://localhost:8080/doctor/change/{id}/{changeStatus} - PUT method, change status pet
* http://localhost:8080/doctor/{id} - DELETE method, delete doctor

* http://localhost:8080/appointment{id} - GET method, info about  appointment
* http://localhost:8080/appointment/doctor/{id} - GET method, shows the records of a certain doctor
* http://localhost:8080/appointment/pet/{id} - GET method, shows the records of a certain pet
* http://localhost:8080/appointment/owner/{id} - GET method, shows the records of a certain owner
* http://localhost:8080/appointment -  POST method, create appointment
* http://localhost:8080/appointment - PUT method, changes the pet's appointment
* http://localhost:8080/appointment{id} - DELETE method,  delete appointment

* http://localhost:8080/schedule/{id} - GET method, info about  doctor schedule
* http://localhost:8080/schedule/doctor/{doctor_id} - GET method, info about appointment doctors
* http://localhost:8080/schedule/day/{dayOfWeek} - GET method, shows entries for a specific day
* http://localhost:8080/schedule/doctor/{id}/active - GET method,shows active doctor's records
* http://localhost:8080/schedule/date/{date} - GET method, shows active records of the specified date
* http://localhost:8080/schedule - POST method, create doctor schedule
* http://localhost:8080/schedule - PUT method, change doctor schedule
* http://localhost:8080/schedule/{id} - DELETE method,delete doctor schedule
