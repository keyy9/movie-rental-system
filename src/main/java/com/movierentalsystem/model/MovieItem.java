package com.movierentalsystem.model;

public class MovieItem {
    private int id;
    private String title;
    private String genre;
    private boolean availability;

    public MovieItem() {}

    public MovieItem(String title, String genre) {
        this.title = title;
        this.genre = genre;
        this.availability = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public boolean isAvailable() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    @Override
    public String toString() {
        return "MovieItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", availability=" + availability +
                '}';
    }
}
