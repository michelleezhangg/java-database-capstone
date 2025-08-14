# Java Database Capstone

## Architecture Summary
This Spring Boot application uses both MVC and REST controllers. Thymeleaf templates are used for the Admin and Doctor dashboards, while REST APIs serve all other modules. The application interacts with two databases -- MySQL (for patient, doctor, appointment, and admin data) and MongoDB (for prescriptions). All controllers route requests through a common service layer, which in turn delegates to the appropriate repositories. MySQL uses JPA entities while MongoDB uses document models.

## Numbered Flow of Data and Control
1. User accesses *AdminDashboard* or *Appointment* pages.
2. The action is routed to the appropriate *Thymeleaf* or *REST controller*.
3. The *controller* calls the *service layer* which takes care of the request through business logic
4. The *service layer* communicates with the *repository layer* to perform data access operations with the databases.
5. Each *repository* interfaces directly with the underlying database engine.
6. When the data gets read or manipulated, **model binding** is performed so the application can use the data to make its computations and direct that data elsewhere.
7. The response is returned back to the interface whether that be through *Thymeleaf* or the web page itself back to the user.