package com.movierentalsystem.model;

import java.time.LocalDateTime;

public class Rental {
    private int id;
    private int movieItemId;
    private int customerId;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private boolean isReturned;

    public Rental() {}

    public Rental(int movieItemId, int customerId) {
        this.movieItemId = movieItemId;
        this.customerId = customerId;
        this.rentalDate = LocalDateTime.now();
        this.isReturned = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMovieItemId() { return movieItemId; }
    public void setMovieItemId(int movieItemId) { this.movieItemId = movieItemId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public LocalDateTime getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDateTime rentalDate) { this.rentalDate = rentalDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { isReturned = returned; }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", movieItemId=" + movieItemId +
                ", customerId=" + customerId +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", isReturned=" + isReturned +
                '}';
    }
}
