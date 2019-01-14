package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.GameTile;

import javax.swing.*;
import java.awt.*;

public class GameTileUI {
    private JButton tileButton;

    public GameTileUI(JButton tileButton) {
        this.tileButton = tileButton;
    }

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
                tileButton.setBackground(Color.WHITE);
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
                break;
            }
        }
    }
}