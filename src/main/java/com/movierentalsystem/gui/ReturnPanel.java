package com.movierentalsystem.gui;

import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.model.Customer;
import com.movierentalsystem.model.Rental;
import com.movierentalsystem.service.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReturnPanel extends JPanel {
    private RentalService rentalService;
    private JTable rentalsTable;
    private DefaultTableModel tableModel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReturnPanel(RentalService rentalService) {
        this.rentalService = rentalService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create top panel with title and return button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Return Movies", SwingConstants.CENTER), BorderLayout.CENTER);
        
        JButton returnButton = new JButton("Return Selected Movie");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(returnButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Create table for current rentals
        String[] columnNames = {"Rental ID", "Movie Title", "Customer Name", "Rental Date", "Is Returned"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rentalsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rentalsTable);

        // Add components to panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Return button action
        returnButton.addActionListener(e -> {
            int selectedRow = rentalsTable.getSelectedRow();
            if (selectedRow != -1) {
                int rentalId = (int) rentalsTable.getValueAt(selectedRow, 0);
                if (rentalService.returnMovie(rentalId)) {
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Movie returned successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to return movie!", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a rental to return!", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Initial table load
        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Rental> currentRentals = rentalService.getCurrentRentals();
        
        for (Rental rental : currentRentals) {
            // Get movie and customer details
            MovieItem movie = rentalService.getMovieById(rental.getMovieItemId());
            Customer customer = rentalService.getCustomerById(rental.getCustomerId());
            
            if (movie != null && customer != null) {
                Object[] row = {
                    rental.getId(),
                    movie.getTitle(),
                    customer.getName(),
                    rental.getRentalDate().format(dateFormatter),
                    rental.isReturned() ? "Yes" : "No"
                };
                tableModel.addRow(row);
            }
        }
    }
}
