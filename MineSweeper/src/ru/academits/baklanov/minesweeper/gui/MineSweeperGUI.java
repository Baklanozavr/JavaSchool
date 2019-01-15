package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.MineSweeperUI;
import ru.academits.baklanov.minesweeper.model.GameProcess.Difficulty;
import ru.academits.baklanov.minesweeper.model.GameProcess;

import javax.swing.*;
import java.awt.*;

public class MineSweeperGUI implements Runnable, MineSweeperUI {
    private GameProcess game;

    private JFrame gameFrame;
    private JPanel gamePanel;
    private JMenuBar menuBar;

    private JLabel minesBalanceLabel;
    private JLabel timeLabel;
    private JLabel infoLabel;

    private MineFieldGUI mineFieldGUI;

    public MineSweeperGUI() {
        game = new GameProcess();

        gameFrame = new JFrame("MineSweeper by Baklanozavr");
        gamePanel = new JPanel();
        menuBar = new JMenuBar();

        minesBalanceLabel = new JLabel(String.valueOf(game.getDifficulty().getTotalNumberOfMines()));
        timeLabel = new JLabel("0");
        infoLabel = new JLabel("Вперёд!");

        mineFieldGUI = new MineFieldGUI(game);
    }

    @Override
    public void run() {
        game.registerUI(this);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
        infoPanel.add(minesBalanceLabel);
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(timeLabel);

        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
        gamePanel.add(infoPanel);
        gamePanel.add(mineFieldGUI);

        menuBar.add(createMenuBar());

        gameFrame.setJMenuBar(menuBar);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    private JMenu createMenuBar() {
        JMenu gameMenu = new JMenu("Игра");

        JMenuItem newGame = new JMenuItem("Новая");
        gameMenu.add(newGame);
        gameMenu.addSeparator();

        JMenu difficultyMenu = new JMenu("Сложность");
        gameMenu.add(difficultyMenu);
        gameMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Выйти");
        gameMenu.add(exit);

        for (Difficulty difficulty : Difficulty.values()) {
            JMenuItem difficultySetting = new JMenuItem(difficulty.getName());

            difficultySetting.addActionListener(event -> restartGame(difficulty));

            difficultyMenu.add(difficultySetting);
        }
        newGame.addActionListener(event -> restartGame(game.getDifficulty()));
        exit.addActionListener(event -> System.exit(0));

        return gameMenu;
    }

    private void restartGame(Difficulty difficulty) {
        Difficulty oldDifficulty = game.getDifficulty();

        game.restart(difficulty);

        minesBalanceLabel.setText(String.valueOf(difficulty.getTotalNumberOfMines()));
        infoLabel.setText("Новая игра");
        timeLabel.setText("0");

        if (oldDifficulty != difficulty) {
            gamePanel.remove(mineFieldGUI);
            mineFieldGUI = new MineFieldGUI(game);
            gamePanel.add(mineFieldGUI);

            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
        }
    }

    @Override
    public void updateMinesBalance(int numberOfMines) {
        minesBalanceLabel.setText(String.valueOf(numberOfMines));
    }

    @Override
    public void updateTime(int time) {
        timeLabel.setText(String.valueOf(time));
    }

    @Override
    public void updateGameState(boolean isVictory) {
        if (isVictory) {
            infoLabel.setText("Победа!");
        } else {
            infoLabel.setText("Вы проиграли!");
        }
    }
}