Spring Boot Security JWT Authentication Example
This project serves as an example of implementing JSON Web Token (JWT) based authentication mechanism using Spring Boot and Spring Security.

Project Purpose
The primary objective of this project is to demonstrate the implementation of a robust authentication mechanism for securing a web application. JSON Web Token (JWT) is employed for user authentication. The project enables users to sign up, log in, and control access to specific endpoints for authorized users.

Recently Added Features
With the latest update, the project now includes "post," "like," and "comment" functionalities. Users can create posts, like posts, and comment on posts using these features.

Access Control
It's important to note that access to these features is restricted to authorized users only. Access control has been implemented for each of these features, ensuring that only authorized users with the correct JWT token can access them.

Technologies Used
Java
Spring Boot v3.2.2
Spring Security v6
JWT (JSON Web Token) v0.12.3
PostgreSQL
Maven
How to Run the Project
Clone the project to your computer or download it as a ZIP file.
Open the project in a Java IDE (e.g., IntelliJ IDEA or Eclipse).
Open the src/main/resources/application.properties file and configure the necessary properties.
Use Maven to install project dependencies (mvn clean install).
Run the project by right-clicking on it or start the application by using the mvn spring-boot:run command in the terminal.
Once the application is successfully running, you can access it through your browser or API testing tool (e.g., Postman).
Endpoints
/signup: This endpoint accepts a POST request to create a new user registration. It expects a JSON body containing username, email, and password information.
/login: This endpoint accepts a POST request to log in with an existing user. It expects a JSON body containing username or email and password information.
/admin: This is a private endpoint accessible to authorized users only. It can only be accessed by users with the correct JWT token.
Contributing
If you encounter any bugs or issues, please open an issue on GitHub.
If you have any development suggestions, please submit a pull request on GitHub.
License
This project is licensed under the MIT License. See the LICENSE file for more information.
