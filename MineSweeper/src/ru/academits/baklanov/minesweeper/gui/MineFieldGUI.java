package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class MineFieldGUI extends JPanel {
    private GameProcess mineSweeperGame;

    MineFieldGUI(GameProcess game) {
        mineSweeperGame = game;

        int width = game.getDifficulty().getFieldWidth();
        int height = game.getDifficulty().getFieldHeight();

        GridLayout fieldGridLayout = new GridLayout(height, width - 1);
        this.setLayout(fieldGridLayout);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                TileButton button = new TileButton(i, j);
                this.add(button);
                game.registerTileUI(button, i, j);
                button.addMouseListener(new TileMouseListener());
            }
        }
    }

    private class TileMouseListener implements MouseListener {
        boolean isOnTile = false;
        boolean isTilePressed = false;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            isOnTile = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isOnTile = false;
            isTilePressed = false;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            isTilePressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isTilePressed && isOnTile) {
                TileButton button = (TileButton) e.getSource();

                if (!GameProcess.isFail) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        mineSweeperGame.markTile(button.getVerticalIndex(), button.getHorizontalIndex());
                    } else {
                        mineSweeperGame.openTile(button.getVerticalIndex(), button.getHorizontalIndex());
                    }
                }
            }
        }
    }
}