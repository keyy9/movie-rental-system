package com.movierentalsystem.dao;

import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieItemDAO {
    
    public boolean addMovie(MovieItem movie) {
        String sql = "INSERT INTO movie_item (title, genre, availability) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setBoolean(3, movie.isAvailable());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    movie.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public MovieItem getMovie(int id) {
        String sql = "SELECT * FROM movie_item WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                MovieItem movie = new MovieItem();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setAvailability(rs.getBoolean("availability"));
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<MovieItem> getAllMovies() {
        List<MovieItem> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_item";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                MovieItem movie = new MovieItem();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setAvailability(rs.getBoolean("availability"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    public boolean updateMovieAvailability(int id, boolean availability) {
        String sql = "UPDATE movie_item SET availability = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, availability);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<MovieItem> getAvailableMovies() {
        List<MovieItem> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_item WHERE availability = true";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                MovieItem movie = new MovieItem();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setAvailability(rs.getBoolean("availability"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
