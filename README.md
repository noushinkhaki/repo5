# Text Analyser Service
Here is a guide to deploy and run the service.
The project can be built by Maven. Before building it, a kafka topic with "words.processed" name should be created. This should be done as the integration test involves the whole process as well as producing the response to Kafka. First, Kafka should be run by the following commands: (on windows)
{path to Kafka}>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
{path to Kafka}>.\bin\windows\kafka-server-start.bat .\config\server.properties
Then, the topic should be created by the following command:
.\bin\windows\kafka-topics.bat --zookeeper localhost:2181 --topic mytopic -create --partitions 4 --replication-factor 1
Now, the project can be built by Maven. After building it, you can run TextAnalyserApplication class to run the service by SpringBoot. When the server is up, The endpoint can be called:
GET http://localhost:8080/betvictor/text?p=2&l=short
