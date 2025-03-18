# Kafka project
Basic project to practice microservice communication using kafka. <br> 
Hotel service will publish a new message to the topic every time a new Hotel is posted. <br> 
Booking service will be listening to the topic, adding Hotels from the topic to a list that will be displayed by the frontend. <br> 

## Booking service - booking-kafka.properties
spring.kafka.bootstrap-servers=http://192.168.1.83:9092 <br>
spring.kafka.consumer.group-id=myGroup <br>
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer <br>
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer <br>
spring.kafka.consumer.properties.spring.json.trusted.packages=* <br>
spring.kafka.consumer.properties.spring.json.type.mapping=domain.model.Hotel:com.darioabuin.booking.application.dto.HotelDto # cast JSON Hotel to HotelDto <br>
auto.offset.reset=earliest # Load all messages from the topic, even before the consumer was listening <br>

## Hotel service - hotel-service-kafka.properties
topic=hotelsTopic <br>
spring.kafka.bootstrap-servers=http://192.168.1.83:9092 <br>
spring.kafka.consumer.group-id=myGroup <br>
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer <br>
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer <br>
