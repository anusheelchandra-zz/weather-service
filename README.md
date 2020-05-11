# Weather Service 

Weather Service is a microservice for getting current/historic weather by city.It has a RestFul API to integrate with.

### Topics
  * [Assumptions](#assumptions)
  * [What does it do ?](#technical-details)
  * [How to use it ?](#guide)
  * [Tech Stack](#tech-stack)
  * [Screen-shot](#screen-shot)
  
###  [Assumptions](#assumptions)
 1. For current weather, one must send city name(london) or city name along with ISO country code(london,gb).
 2. For historic weather if no history is present application then calls open weather api for current weather.
 3. For historic weather, it returns complete history of weather for specific city and average temperature & pressure
    calculated on basis on last 5 records. 
 4. There are integration tests which are @Disabled as it depends on open weather api but can be ran manually.


###  [What does it do ?](#technical-details)
This api returns current and historic weather response for a given location. For the historic weather response, it returns the list
of recorded weather history with average pressure and average temperature calculated based upon last 5 weather records.

###  [How to use it ?](#guide)

### Build and Run
```
One can run the WeatherServiceApplication.java from the IDE or execute mvn spring-boot:run 
on the terminal inside the project folder.

Else:

mvn clean install 

mvn spring-boot:run
```

One can use this api very easily by hitting the RESTFul endpoints which when provided with appropriate location will return the weather response.

To connect with the endpoints (http://localhost:8080/current?location=Berlin and http://localhost:8080/history?location=Berlin) 
you can refer to below Swagger Ui.
* [Swagger UI](http://localhost:8080/swagger-ui.html#/)

One can simply send a request to endpoint (http://localhost:8080/current?location=London) and will receive current weather.
One can simply send a request to endpoint (http://localhost:8080/history?location=London) and will receive historic weather.


###[Tech Stack](#tech-stack)
Java 11, Spring Boot, Maven, Lombok, Mockito, Junit5, AssertJ, Spring Data JPA and H2 database for persistence.
This microservice has been built with IntelliJ IDE and formatted with google-java-format.

###[Screen-shot](#screen-shot)

![alt text](https://github.com/anusheelchandra/weather-service/blob/master/src/test/resources/ScreenShot1.png)
![alt text](https://github.com/anusheelchandra/weather-service/blob/master/src/test/resources/ScreenShot2.png)
![alt text](https://github.com/anusheelchandra/weather-service/blob/master/src/test/resources/ScreenShot3.png)
![alt text](https://github.com/anusheelchandra/weather-service/blob/master/src/test/resources/ScreenShot4.png)
