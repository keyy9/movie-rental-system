package com.movierentalsystem.gui;

import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.service.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovieManagementPanel extends JPanel {
    private RentalService rentalService;
    private JTextField titleField;
    private JTextField genreField;
    private JTable movieTable;
    private DefaultTableModel tableModel;

    public MovieManagementPanel(RentalService rentalService) {
        this.rentalService = rentalService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Movie Title:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        // Genre field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Genre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        genreField = new JTextField(20);
        formPanel.add(genreField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Movie");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Table
        String[] columnNames = {"ID", "Title", "Genre", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        movieTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(movieTable);

        // Add components to panel
        add(new JLabel("Movie Management", SwingConstants.CENTER), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Add button action
        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String genre = genreField.getText().trim();
            
            if (!title.isEmpty() && !genre.isEmpty()) {
                if (rentalService.addMovie(title, genre)) {
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Movie added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add movie!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear button action
        clearButton.addActionListener(e -> clearFields());

        // Initial table load
        refreshTable();
    }

    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<MovieItem> movies = rentalService.getAllMovies();
        for (MovieItem movie : movies) {
            Object[] row = {
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.isAvailable()
            };
            tableModel.addRow(row);
        }
    }
}
