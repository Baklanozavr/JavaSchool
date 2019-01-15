package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameTile;
import ru.academits.baklanov.minesweeper.TileUI;

import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton implements TileUI {
    private int verticalIndex;
    private int horizontalIndex;

    TileButton(int verticalIndex, int horizontalIndex) {
        this.verticalIndex = verticalIndex;
        this.horizontalIndex = horizontalIndex;

        setPreferredSize(new Dimension(20, 20));
        setMargin(new Insets(0, 0, 0, 0));
        setFocusPainted(false);
    }

    @Override
    public void update(GameTile.State state) {
        switch (state) {
            case CLOSED: {
                setText(null);
                setBackground(null);
                setEnabled(true);
                return;
            }
            case FLAG: {
                setText("F");
                setEnabled(false);
                return;
            }
            case MINE: {
                setText("X");
                return;
            }
            case ERROR_MINE: {
                //tileButton.setText("F");
                setBackground(Color.RED);
                return;
            }
            case BOOMED_MINE: {
                setText("X");
                setBackground(Color.RED);
                return;
            }
            case EMPTY: {
                break;
            }
            case ONE: {
                setText("1");
                break;
            }
            case TWO: {
                setText("2");
                break;
            }
            case THREE: {
                setText("3");
                break;
            }
            case FOUR: {
                setText("4");
                break;
            }
            case FIVE: {
                setText("5");
                break;
            }
            case SIX: {
                setText("6");
                break;
            }
            case SEVEN: {
                setText("7");
                break;
            }
            case EIGHT: {
                setText("8");
            }
        }
        setBackground(Color.WHITE);
    }

    int getVerticalIndex() {
        return verticalIndex;
    }

    int getHorizontalIndex() {
        return horizontalIndex;
    }
}