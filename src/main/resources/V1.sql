CREATE TABLE car (
  id SERIAL PRIMARY KEY,
  brand VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  year INT NOT NULL,
  color VARCHAR(30) NOT NULL,
  engine_type VARCHAR(30),
  horsepower INT,
  transmission VARCHAR(30),
  seating_capacity INT,
  mileage INT,
  registration_number VARCHAR(50)
);
