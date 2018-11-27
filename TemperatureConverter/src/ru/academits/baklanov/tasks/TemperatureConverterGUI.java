package ru.academits.baklanov.tasks;

import javax.swing.*;

public class TemperatureConverterGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("My first GUI application with Temperature Converter");

            frame.setSize(600, 400);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}