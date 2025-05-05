package com.movierentalsystem.gui;

import com.movierentalsystem.model.Customer;
import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.service.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RentalPanel extends JPanel {
    private RentalService rentalService;
    private JComboBox<CustomerItem> customerComboBox;
    private JTable availableMoviesTable;
    private DefaultTableModel tableModel;

    public RentalPanel(RentalService rentalService) {
        this.rentalService = rentalService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Customer selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Select Customer:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        customerComboBox = new JComboBox<>();
        formPanel.add(customerComboBox, gbc);

        // Rent button
        JButton rentButton = new JButton("Rent Selected Movie");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(rentButton, gbc);

        // Available movies table
        String[] columnNames = {"ID", "Title", "Genre"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        availableMoviesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(availableMoviesTable);

        // Add components to panel
        add(new JLabel("Rent Movie", SwingConstants.CENTER), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Rent button action
        rentButton.addActionListener(e -> {
            int selectedRow = availableMoviesTable.getSelectedRow();
            CustomerItem selectedCustomer = (CustomerItem) customerComboBox.getSelectedItem();

            if (selectedRow != -1 && selectedCustomer != null) {
                int movieId = (int) availableMoviesTable.getValueAt(selectedRow, 0);
                if (rentalService.rentMovie(movieId, selectedCustomer.getId())) {
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Movie rented successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to rent movie!", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select both a customer and a movie!", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Initial data load
        refreshCustomers();
        refreshTable();
    }

    public void refreshCustomers() {
        customerComboBox.removeAllItems();
        List<Customer> customers = rentalService.getAllCustomers();
        for (Customer customer : customers) {
            customerComboBox.addItem(new CustomerItem(customer));
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<MovieItem> movies = rentalService.getAvailableMovies();
        for (MovieItem movie : movies) {
            Object[] row = {
                movie.getId(),
                movie.getTitle(),
                movie.getGenre()
            };
            tableModel.addRow(row);
        }
    }

    // Helper class for ComboBox items
    private static class CustomerItem {
        private final Customer customer;

        public CustomerItem(Customer customer) {
            this.customer = customer;
        }

        public int getId() {
            return customer.getId();
        }

        @Override
        public String toString() {
            return customer.getName() + " (" + customer.getEmail() + ")";
        }
    }
}
