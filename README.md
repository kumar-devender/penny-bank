# Penny Bank

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.
![System Design](https://i.ibb.co/PsLn2ST/System-arch-Diagram.png)

### Prerequisites

To run this application on your machine you need to have install docker in your machine. Please follow below link if docker is not installed on your machine.

```
https://docs.docker.com/docker-for-mac/install
```

After docker installation run following two docker command to start elastic and activeMQ docker containers
```
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "cluster.name=loan_application" docker.elastic.co/elasticsearch/elasticsearch:6.4.2
```
Please do not change the cluster name. It is being used in the service configs. If you change cluster name then change in the service too.
```
docker run -d -p 8161:8161 -p 61616:61616 -e ACTIVEMQ_ADMIN_LOGIN=admin -e ACTIVEMQ_ADMIN_PASSWORD=admin --name activemq webcenter/activemq
```

### Running application on new machine


Clone the project on your system

```
git clone https://github.com/kumar-devender/penny-bank.git
```

Enter in the cloned repository and run the applications

```
├── eureka
    Default Port: 8761
    To start use: mvn spring-boot:run
├── auth
    Default Port: 9180
    To start use: mvn spring-boot:run
├── gateway
    Default Port: 9080
    To start use: mvn spring-boot:run
├── customer service
    Default Port: 9280
    To start use: mvn spring-boot:run
├── loan application service
    Default Port: 9380
    To start use: mvn spring-boot:run
├── elastic search service
    Default Port: 9480
    To start use: mvn spring-boot:run
├── spring boot admin
    Default Port: 8765
    To start use: mvn spring-boot:run
```

Now the application is running on your machine. You can play with API ```http://localhost:<application-port-number>/swagger-ui.html``` 

Customer, loan application and elastic search API are accessible via gatekeeper too.

To access the API via gatekeeper first obtain access token for the user:
```
curl -u client:secret -X POST localhost:9180/oauth/token\?grant_type=password\&username=<replace-me-with-user-name>\&password=<replace-me-with-password>
```
Use the obtained access token in ``Authorization`` header of for the API request via gatekeeper. Here is sample request:
```
curl --location --request POST 'http://localhost:9080/api/loanapplications' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <replace-me-with-access-token>' \
--data-raw '{
  "customerId":3,
  "duration":6,
  "amount": 500
}'
```

## Special Note for Search API
The signature of search api look like this:
```
/api/search/<CATALOGUE>/<TEMPLATE_NAME>
```
Currently CATALOGUE : loanapplication and TEMPLATE_NAME : findLoanApplications

Here is the sample request.

```
curl --location --request POST 'http://localhost:9480/api/search/loanapplication/findLoanApplications' \
--header 'Content-Type: application/json' \
--data-raw '{
	"meta":"{\"amount\":{\"from\":250,\"to\":600},\"status\":\"CREATED\"}"
}'
```
This provide more flexibility to clients and and easy to scale for future requirements.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [ActiveMQ](https://activemq.apache.org) - Message broker for distributed systems
* [H2 Database]- Database
* [Elastic search]- Search Engine
* [Flyway] (http://flywaydb.org) Schema creation and migration
* [Spring Boot, lombok, springfox]
* [Lots of love]

## Further enhancement
* Distributed tracing using sleuth and zipkin
* Embedded DB can be replaced with Postgres