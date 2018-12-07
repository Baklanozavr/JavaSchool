package ru.academits.baklanov.tasks;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MineSweeperGUI {
    public MineSweeperGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MineSweeper by Baklanozavr");

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GameProcess game = new GameProcess(GameProcess.Difficulty.EASY);
            int start = game.getOpenedTiles().size() - 1;
            game.getMineField().setMines(start);

            GridLayout fieldGridLayout = new GridLayout(game.getMineField().getHeight(), game.getMineField().getWidth() - 1);

            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(fieldGridLayout);
            frame.add(fieldPanel);

            ArrayList<JButton> fieldButtonsArray = new ArrayList<>();

            for (int i = 0; i < game.getOpenedTiles().size(); ++i) {
                fieldButtonsArray.add(new JButton());
            }

            Dimension tileButtonSize = new Dimension(20, 20);
            Insets tileButtonInsets = new Insets(0, 0, 0, 0);

            fieldButtonsArray.forEach(button -> button.setPreferredSize(tileButtonSize));
            fieldButtonsArray.forEach(button -> button.setMargin(tileButtonInsets));
            fieldButtonsArray.forEach(fieldPanel::add);

            frame.pack();

            fieldButtonsArray.forEach(button ->
                    button.addActionListener(action -> {
                        int clickedIndex = fieldButtonsArray.indexOf(button);

                        BitSet indexesForOpening = game.openTilesFrom(clickedIndex);

                        for (int i = indexesForOpening.nextSetBit(0); i >= 0; i = indexesForOpening.nextSetBit(i + 1)) {
                            Tile selectedTile = game.getMineField().getTile(i);

                            if (selectedTile.isMine()) {
                                fieldButtonsArray.get(i).setText("*");
                                fieldButtonsArray.get(i).setEnabled(false);
                            } else {
                                int numberOfAdjacentMines = selectedTile.getNumberOfAdjacentMines();
                                fieldButtonsArray.get(i).setText(String.valueOf(numberOfAdjacentMines));
                                fieldButtonsArray.get(i).setEnabled(false);
                            }
                        }
                    }));
        });
    }
}