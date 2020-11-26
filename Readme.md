# Order Service

This application represents basic Order Api. It stores the orders in Mongo Database and 
before get the request controls the user authenticated or not. If user is not authenticated, 
application does not allow the user to make request for the resources.

#Requirements

This project requires:

* Java 11
* Maven 
* MongoDB

#Local Testing and startup

You can test the complete application locally by using a running docker-compose environment.
  The application consists in 2 docker containers, one running the application and other running a MongoDB image from the their official Dockhub repository.
  The webserver container will have the port :8080 exposed and linked to Mongo container connecting by default port : 27017

To start the app locally make the following steps:

* install java 11
* install docker
* start the docker containers
    * docker build -t springio/order-service-docker .
    * docker run -p 8080:8080 springio/order-service-docker
* control the docker images
    * docker images
* run whole docker and app
    * docker-compose up -d
    
* If you would like to stop the app, run the docker command
    * docker-compose down
    
After run the application locally, client credentials should get from yml file 
and use that to make request to get new oauth token
* security.oauth.client = client
* security.oauth.secret = secret
    
To see the kafka events run the commands locally:
* To run zookeeper:
    *  bin/zookeeper-server-start.sh config/zookeeper.properties
* To run kafka server:
    *   bin/kafka-server-start.sh config/server.properties
* To see the produced events in order_events topic:
    *   bin/kafka-console-consumer.sh --topic order_events --from-beginning --bootstrap-server localhost:9092

#Documentation

To see the swagger documentation, go to pages
* http://localhost:8080/swagger-ui.html
* http://localhost:8080/v2/api-docs

#Existing Database Tables

* User
* Order

#Future Improvements

As a result, events could listen from the topic from another service like payment-service
 and do another related jobs like payment calculation.