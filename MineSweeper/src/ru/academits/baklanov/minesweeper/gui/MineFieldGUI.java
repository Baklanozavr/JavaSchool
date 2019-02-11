package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class MineFieldGUI extends JPanel {
    private ArrayList<TileButton> tileButtonsArray;
    private ArrayList<TileMouseListener> tileMouseListenersArray;
    private GameProcess mineSweeperGame;

    MineFieldGUI(GameProcess game) {
        int height = game.getGameDifficulty().getFieldHeight();
        int width = game.getGameDifficulty().getFieldWidth();

        tileButtonsArray = new ArrayList<>();
        tileMouseListenersArray = new ArrayList<>();
        mineSweeperGame = game;

        this.setLayout(new GridLayout(height, width - 1));

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                TileButton button = new TileButton(i, j);
                tileButtonsArray.add(button);
                this.add(button);
                game.registerTileUI(button, i, j);

                TileMouseListener tileMouseListener = new TileMouseListener();
                tileMouseListenersArray.add(tileMouseListener);
            }
        }

        activate();
    }

    private class TileMouseListener implements MouseListener {
        boolean isOnTile = false;
        boolean isTilePressed = false;

        @Override
        public void mouseEntered(MouseEvent e) {
            isOnTile = true;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            isTilePressed = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isOnTile = false;
            isTilePressed = false;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isTilePressed && isOnTile) {
                TileButton button = (TileButton) e.getSource();

                if (SwingUtilities.isRightMouseButton(e)) {
                    mineSweeperGame.markTile(button.getVerticalIndex(), button.getHorizontalIndex());
                } else {
                    mineSweeperGame.openTile(button.getVerticalIndex(), button.getHorizontalIndex());
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    void block() {
        for (int i = 0; i < tileButtonsArray.size(); ++i) {
            tileButtonsArray.get(i).removeMouseListener(tileMouseListenersArray.get(i));
        }
    }

    void activate() {
        for (int i = 0; i < tileButtonsArray.size(); ++i) {
            tileButtonsArray.get(i).addMouseListener(tileMouseListenersArray.get(i));
        }
    }
}