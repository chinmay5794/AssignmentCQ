Pre-requisites - Java 8, Maven, MySQL (user credentials - root/welcome123, schema "ciq" should exist)

Commands to start the server - 
git clone https://github.com/chinmay5794/AssignmentCQ.git
mvn clean install -DskipTests
java -jar target/cj-1.0.0.jar

Output should be seen in tables ciq.dt_item & ciq.dt_item_property.