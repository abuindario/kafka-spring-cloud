CREATE TABLE IF NOT EXISTS FLIGHT (
	id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
	company VARCHAR(50),
	flightDate TIMESTAMP,
	price NUMERIC(8,2),
	availableSeats INT
);