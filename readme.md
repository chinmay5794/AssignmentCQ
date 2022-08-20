Pre-requisites - Java 8, Maven, MySQL Server (username=root, password=welcome123, schema=ciq)

Technologies used - Spring Boot, Spring JPA, Gson, Hibernate Envers (for capturing data history), HtmlUnit (for web crawling) 

Commands to build application & start the server - <br/>
git clone https://github.com/chinmay5794/AssignmentCQ.git <br/>
mvn clean install -DskipTests <br/>
java -jar target/cq-1.0.0.jar <br/>

Output should be seen in tables ciq.dt_item & ciq.dt_item_property. <br/>
Tested on Ubuntu 20.04.1.

Few of the major missing items - <br/>
1. No service layer to massage results of repository layer
2. Error handling & notification
3. Controller layer & proper DTO objects
4. Object Composition might be a better a approach for creating appropriate Data Fetcher object
