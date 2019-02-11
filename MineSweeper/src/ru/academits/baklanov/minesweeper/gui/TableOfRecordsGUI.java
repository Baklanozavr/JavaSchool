package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameDifficulty;
import ru.academits.baklanov.minesweeper.model.records.GameRecords;
import ru.academits.baklanov.minesweeper.model.records.WinResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class TableOfRecordsGUI extends JDialog {
    private ArrayList<JLabel> listOfContentForTableOfRecords;

    TableOfRecordsGUI(JFrame owner) {
        super(owner, true);

        int numberOfRowsInTableOfRecords = 1 + GameRecords.TABLE.getMaxNumberOfWinners();
        int numberOfColumnsInTableOfRecords = GameRecords.TABLE.getWinParameters().length;

        listOfContentForTableOfRecords = new ArrayList<>();
        for (int i = 0; i < numberOfRowsInTableOfRecords * numberOfColumnsInTableOfRecords; ++i) {
            listOfContentForTableOfRecords.add(new JLabel());
        }
        listOfContentForTableOfRecords.trimToSize();

        JPanel panelForTableOfRecords = new JPanel(new GridLayout(
                numberOfRowsInTableOfRecords, numberOfColumnsInTableOfRecords - 1));
        add(panelForTableOfRecords, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(action -> setVisible(false));
        add(new JPanel().add(closeButton), BorderLayout.SOUTH);

        setVisible(false);
    }

    void showTable(GameDifficulty gameDifficulty) {
        setTitle("Winners of " + gameDifficulty.getName() + " difficulty");

        fillListOfContentForTableOfRecords(gameDifficulty);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fillListOfContentForTableOfRecords(GameDifficulty gameDifficulty) {
        int indexCounter = 0;

        for (String parameter : GameRecords.TABLE.getWinParameters()) {
            JLabel label = listOfContentForTableOfRecords.get(indexCounter);
            label.setText(parameter);
            ++indexCounter;
        }

        for (WinResult record : GameRecords.TABLE.getRecords(gameDifficulty)) {
            int time = record.getTime();
            int numberOfClicks = record.getNumberOfClicks();
            double clicksPerSecond = (double) numberOfClicks / time;

            JLabel label = listOfContentForTableOfRecords.get(indexCounter);
            label.setText(String.valueOf(time));
            ++indexCounter;

            label = listOfContentForTableOfRecords.get(indexCounter);
            label.setText(String.valueOf(numberOfClicks));
            ++indexCounter;

            label = listOfContentForTableOfRecords.get(indexCounter);
            label.setText(String.format("%.1f", clicksPerSecond));
            ++indexCounter;

            label = listOfContentForTableOfRecords.get(indexCounter);
            label.setText(record.getNameOfPlayer());
            ++indexCounter;
        }
    }
}