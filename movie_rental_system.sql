-- Create the database
CREATE DATABASE IF NOT EXISTS movie_rental_system;
USE movie_rental_system;

-- Create MovieItem table
CREATE TABLE IF NOT EXISTS movie_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    availability BOOLEAN DEFAULT true
);

-- Create Customer table
CREATE TABLE IF NOT EXISTS customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Create Rentals table
CREATE TABLE IF NOT EXISTS rental (
    id INT PRIMARY KEY AUTO_INCREMENT,
    movie_item_id INT,
    customer_id INT,
    rental_date DATETIME NOT NULL,
    return_date DATETIME,
    is_returned BOOLEAN DEFAULT false,
    FOREIGN KEY (movie_item_id) REFERENCES movie_item(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);
