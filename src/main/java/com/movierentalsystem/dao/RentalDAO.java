package com.movierentalsystem.dao;

import com.movierentalsystem.model.Rental;
import com.movierentalsystem.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    
    public boolean createRental(Rental rental) {
        String sql = "INSERT INTO rental (movie_item_id, customer_id, rental_date, is_returned) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, rental.getMovieItemId());
            pstmt.setInt(2, rental.getCustomerId());
            pstmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDate()));
            pstmt.setBoolean(4, rental.isReturned());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    rental.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean returnMovie(int rentalId) {
        String sql = "UPDATE rental SET return_date = ?, is_returned = true WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            LocalDateTime returnDate = LocalDateTime.now();
            pstmt.setTimestamp(1, Timestamp.valueOf(returnDate));
            pstmt.setInt(2, rentalId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Rental> getLateRentals() {
        List<Rental> lateRentals = new ArrayList<>();
        // Consider a rental late if it's been out for more than 14 days
        String sql = "SELECT * FROM rental WHERE is_returned = false AND " +
                    "DATEDIFF(NOW(), rental_date) > 14";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setMovieItemId(rs.getInt("movie_item_id"));
                rental.setCustomerId(rs.getInt("customer_id"));
                rental.setRentalDate(rs.getTimestamp("rental_date").toLocalDateTime());
                Timestamp returnDate = rs.getTimestamp("return_date");
                if (returnDate != null) {
                    rental.setReturnDate(returnDate.toLocalDateTime());
                }
                rental.setReturned(rs.getBoolean("is_returned"));
                lateRentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lateRentals;
    }
    
    public List<Rental> getCurrentRentals() {
        List<Rental> currentRentals = new ArrayList<>();
        String sql = "SELECT * FROM rental WHERE is_returned = false";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setMovieItemId(rs.getInt("movie_item_id"));
                rental.setCustomerId(rs.getInt("customer_id"));
                rental.setRentalDate(rs.getTimestamp("rental_date").toLocalDateTime());
                Timestamp returnDate = rs.getTimestamp("return_date");
                if (returnDate != null) {
                    rental.setReturnDate(returnDate.toLocalDateTime());
                }
                rental.setReturned(rs.getBoolean("is_returned"));
                currentRentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentRentals;
    }
    
    public List<Rental> getCustomerRentals(int customerId) {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rental WHERE customer_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setMovieItemId(rs.getInt("movie_item_id"));
                rental.setCustomerId(rs.getInt("customer_id"));
                rental.setRentalDate(rs.getTimestamp("rental_date").toLocalDateTime());
                Timestamp returnDate = rs.getTimestamp("return_date");
                if (returnDate != null) {
                    rental.setReturnDate(returnDate.toLocalDateTime());
                }
                rental.setReturned(rs.getBoolean("is_returned"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
}
