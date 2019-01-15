package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameProcess.*;
import ru.academits.baklanov.minesweeper.model.GameProcess;

import javax.swing.*;
import java.awt.*;

import static ru.academits.baklanov.minesweeper.model.GameProcess.getVariantsOfDifficulty;

public class MineSweeperGUI implements Runnable {
    private JLabel minesBalanceLabel;
    private JLabel timeLabel;
    private JLabel infoLabel;

    private JFrame gameFrame;
    private JPanel gamePanel;

    private GameProcess game;
    private JMenuBar menuBar;
    private MineFieldGUI mineFieldGUI;
    private boolean isNewGame;

    public MineSweeperGUI() {
        game = new GameProcess();
        menuBar = new JMenuBar();
        mineFieldGUI = new MineFieldGUI(game);
        gamePanel = new JPanel();

        gameFrame = new JFrame("MineSweeper by Baklanozavr");

        minesBalanceLabel = new JLabel(String.valueOf(game.getDifficulty().getTotalNumberOfMines()));
        timeLabel = new JLabel("0");
        infoLabel = new JLabel("Вперёд!");
    }

    @Override
    public void run() {
        game.registerUI(this);

        SwingUtilities.invokeLater(() -> {
            activateMenuBar();

            gameFrame.setVisible(true);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gameFrame.setJMenuBar(menuBar);

            gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
            gameFrame.add(gamePanel, BorderLayout.CENTER);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
            gamePanel.add(infoPanel);

            infoPanel.add(minesBalanceLabel);
            infoPanel.add(Box.createHorizontalGlue());

            infoPanel.add(infoLabel);
            infoPanel.add(Box.createHorizontalGlue());
            infoPanel.add(timeLabel);

            gamePanel.add(mineFieldGUI);

            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);

            //тут что-то планировалось
            if (isNewGame) {
                gamePanel.removeAll();
                gamePanel.add(mineFieldGUI);
                gameFrame.pack();
            }

            if (game.isVictory()) {
                //fieldButtonsArray.forEach(JButton::removeAll);
                infoLabel.setText("Победа!");
            }
        });
    }

    private void activateMenuBar() {
        JMenu gameMenu = new JMenu("Игра");

        JMenuItem newGame = new JMenuItem("Новая");
        newGame.addActionListener(event -> {
            infoLabel.setText("Новая игра");
            game.restart();
        });
        gameMenu.add(newGame);
        gameMenu.addSeparator();

        JMenu difficultyMenu = new JMenu("Сложность");
        for (Difficulty difficulty : getVariantsOfDifficulty()) {
            JMenuItem difficultySetting = new JMenuItem(difficulty.getName());
            difficultySetting.addActionListener(event -> {
                infoLabel.setText("Новая игра");
                game.restart(difficulty);

                gamePanel.remove(mineFieldGUI);
                mineFieldGUI = new MineFieldGUI(game);
                gamePanel.add(mineFieldGUI);

                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);

            });
            difficultyMenu.add(difficultySetting);
        }
        gameMenu.add(difficultyMenu);
        gameMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Выйти");
        exit.addActionListener(event -> System.exit(0));
        gameMenu.add(exit);

        menuBar.add(gameMenu);
    }

    public void updateMinesBalance(int numberOfMines) {
        minesBalanceLabel.setText(String.valueOf(numberOfMines));
    }

    public void updateTime(int time) {
        timeLabel.setText(String.valueOf(time));
    }

    public void updateGameState(boolean isVictory) {
        if (isVictory) {
            infoLabel.setText("Победа!");
        } else {
            infoLabel.setText("Вы проиграли!");
        }
    }
}