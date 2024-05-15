# README
## Bootstrap instructions

To build and run this service, Java and Apache Maven must be installed, with their respective environment variables set up.
I used JDK 17 and Apache Maven 3.9.6, but newer versions should work too.

1) Clone this repository to a location of your choosing.

2) Open either Command Prompt or Windows PowerShell, or the equivalent on other operating systems, and change the working directory, using cd or the equivalent on other operating systems, to the directory the repository is located in.

3) Run the command "mvn clean package" to generate a .jar file that contains the service.

4) Optional: run "mvn javadoc:javadoc" to create documentation files for the service.

5) Change the working directory to the "target" directory that was created following the "mvn clean package" command. This directory contains the .jar file and the documentation files in the "site" directory if they were created.

6) Run the command "java -jar CodeScreen_6ja9hs6b-1.0.0.jar" to run the service, which will start the server locally on your machine.

7) You can now use a tool like Postman to perform GET and PUT requests on the server using the /ping, /load and /authorize paths. The URL of the server is "http://localhost:8080".

## Design considerations

When I designed the service, I felt that the best course of action was to use a framework that is designed to be used for small services, while also being relatively straightforward to implement compared to other frameworks. Thus, I decided to use Spring Boot as the basis for this service.

Spring Boot's concept of using Controller classes to handle specific paths made implementing the underlying systems that process the GET and PUT requests a concise and structured process. Each request and response is handled similarly to how a method/function works when given parameters and later returns some value. This results in a logic flow that is easy to follow and trace, and also made implementing the Event Sourcing pattern a straightforward process as well.

Additionally, Spring Boot handles processes such as serialization and deserialization automatically, further simplifying the overall design of the service.

Lastly, Spring Boot comes with its own robust testing framework, reducing the need for other dependencies and also keeping the testing structure similar throughout all tests.

## Assumptions

While designing the service, I made some assumptions about what possible inputs can be present in the JSON body that users use for sending requests to the server:

1) The Message ID can be duplicated between requests, or be empty entirely.
2) The User ID can be empty entirely.
3) The transaction amount can be in a non-numeric form, such as 10a.01, +100.1, or many other possibilities.
4) The transaction currency might not follow the ISO 4217 standard of 3 uppercase letters. However, fictional currencies are permitted.
5) The transaction request might be a CREDIT when it is supposed to be a DEBIT, or vice versa. It could even be a different string altogether.

## Deployment considerations

If I were to deploy this service, I would use Amazon Web Services (AWS), because of its wide reach, affordable cost, and documented compatibility with Java and Spring Boot.

Docker can be used to containerize this service, which can then be hosted on AWS through Amazon Elastic Container Service (Amazon ECS).