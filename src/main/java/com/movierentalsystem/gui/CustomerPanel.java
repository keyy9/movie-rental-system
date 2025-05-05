package com.movierentalsystem.gui;

import com.movierentalsystem.model.Customer;
import com.movierentalsystem.service.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {
    private RentalService rentalService;
    private JTextField nameField;
    private JTextField emailField;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    public CustomerPanel(RentalService rentalService) {
        this.rentalService = rentalService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Email field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton registerButton = new JButton("Register Customer");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Table
        String[] columnNames = {"ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        // Add components to panel
        add(new JLabel("Customer Management", SwingConstants.CENTER), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Register button action
        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            
            if (!name.isEmpty() && !email.isEmpty()) {
                if (rentalService.registerCustomer(name, email)) {
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Customer registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register customer! Email might already be registered.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear button action
        clearButton.addActionListener(e -> clearFields());

        // Initial table load
        refreshTable();
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Customer> customers = rentalService.getAllCustomers();
        for (Customer customer : customers) {
            Object[] row = {
                customer.getId(),
                customer.getName(),
                customer.getEmail()
            };
            tableModel.addRow(row);
        }
    }
}
