# Movie Rental System

A Java-based GUI application for managing a movie rental business. The system allows for movie management, customer registration, rental operations, and late return tracking with a modern graphical interface.

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

## Features

- Modern graphical user interface
- Easy navigation with sidebar menu
- Movie management (add, view, track availability)
- Customer registration and management
- Rental operations (rent and return movies)
- Late return tracking with visual indicators
- Intuitive forms and tables for data display

## Running the Application

After building, run the application using:
```bash
java -jar target/movie-rental-system-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Usage

The application provides a graphical interface with the following sections:

- **Home** - Welcome screen
- **Movies** - Add and manage movies
- **Customers** - Register and manage customers
- **Rent** - Rent movies to customers
- **Return** - Process movie returns
- **Late Returns** - Track overdue rentals with color-coded indicators

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
