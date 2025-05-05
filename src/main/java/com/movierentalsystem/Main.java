package com.movierentalsystem;

import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.model.Customer;
import com.movierentalsystem.model.Rental;
import com.movierentalsystem.service.RentalService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static RentalService rentalService = new RentalService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    registerCustomer();
                    break;
                case 3:
                    rentMovie();
                    break;
                case 4:
                    returnMovie();
                    break;
                case 5:
                    viewLateRentals();
                    break;
                case 6:
                    viewAllMovies();
                    break;
                case 7:
                    viewAvailableMovies();
                    break;
                case 8:
                    viewAllCustomers();
                    break;
                case 9:
                    viewCustomerRentals();
                    break;
                case 0:
                    System.out.println("Thank you for using Movie Rental System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Movie Rental System ===");
        System.out.println("1. Add Movie");
        System.out.println("2. Register Customer");
        System.out.println("3. Rent Movie");
        System.out.println("4. Return Movie");
        System.out.println("5. View Late Rentals");
        System.out.println("6. View All Movies");
        System.out.println("7. View Available Movies");
        System.out.println("8. View All Customers");
        System.out.println("9. View Customer Rentals");
        System.out.println("0. Exit");
    }

    private static void addMovie() {
        System.out.println("\n=== Add New Movie ===");
        String title = getStringInput("Enter movie title: ");
        String genre = getStringInput("Enter movie genre: ");
        
        if (rentalService.addMovie(title, genre)) {
            System.out.println("Movie added successfully!");
        } else {
            System.out.println("Failed to add movie.");
        }
    }

    private static void registerCustomer() {
        System.out.println("\n=== Register New Customer ===");
        String name = getStringInput("Enter customer name: ");
        String email = getStringInput("Enter customer email: ");
        
        if (rentalService.registerCustomer(name, email)) {
            System.out.println("Customer registered successfully!");
        } else {
            System.out.println("Failed to register customer.");
        }
    }

    private static void rentMovie() {
        System.out.println("\n=== Rent Movie ===");
        viewAvailableMovies();
        int movieId = getIntInput("Enter movie ID to rent: ");
        viewAllCustomers();
        int customerId = getIntInput("Enter customer ID: ");
        
        if (rentalService.rentMovie(movieId, customerId)) {
            System.out.println("Movie rented successfully!");
        } else {
            System.out.println("Failed to rent movie.");
        }
    }

    private static void returnMovie() {
        System.out.println("\n=== Return Movie ===");
        List<Rental> currentRentals = rentalService.getCurrentRentals();
        if (currentRentals.isEmpty()) {
            System.out.println("No current rentals found.");
            return;
        }
        
        System.out.println("Current Rentals:");
        for (Rental rental : currentRentals) {
            System.out.println("Rental ID: " + rental.getId() + 
                             ", Movie ID: " + rental.getMovieItemId() + 
                             ", Customer ID: " + rental.getCustomerId());
        }
        
        int rentalId = getIntInput("Enter rental ID to return: ");
        if (rentalService.returnMovie(rentalId)) {
            System.out.println("Movie returned successfully!");
        } else {
            System.out.println("Failed to return movie.");
        }
    }

    private static void viewLateRentals() {
        System.out.println("\n=== Late Rentals ===");
        List<Rental> lateRentals = rentalService.getLateRentals();
        if (lateRentals.isEmpty()) {
            System.out.println("No late rentals found.");
            return;
        }
        
        for (Rental rental : lateRentals) {
            System.out.println("Rental ID: " + rental.getId() + 
                             ", Movie ID: " + rental.getMovieItemId() + 
                             ", Customer ID: " + rental.getCustomerId() + 
                             ", Rental Date: " + rental.getRentalDate());
        }
    }

    private static void viewAllMovies() {
        System.out.println("\n=== All Movies ===");
        List<MovieItem> movies = rentalService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }
        
        for (MovieItem movie : movies) {
            System.out.println("ID: " + movie.getId() + 
                             ", Title: " + movie.getTitle() + 
                             ", Genre: " + movie.getGenre() + 
                             ", Available: " + movie.isAvailable());
        }
    }

    private static void viewAvailableMovies() {
        System.out.println("\n=== Available Movies ===");
        List<MovieItem> movies = rentalService.getAvailableMovies();
        if (movies.isEmpty()) {
            System.out.println("No available movies found.");
            return;
        }
        
        for (MovieItem movie : movies) {
            System.out.println("ID: " + movie.getId() + 
                             ", Title: " + movie.getTitle() + 
                             ", Genre: " + movie.getGenre());
        }
    }

    private static void viewAllCustomers() {
        System.out.println("\n=== All Customers ===");
        List<Customer> customers = rentalService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getId() + 
                             ", Name: " + customer.getName() + 
                             ", Email: " + customer.getEmail());
        }
    }

    private static void viewCustomerRentals() {
        System.out.println("\n=== Customer Rentals ===");
        viewAllCustomers();
        int customerId = getIntInput("Enter customer ID: ");
        
        List<Rental> rentals = rentalService.getCustomerRentals(customerId);
        if (rentals.isEmpty()) {
            System.out.println("No rentals found for this customer.");
            return;
        }
        
        for (Rental rental : rentals) {
            System.out.println("Rental ID: " + rental.getId() + 
                             ", Movie ID: " + rental.getMovieItemId() + 
                             ", Rental Date: " + rental.getRentalDate() + 
                             ", Returned: " + rental.isReturned());
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
