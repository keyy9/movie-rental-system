package com.movierentalsystem.gui;

import com.movierentalsystem.model.MovieItem;
import com.movierentalsystem.model.Customer;
import com.movierentalsystem.model.Rental;
import com.movierentalsystem.service.RentalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LateReturnsPanel extends JPanel {
    private RentalService rentalService;
    private JTable lateRentalsTable;
    private DefaultTableModel tableModel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LateReturnsPanel(RentalService rentalService) {
        this.rentalService = rentalService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create header
        JLabel headerLabel = new JLabel("Late Returns", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Create table
        String[] columnNames = {"Rental ID", "Movie Title", "Customer Name", "Rental Date", "Days Overdue"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lateRentalsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(lateRentalsTable);

        // Create info panel
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("* Movies are considered late after 14 days"));

        // Add components to panel
        add(headerLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        // Style the table
        lateRentalsTable.getTableHeader().setBackground(new Color(240, 240, 240));
        lateRentalsTable.setRowHeight(25);
        lateRentalsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lateRentalsTable.setGridColor(Color.LIGHT_GRAY);

        // Add custom renderer for days overdue column
        lateRentalsTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int days = (Integer) value;
                if (days > 30) {
                    c.setForeground(Color.RED);
                } else if (days > 14) {
                    c.setForeground(new Color(255, 140, 0)); // Dark Orange
                } else {
                    c.setForeground(table.getForeground());
                }
                return c;
            }
        });

        // Initial table load
        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Rental> lateRentals = rentalService.getLateRentals();
        LocalDateTime now = LocalDateTime.now();

        for (Rental rental : lateRentals) {
            // Get movie and customer details
            MovieItem movie = rentalService.getMovieById(rental.getMovieItemId());
            Customer customer = rentalService.getCustomerById(rental.getCustomerId());
            
            if (movie != null && customer != null) {
                // Calculate days overdue
                long daysOverdue = ChronoUnit.DAYS.between(rental.getRentalDate(), now) - 14;
                
                Object[] row = {
                    rental.getId(),
                    movie.getTitle(),
                    customer.getName(),
                    rental.getRentalDate().format(dateFormatter),
                    daysOverdue
                };
                tableModel.addRow(row);
            }
        }

        // Sort table by days overdue (descending)
        if (lateRentalsTable.getRowCount() > 0) {
            sortTableByDaysOverdue();
        }
    }

    private void sortTableByDaysOverdue() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        lateRentalsTable.setRowSorter(sorter);
        
        // Sort by days overdue column (index 4) in descending order
        sorter.setSortKeys(List.of(new RowSorter.SortKey(4, SortOrder.DESCENDING)));
        sorter.sort();
    }
}
