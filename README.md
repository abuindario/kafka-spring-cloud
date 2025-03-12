# Kafka project
Basic project to practice microservice communication using kafka. 
Hotel service will publish a new message to the topic every time a new Hotel is posted. 
Booking service will be listening to the topic, adding Hotels from the topic to a list that will be displayed by the fronted. 

## Booking service - booking-kafka.properties
spring.kafka.bootstrap-servers=http://192.168.1.83:9092
spring.kafka.consumer.group-id=myGroup
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.kafka.consumer.properties.spring.json.type.mapping=domain.model.Hotel:com.darioabuin.booking.application.dto.HotelDto # cast JSON Hotel to HotelDto
auto.offset.reset=earliest # Load all messages from the topic, even before the consumer was listening

## Hotel service - hotel-service-kafka.properties
topic=hotelsTopic
spring.kafka.bootstrap-servers=http://192.168.1.83:9092
spring.kafka.consumer.group-id=myGroup
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
