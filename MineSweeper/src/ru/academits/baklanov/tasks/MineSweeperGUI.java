package ru.academits.baklanov.tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MineSweeperGUI {
    public MineSweeperGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MineSweeper by Baklanozavr");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GameProcess game = new GameProcess(GameProcess.Difficulty.MEDIUM);

            JMenuBar menuBar = new JMenuBar();
            JMenu gameMenu = new JMenu("Игра");
            gameMenu.add(new JMenuItem("Новая"));
            gameMenu.addSeparator();

            JMenu difficultyMenu = new JMenu("Сложность");
            for(GameProcess.Difficulty difficulty : game.getVariantsOfDifficulty()) {
                JMenuItem difficultySetting = new JMenuItem(difficulty.getName());
                //difficultySetting.addActionListener(event -> game.setNewDifficulty(difficulty));
                difficultyMenu.add(difficultySetting);
            }
            gameMenu.add(difficultyMenu);
            gameMenu.addSeparator();

            JMenuItem exit = new JMenuItem("Выйти");
            exit.addActionListener(event -> System.exit(0));
            gameMenu.add(exit);

            menuBar.add(gameMenu);
            frame.setJMenuBar(menuBar);

            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
            frame.add(gamePanel, BorderLayout.CENTER);

            int start = game.getOpenedTiles().size() - 1;
            game.getMineField().setMines(start);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
            gamePanel.add(infoPanel);

            JLabel minesBalanceLabel = new JLabel("Mines");
            infoPanel.add(minesBalanceLabel);
            infoPanel.add(Box.createHorizontalGlue());
            JButton resetButton = new JButton("Reset");
            infoPanel.add(resetButton);
            infoPanel.add(Box.createHorizontalGlue());
            JLabel timeLabel = new JLabel("Time");
            infoPanel.add(timeLabel);

            GridLayout fieldGridLayout = new GridLayout(game.getMineField().getHeight(), game.getMineField().getWidth() - 1);

            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(fieldGridLayout);
            gamePanel.add(fieldPanel);

            ArrayList<JButton> fieldButtonsArray = new ArrayList<>();

            for (int i = 0; i < game.getOpenedTiles().size(); ++i) {
                JButton button = new JButton();

                fieldButtonsArray.add(button);
                fieldPanel.add(button);

                button.setPreferredSize(new Dimension(20, 20));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setFocusPainted(false);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int clickedIndex = fieldButtonsArray.indexOf(button);

                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (!game.isOpen(clickedIndex)) {
                                if (game.setFlag(clickedIndex)) {
                                    button.setText("F");
                                } else {
                                    button.setText("");
                                }
                            }
                        } else {
                            if (!game.isFlag(clickedIndex)) {

                                for (Integer i : game.openTilesFrom(clickedIndex)){
                                    Tile selectedTile = game.getMineField().getTile(i);

                                    if (selectedTile.isMine()) {
                                        fieldButtonsArray.get(i).setText("X");
                                        fieldButtonsArray.get(i).setBackground(Color.RED);
                                        button.setEnabled(false);
                                    } else {
                                        int numberOfAdjacentMines = selectedTile.getNumberOfAdjacentMines();

                                        if (numberOfAdjacentMines != 0) {
                                            fieldButtonsArray.get(i).setText(String.valueOf(numberOfAdjacentMines));
                                        } else {
                                            fieldButtonsArray.get(i).setEnabled(false);
                                        }
                                        fieldButtonsArray.get(i).setBackground(Color.WHITE);
                                    }
                                }
                            }
                        }
                    }
                });
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
        });
    }
}