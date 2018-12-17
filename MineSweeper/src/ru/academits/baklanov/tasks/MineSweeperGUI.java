package ru.academits.baklanov.tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static ru.academits.baklanov.tasks.GameProcess.*;

public class MineSweeperGUI {
    public MineSweeperGUI() {
        SwingUtilities.invokeLater(() -> {
            Difficulty defaultDifficulty = Difficulty.MEDIUM;

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

            //TODO убрать это
            int start = game.getOpenedTiles().size() - 1;
            game.getMineField().setMines(start);

            fieldButtonsArray.forEach(button -> button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int clickedIndex = fieldButtonsArray.indexOf(button);

                    HashMap<Integer, String> tilesForChange = new HashMap<>();

                    //HashMap<Integer, TileState> tilesForChanges = new HashMap<>();

                    if (SwingUtilities.isRightMouseButton(e)) {
                        tilesForChange = game.markTile(clickedIndex);
                    } else {
                        tilesForChange = game.openTiles(clickedIndex);
                    }

                    for (Integer index : tilesForChange.keySet()) {
                        String textForButton = tilesForChange.get(index);

                        fieldButtonsArray.get(index).setText(textForButton);

                        if (textForButton.equals("")) {
                            fieldButtonsArray.get(index).setBackground(Color.WHITE);
                        }
                    }



                    //TODO добавить код проверки победы/поражения
                }
            }));
        });
    }
}