package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameTile;
import ru.academits.baklanov.minesweeper.TileUI;

import javax.swing.*;
import java.awt.*;

public class TileGUI implements TileUI {
    private JButton tileButton;

    TileGUI(JButton tileButton) {
        this.tileButton = tileButton;

        tileButton.setPreferredSize(new Dimension(20, 20));
        tileButton.setMargin(new Insets(0, 0, 0, 0));
        tileButton.setFocusPainted(false);
    }

    @Override
    public void update(GameTile.State state) {
        switch (state) {
            case CLOSED: {
                tileButton.setText(null);
                tileButton.setBackground(null);
                tileButton.setEnabled(true);
                return;
            }
            case FLAG: {
                tileButton.setText("F");
                tileButton.setEnabled(false);
                return;
            }
            case MINE: {
                tileButton.setText("X");
                return;
            }
            case ERROR_MINE: {
                //fieldButtonsArray.get(index).setText("F");
                tileButton.setBackground(Color.RED);
                return;
            }
            case BOOMED_MINE: {
                tileButton.setText("X");
                tileButton.setBackground(Color.RED);
                return;
            }
            case EMPTY: {
                break;
            }
            case ONE: {
                tileButton.setText("1");
                break;
            }
            case TWO: {
                tileButton.setText("2");
                break;
            }
            case THREE: {
                tileButton.setText("3");
                break;
            }
            case FOUR: {
                tileButton.setText("4");
                break;
            }
            case FIVE: {
                tileButton.setText("5");
                break;
            }
            case SIX: {
                tileButton.setText("6");
                break;
            }
            case SEVEN: {
                tileButton.setText("7");
                break;
            }
            case EIGHT: {
                tileButton.setText("8");
            }
        }
        tileButton.setBackground(Color.WHITE);
    }
}