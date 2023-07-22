# Text Analyser Service
The service gets some texts from https://loripsum.net/ and analyses them in order to find the most frequent word. The service has the following endpoints:

- HTTP GET /betvictor/text

- HTTP GET /betvictor/history

## HTTP GET /betvictor/text

It returns the following response:

Response (JSON):

{

"freq_word": <most_frequent_word>

"avg_paragraph_size":<avg_paragraph_size>

"avg_paragraph_processing_time":<avg_paragraph_processing_time>

"total_processing_time": <total_processing_time>

}

where:

<most_frequent_word>- the word that was the most frequent in the paragraphs

<avg_paragraph_size> - the average size of a paragraph

<avg_paragraph_processing_time> - the average time spent analyzing a paragraph

<total_processing_time> - total processing time to generate the final response

The responses are sent to a Kafka topic.

## HTTP GET /betvictor/history

By this endpoint, the responses from the above endpoint are consumed from the Kafka topic, and stored in a H2 database. Then, the latest 10 messages are got from the database and returned as the response.

## Build and Run the Service

Here is a guide to build and run the service:

The project can be built by Maven. Before building it, a kafka topic with "words.processed" name should be created. This should be done as the integration test involves the whole process as well as producing and consuming the response to/from Kafka.

In this project, we use Kafka in a Docker container. The related file is docker-compose.yml, and it can be run by the following command:

docker-compose -f docker-compose.yml up -d

Then, we need to go inside the container in order to run Kafka commands. This is done by the following:

docker exec -it kafka /bin/sh

The related command files are in opt/Kafka_{version}/bin. So, run the following to get access to the commands:

cd opt/kafka_2.13-2.8.1/bin

Now you can create a topic by the following which has 4 partitions:

kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 4 --topic words.processed



After building it by Maven, the service is ready to be run by 2 either ways:

- Run TextAnalyserApplication class

Or

- Run it by maven command: mvn spring-boot:run

When the server is up, The endpoints can be called:

GET http://localhost:8080/betvictor/text?p=2&l=short

GET http://localhost:8080/betvictor/history

The related postman collection can be found in postman folder. 

An integration test is done by HistoryControllerTest.java class. First, it calls "GET /betvictor/text?p=1&l=short" end point for 11 times, the it returns 10 latest messages by calling "GET /betvictor/history".
