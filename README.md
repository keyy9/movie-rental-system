# Movie Rental System

A Java-based command-line application for managing a movie rental business. The system allows for movie management, customer registration, rental operations, and late return tracking.

## Features

- Add new movies to the system
- Register new customers
- Rent movies to customers
- Process movie returns
- Track late rentals
- View all movies and their availability
- View customer rental history

## Prerequisites

- Java 11 or higher
- Maven
- MySQL
- XAMPP (for MySQL server)

## Database Setup

1. Start MySQL server through XAMPP
2. Execute the SQL script `movie_rental_system.sql` to create the database and tables:
```bash
mysql -u root < movie_rental_system.sql
```

## Building the Project

1. Clone the repository
2. Navigate to the project directory
3. Build the project using Maven:
```bash
mvn clean package
```

## Running the Application

After building, run the application using:
```bash
java -jar target/movie-rental-system-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Usage

The application provides a command-line interface with the following options:

1. Add Movie - Add a new movie to the system
2. Register Customer - Register a new customer
3. Rent Movie - Rent a movie to a customer
4. Return Movie - Process a movie return
5. View Late Rentals - Show all overdue rentals
6. View All Movies - Display all movies in the system
7. View Available Movies - Show only available movies
8. View All Customers - List all registered customers
9. View Customer Rentals - Show rental history for a specific customer
0. Exit - Close the application

## Database Schema

### Movie Item
- ID (Primary Key)
- Title
- Genre
- Availability

### Customer
- ID (Primary Key)
- Name
- Email

### Rental
- ID (Primary Key)
- MovieItem_id (Foreign Key)
- Customer_id (Foreign Key)
- Rental_date
- Return_date
- Is_returned

## Note

The system considers a rental as "late" if it has been out for more than 14 days.
