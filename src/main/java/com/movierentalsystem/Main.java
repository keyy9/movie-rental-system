package com.movierentalsystem;

import com.movierentalsystem.gui.MainGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the GUI
        SwingUtilities.invokeLater(() -> {
            try {
                new MainGUI();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to start application: " + e.getMessage());
            }
        });
    }
}
