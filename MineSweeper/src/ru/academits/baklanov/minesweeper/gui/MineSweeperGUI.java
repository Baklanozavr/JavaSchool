package ru.academits.baklanov.minesweeper.gui;


import ru.academits.baklanov.minesweeper.GameProcess.*;
import ru.academits.baklanov.minesweeper.GameProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static ru.academits.baklanov.minesweeper.GameProcess.getVariantsOfDifficulty;

public class MineSweeperGUI {
    public MineSweeperGUI() {
        SwingUtilities.invokeLater(() -> {
            Difficulty defaultDifficulty = Difficulty.HARD;

            JFrame frame = new JFrame("MineSweeper by Baklanozavr");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            frame.setJMenuBar(menuBar);

            JMenu gameMenu = new JMenu("Игра");
            menuBar.add(gameMenu);

            gameMenu.add(new JMenuItem("Новая"));
            gameMenu.addSeparator();

            JMenu difficultyMenu = new JMenu("Сложность");
            for (Difficulty difficulty : getVariantsOfDifficulty()) {
                JMenuItem difficultySetting = new JMenuItem(difficulty.getName());
                //difficultySetting.addActionListener(event -> game.setNewDifficulty(difficulty));
                difficultyMenu.add(difficultySetting);
            }
            gameMenu.add(difficultyMenu);
            gameMenu.addSeparator();

            JMenuItem exit = new JMenuItem("Выйти");
            exit.addActionListener(event -> System.exit(0));
            gameMenu.add(exit);

            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
            frame.add(gamePanel, BorderLayout.CENTER);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
            gamePanel.add(infoPanel);

            JLabel minesBalanceLabel = new JLabel("Mines");
            infoPanel.add(minesBalanceLabel);
            infoPanel.add(Box.createHorizontalGlue());
            JLabel infoLabel = new JLabel();
            infoPanel.add(infoLabel);
            infoPanel.add(Box.createHorizontalGlue());
            JLabel timeLabel = new JLabel("Time");
            infoPanel.add(timeLabel);

            GridLayout fieldGridLayout = new GridLayout(defaultDifficulty.getFieldHeight(), defaultDifficulty.getFieldWidth() - 1);
            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(fieldGridLayout);
            gamePanel.add(fieldPanel);

            ArrayList<JButton> fieldButtonsArray = new ArrayList<>();

            for (int i = 0; i < defaultDifficulty.getFieldSize(); ++i) {
                JButton button = new JButton();
                fieldButtonsArray.add(button);

                fieldPanel.add(button);

                button.setPreferredSize(new Dimension(20, 20));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setFocusPainted(false);
            }

            frame.pack();
            frame.setLocationRelativeTo(null);

            GameProcess game = new GameProcess(defaultDifficulty);

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
                        HashMap<Integer, TileGameState> tilesForChange = game.openTiles(clickedIndex);

                        for (Integer index : tilesForChange.keySet()) {
                            TileGameState tileState = tilesForChange.get(index);

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

                        if (game.isFail()) {
                            //fieldButtonsArray.forEach(JButton::removeAll);
                            infoLabel.setText("Вы проиграли!");
                        }

                        if (game.isVictory()) {
                            //fieldButtonsArray.forEach(JButton::removeAll);
                            infoLabel.setText("Победа!");
                        }
                    }
                }
            }));
        });
    }
}