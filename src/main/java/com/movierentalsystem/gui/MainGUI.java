package com.movierentalsystem.gui;

import com.movierentalsystem.service.RentalService;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private RentalService rentalService;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private MovieManagementPanel moviePanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;
    private ReturnPanel returnPanel;
    private LateReturnsPanel lateReturnsPanel;

    public MainGUI() {
        rentalService = new RentalService();
        initializeFrame();
        createComponents();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Movie Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createComponents() {
        // Create the sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(51, 51, 51));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));

        // Create menu buttons
        String[] menuItems = {"Home", "Movies", "Customers", "Rent", "Return", "Late Returns"};
        for (String menuItem : menuItems) {
            JButton button = createMenuButton(menuItem);
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 1))); // Spacing between buttons
        }

        // Create the content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Create and add panels
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Movie Rental System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(welcomeLabel, BorderLayout.CENTER);
        
        moviePanel = new MovieManagementPanel(rentalService);
        customerPanel = new CustomerPanel(rentalService);
        rentalPanel = new RentalPanel(rentalService);
        returnPanel = new ReturnPanel(rentalService);
        lateReturnsPanel = new LateReturnsPanel(rentalService);

        // Add panels to card layout
        contentPanel.add(homePanel, "Home");
        contentPanel.add(moviePanel, "Movies");
        contentPanel.add(customerPanel, "Customers");
        contentPanel.add(rentalPanel, "Rent");
        contentPanel.add(returnPanel, "Return");
        contentPanel.add(lateReturnsPanel, "Late Returns");

        // Add components to frame
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(180, 40));
        
        // Style the button
        button.setBackground(new Color(51, 51, 51));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(75, 75, 75));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(51, 51, 51));
            }
        });

        // Add action listener
        button.addActionListener(e -> cardLayout.show(contentPanel, text));

        return button;
    }

    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show GUI
        SwingUtilities.invokeLater(() -> new MainGUI());
    }

    // Method to refresh all panels
    public void refreshPanels() {
        if (moviePanel != null) {
            moviePanel.refreshTable();
        }
        if (customerPanel != null) {
            customerPanel.refreshTable();
        }
        if (rentalPanel != null) {
            rentalPanel.refreshCustomers();
            rentalPanel.refreshTable();
        }
        if (returnPanel != null) {
            returnPanel.refreshTable();
        }
        if (lateReturnsPanel != null) {
            lateReturnsPanel.refreshTable();
        }
    }
}
