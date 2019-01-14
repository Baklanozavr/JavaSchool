package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.GameProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

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

            button.setPreferredSize(new Dimension(20, 20));
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setFocusPainted(false);
        }

        fieldButtonsArray.forEach(button -> button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedIndex = fieldButtonsArray.indexOf(button);

                if (SwingUtilities.isRightMouseButton(e)) {
                    Boolean isMarked = game.markTile(clickedIndex);

                    if (isMarked != null) {
                        if (isMarked) {
                            button.setText("F");
                        } else {
                            button.setText("");
                        }
                    }
                } else {
                    HashMap<Integer, GameProcess.TileGameState> tilesForChange = game.openTiles(clickedIndex);

                    for (Integer index : tilesForChange.keySet()) {
                        GameProcess.TileGameState tileState = tilesForChange.get(index);

                        switch (tileState) {
                            case BOOMED_MINE: {
                                fieldButtonsArray.get(index).setText("X");
                                fieldButtonsArray.get(index).setBackground(Color.RED);
                                break;
                            }
                            case MINE: {
                                fieldButtonsArray.get(index).setText("X");
                                break;
                            }
                            case ERROR_MINE: {
                                //fieldButtonsArray.get(index).setText("F");
                                fieldButtonsArray.get(index).setBackground(Color.RED);
                                break;
                            }
                            case EMPTY: {
                                fieldButtonsArray.get(index).setBackground(Color.WHITE);
                                break;
                            }
                            case ONE: {
                                fieldButtonsArray.get(index).setText("1");
                                break;
                            }
                            case TWO: {
                                fieldButtonsArray.get(index).setText("2");
                                break;
                            }
                            case THREE: {
                                fieldButtonsArray.get(index).setText("3");
                                break;
                            }
                            case FOUR: {
                                fieldButtonsArray.get(index).setText("4");
                                break;
                            }
                            case FIVE: {
                                fieldButtonsArray.get(index).setText("5");
                                break;
                            }
                            case SIX: {
                                fieldButtonsArray.get(index).setText("6");
                                break;
                            }
                            case SEVEN: {
                                fieldButtonsArray.get(index).setText("7");
                                break;
                            }
                            case EIGHT: {
                                fieldButtonsArray.get(index).setText("8");
                                break;
                            }
                        }

                        fieldButtonsArray.get(index).setEnabled(false);
                    }
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
