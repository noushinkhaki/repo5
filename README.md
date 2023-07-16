# Text Analyser Service
Here is a guide to deploy and run the service:

The project can be built by Maven. Before building it, a kafka topic with "words.processed" name should be created. This should be done as the integration test involves the whole process as well as producing the response to Kafka. First, Kafka should be run by the following commands: (on windows)

{path to Kafka}>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

{path to Kafka}>.\bin\windows\kafka-server-start.bat .\config\server.properties

Then, the topic should be created by the following command:

{path to Kafka}>.\bin\windows\kafka-topics.bat --zookeeper localhost:2181 --topic words.processed -create --partitions 4 --replication-factor 1

After building it by Maven, the service is ready to be run by 2 either ways:

- Run TextAnalyserApplication class

Or

- Run it by maven command: mvn spring-boot:run

When the server is up, The endpoint can be called:

GET http://localhost:8080/betvictor/text?p=2&l=short

The related postman collection can be found in postman folder.
