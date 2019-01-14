package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class MineFieldGUI extends JPanel {
    private ArrayList<JButton> fieldButtonsArray;

    MineFieldGUI(GameProcess game) {
        GridLayout fieldGridLayout =
                new GridLayout(game.getDifficulty().getFieldHeight(), game.getDifficulty().getFieldWidth() - 1);
        this.setLayout(fieldGridLayout);

        fieldButtonsArray = new ArrayList<>();

        for (int i = 0; i < game.getDifficulty().getFieldSize(); ++i) {
            JButton button = new JButton();
            fieldButtonsArray.add(button);

            this.add(button);

            TileGUI tileUI = new TileGUI(button);
            game.getMineField().getTile(i / game.getDifficulty().getFieldHeight(), i % game.getDifficulty().getFieldHeight()).registerUI(tileUI);
        }

        fieldButtonsArray.forEach(button -> button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedIndex = fieldButtonsArray.indexOf(button);

                if (SwingUtilities.isRightMouseButton(e)) {
                    game.markTile(clickedIndex);
                } else {
                    game.openTile(clickedIndex);
                }
            }
        }));
    }

    void clearButtons() {
        fieldButtonsArray.forEach(button -> {
            button.setText(null);
            button.setBackground(null);
            button.setEnabled(true);
        });
    }
}
