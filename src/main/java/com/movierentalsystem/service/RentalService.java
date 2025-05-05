package com.movierentalsystem.service;

import com.movierentalsystem.dao.MovieItemDAO;
import com.movierentalsystem.dao.CustomerDAO;
import com.movierentalsystem.dao.RentalDAO;
import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.model.Customer;
import com.movierentalsystem.model.Rental;

import java.util.List;

public class RentalService {
    private MovieItemDAO movieItemDAO;
    private CustomerDAO customerDAO;
    private RentalDAO rentalDAO;

    public RentalService() {
        this.movieItemDAO = new MovieItemDAO();
        this.customerDAO = new CustomerDAO();
        this.rentalDAO = new RentalDAO();
    }

    // Movie management
    public boolean addMovie(String title, String genre) {
        MovieItem movie = new MovieItem(title, genre);
        return movieItemDAO.addMovie(movie);
    }

    public List<MovieItem> getAllMovies() {
        return movieItemDAO.getAllMovies();
    }

    public List<MovieItem> getAvailableMovies() {
        return movieItemDAO.getAvailableMovies();
    }

    // Customer management
    public boolean registerCustomer(String name, String email) {
        // Check if email already exists
        if (customerDAO.getCustomerByEmail(email) != null) {
            System.out.println("Email already registered!");
            return false;
        }
        Customer customer = new Customer(name, email);
        return customerDAO.registerCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    // Rental operations
    public boolean rentMovie(int movieId, int customerId) {
        // Check if movie exists and is available
        MovieItem movie = movieItemDAO.getMovie(movieId);
        if (movie == null || !movie.isAvailable()) {
            System.out.println("Movie not available for rent!");
            return false;
        }

        // Check if customer exists
        Customer customer = customerDAO.getCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return false;
        }

        // Create rental
        Rental rental = new Rental(movieId, customerId);
        if (rentalDAO.createRental(rental)) {
            // Update movie availability
            return movieItemDAO.updateMovieAvailability(movieId, false);
        }
        return false;
    }

    public boolean returnMovie(int rentalId) {
        // Get rental details first
        List<Rental> currentRentals = rentalDAO.getCurrentRentals();
        Rental rentalToReturn = currentRentals.stream()
                .filter(r -> r.getId() == rentalId)
                .findFirst()
                .orElse(null);

        if (rentalToReturn == null) {
            System.out.println("Rental not found or already returned!");
            return false;
        }

        // Update rental and movie availability
        if (rentalDAO.returnMovie(rentalId)) {
            return movieItemDAO.updateMovieAvailability(rentalToReturn.getMovieItemId(), true);
        }
        return false;
    }

    // Late return tracking
    public List<Rental> getLateRentals() {
        return rentalDAO.getLateRentals();
    }

    public List<Rental> getCurrentRentals() {
        return rentalDAO.getCurrentRentals();
    }

    public List<Rental> getCustomerRentals(int customerId) {
        return rentalDAO.getCustomerRentals(customerId);
    }
}
