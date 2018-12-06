package ru.academits.baklanov.tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TemperatureConverterGUI {
    public TemperatureConverterGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("My first GUI application with Temperature Converter");

            frame.setSize(500, 240);
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
            JLabel blankLabel = new JLabel(" ");
            JLabel errorLabel = new JLabel(" ");
            errorLabel.setForeground(Color.RED);
            infoPanel.add(infoLabel1);
            infoPanel.add(infoLabel2);
            infoPanel.add(blankLabel);
            infoPanel.add(errorLabel);
            infoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            blankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            ArrayList<JRadioButton> fromButtonsArray = new ArrayList<>();
            ArrayList<JRadioButton> toButtonsArray = new ArrayList<>();

            for (TemperatureConverter.Scale scale : TemperatureConverter.Scale.values()) {
                fromButtonsArray.add(new JRadioButton(scale.getNameString()));
                toButtonsArray.add(new JRadioButton(scale.getNameString()));
            }

            if (fromButtonsArray.size() > 0) {
                fromButtonsArray.get(0).setSelected(true);
            }
            if (toButtonsArray.size() > 0) {
                toButtonsArray.get(0).setSelected(true);
            }

            toButtonsArray.forEach(button -> button.setHorizontalTextPosition(SwingConstants.LEFT));
            toButtonsArray.forEach(button -> button.setAlignmentX(Component.RIGHT_ALIGNMENT));

            fromButtonsArray.forEach(scalesFromPanel::add);
            toButtonsArray.forEach(scalesToPanel::add);

            ButtonGroup fromButtonGroup = new ButtonGroup();
            fromButtonsArray.forEach(fromButtonGroup::add);

            ButtonGroup toButtonGroup = new ButtonGroup();
            toButtonsArray.forEach(toButtonGroup::add);

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
                errorLabel.setText(" ");

                TemperatureConverter.Scale fromScale = null;
                TemperatureConverter.Scale toScale = null;

                for (int i = 0; i < TemperatureConverter.Scale.values().length; ++i) {
                    if (fromButtonsArray.get(i).isSelected()) {
                        fromScale = TemperatureConverter.Scale.values()[i];
                    }

                    if (toButtonsArray.get(i).isSelected()) {
                        toScale = TemperatureConverter.Scale.values()[i];
                    }
                }

                String inputText = inputTextField.getText();

                try {
                    double inputTemperature = Double.parseDouble(inputText);

                    double outputTemperature = TemperatureConverter.convert(inputTemperature, fromScale, toScale);

                    String outputText = String.format("%.2f", outputTemperature);

                    outputTextField.setText(outputText);
                } catch (NumberFormatException e) {
                    inputTextField.setText("");
                    outputTextField.setText("Output");
                    errorLabel.setText("Нельзя вводить НЕ числа!");
                } catch (IllegalArgumentException e) {
                    outputTextField.setText("Output");
                    errorLabel.setText(e.getMessage());
                }
            });
        });
    }
}