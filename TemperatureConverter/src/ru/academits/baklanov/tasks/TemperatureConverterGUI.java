package ru.academits.baklanov.tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TemperatureConverterGUI {
    public TemperatureConverterGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("My first GUI application with Temperature Converter");

            frame.setSize(500, 200);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
            frame.add(infoPanel, BorderLayout.NORTH);

            JPanel convertButtonPanel = new JPanel();
            frame.add(convertButtonPanel, BorderLayout.SOUTH);

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
            frame.add(inputPanel, BorderLayout.LINE_START);

            JPanel outputPanel = new JPanel();
            outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.PAGE_AXIS));
            frame.add(outputPanel, BorderLayout.LINE_END);

            JPanel scalesChoicePanel = new JPanel();
            scalesChoicePanel.setLayout(new BoxLayout(scalesChoicePanel, BoxLayout.LINE_AXIS));
            frame.add(scalesChoicePanel, BorderLayout.CENTER);

            JPanel scalesFromPanel = new JPanel();
            JPanel scalesToPanel = new JPanel();
            scalesFromPanel.setLayout(new BoxLayout(scalesFromPanel, BoxLayout.PAGE_AXIS));
            scalesToPanel.setLayout(new BoxLayout(scalesToPanel, BoxLayout.PAGE_AXIS));
            scalesChoicePanel.add(scalesFromPanel);
            scalesChoicePanel.add(Box.createHorizontalGlue());
            scalesChoicePanel.add(scalesToPanel);

            JLabel infoLabel1 = new JLabel("Это программа для перевода температуры из одной шкалы в другую,");
            JLabel infoLabel2 = new JLabel("(доступные шкалы: Цельсия, Фаренгейта, Кельвина).");
            infoPanel.add(infoLabel1);
            infoPanel.add(infoLabel2);
            infoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

            JRadioButton celsiusFromButton = new JRadioButton("шкала Цельсия", true);
            scalesFromPanel.add(celsiusFromButton);

            JRadioButton fahrenheitFromButton = new JRadioButton("шкала Фаренгейта");
            scalesFromPanel.add(fahrenheitFromButton);

            JRadioButton kelvinFromButton = new JRadioButton("шкала Кельвина");
            scalesFromPanel.add(kelvinFromButton);

            JRadioButton celsiusToButton = new JRadioButton("шкала Цельсия");
            celsiusToButton.setHorizontalTextPosition(SwingConstants.LEFT);
            scalesToPanel.add(celsiusToButton);
            celsiusToButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JRadioButton fahrenheitToButton = new JRadioButton("шкала Фаренгейта", true);
            fahrenheitToButton.setHorizontalTextPosition(SwingConstants.LEFT);
            scalesToPanel.add(fahrenheitToButton);
            fahrenheitToButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JRadioButton kelvinToButton = new JRadioButton("шкала Кельвина");
            kelvinToButton.setHorizontalTextPosition(SwingConstants.LEFT);
            scalesToPanel.add(kelvinToButton);
            kelvinToButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

            ButtonGroup fromButtonGroup = new ButtonGroup();
            fromButtonGroup.add(celsiusFromButton);
            fromButtonGroup.add(fahrenheitFromButton);
            fromButtonGroup.add(kelvinFromButton);

            ButtonGroup toButtonGroup = new ButtonGroup();
            toButtonGroup.add(celsiusToButton);
            toButtonGroup.add(fahrenheitToButton);
            toButtonGroup.add(kelvinToButton);

            JTextField inputTextField = new JTextField("", 6);
            inputTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
            inputTextField.setHorizontalAlignment(JTextField.CENTER);
            inputTextField.setToolTipText("Введите сюда температуру (число градусов)");
            inputPanel.add(inputTextField);

            JTextField outputTextField = new JTextField("Output", 6);
            outputTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
            outputTextField.setHorizontalAlignment(JTextField.CENTER);
            outputTextField.setToolTipText("Окно для температуры после перевода в необходимую шкалу");
            outputTextField.setEditable(false);
            outputPanel.add(outputTextField);

            JButton buttonToConvert = new JButton("Перевести!");
            convertButtonPanel.add(buttonToConvert);

            buttonToConvert.addActionListener((ActionEvent action) -> {
                TemperatureConverter.Scale fromScale;
                TemperatureConverter.Scale toScale;

                if (celsiusFromButton.isSelected()) {
                    fromScale = TemperatureConverter.Scale.CELSIUS;
                } else if (fahrenheitFromButton.isSelected()) {
                    fromScale = TemperatureConverter.Scale.FAHRENHEIT;
                } else {
                    fromScale = TemperatureConverter.Scale.KELVIN;
                }

                if (celsiusToButton.isSelected()) {
                    toScale = TemperatureConverter.Scale.CELSIUS;
                } else if (fahrenheitToButton.isSelected()) {
                    toScale = TemperatureConverter.Scale.FAHRENHEIT;
                } else {
                    toScale = TemperatureConverter.Scale.KELVIN;
                }

                String inputText = inputTextField.getText();

                try {
                    double inputTemperature = Double.parseDouble(inputText);

                    double outputTemperature = TemperatureConverter.convert(inputTemperature, fromScale, toScale);

                    String outputText = String.format("%.2f", outputTemperature);

                    outputTextField.setText(outputText);
                } catch (NumberFormatException e) {
                    inputTextField.setText("");

                    JOptionPane.showMessageDialog(frame,
                            "Нельзя вводить НЕ числа!",
                            "Ошибка ввода!",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}