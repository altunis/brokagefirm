The project can easily run with **mvn spring-boot:run** command. <br>
When you type localhost:8080 on browser, the application navigates you to the login (localhost:8080/login) page which you can log in with admin user and perform all operations.  <br>
Credentials of admin Username : **admin** Password : **1234** . <br>
There you will see the swagger interface.<br>
By using the swagger ui, you can create customers and set their password via the APIs. <br>
You can invoke /logout URL to logout and log in again with regular customer so you can test the other cases.<br>
You can access the in-memory database via **http://localhost:8080/h2-console** You can see the default credentials and datasource url on application.properties.<br>
**The first bonus task has been accomplished.**<br>
