Pre-requisites - Java 8, Maven, MySQL Server (username=root, password=welcome123, schema=ciq)

Technologies used - Spring Boot, Spring JPA, Gson, Hibernate Envers (for capturing data history), HtmlUnit (for web crawling) 

Commands to start the server - <br/>
git clone https://github.com/chinmay5794/AssignmentCQ.git <br/>
mvn clean install -DskipTests <br/>
java -jar target/cq-1.0.0.jar <br/>

Output should be seen in tables ciq.dt_item & ciq.dt_item_property.
Tested on Ubuntu 20.04.1.
