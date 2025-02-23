The project can easily run with **mvn spring-boot:run** command. 
After you navigate localhost:8080, the application navigates you to the login page which you can log in with admin user and perform all operations. Username : **admin** Password : **1234** . 
There you will see the swagger interface.
By using the swagger ui, you can create customers and set their password via the APIs. 
You can invoke /logout URL to logout and log in again with regular customer so you can test the other cases.
You can access the in-memory database via **http://localhost:8080/h2-console** You can see the default credentials and datasource url on application.properties.
**The first bonus task has been accomplished.**
