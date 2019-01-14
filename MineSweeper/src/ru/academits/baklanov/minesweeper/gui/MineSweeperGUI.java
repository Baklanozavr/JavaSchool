package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.model.GameProcess.*;
import ru.academits.baklanov.minesweeper.model.GameProcess;

import javax.swing.*;
import java.awt.*;

import static ru.academits.baklanov.minesweeper.model.GameProcess.getVariantsOfDifficulty;

public class MineSweeperGUI implements Runnable {
    private JLabel minesBalanceLabel;
    private JLabel timeLabel;
    private GameProcess game;
    private JMenuBar menuBar;
    private MineFieldGUI mineFieldGUI;
    private boolean isNewGame;

    public MineSweeperGUI() {
        game = new GameProcess();
        menuBar = new JMenuBar();
        mineFieldGUI = new MineFieldGUI(game);
        minesBalanceLabel = new JLabel(String.valueOf(game.getDifficulty().getTotalNumberOfMines()));
        timeLabel = new JLabel("0");
    }

    @Override
    public void run() {
        game.registerUI(this);

        SwingUtilities.invokeLater(() -> {
            activateMenuBar();

            JFrame frame = new JFrame("MineSweeper by Baklanozavr");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setJMenuBar(menuBar);

            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
            frame.add(gamePanel, BorderLayout.CENTER);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
            gamePanel.add(infoPanel);

            infoPanel.add(minesBalanceLabel);
            infoPanel.add(Box.createHorizontalGlue());
            JLabel infoLabel = new JLabel();
            infoPanel.add(infoLabel);
            infoPanel.add(Box.createHorizontalGlue());
            infoPanel.add(timeLabel);

            gamePanel.add(mineFieldGUI);

            frame.pack();
            frame.setLocationRelativeTo(null);

            //тут что-то планировалось
            if (isNewGame) {
                gamePanel.removeAll();
                gamePanel.add(mineFieldGUI);
                frame.pack();
            }

            if (game.isFail()) {
                //fieldButtonsArray.forEach(JButton::removeAll);
                infoLabel.setText("Вы проиграли!");
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
            mineFieldGUI.clearButtons();
            game.restart();
        });
        gameMenu.add(newGame);
        gameMenu.addSeparator();

        JMenu difficultyMenu = new JMenu("Сложность");
        for (Difficulty difficulty : getVariantsOfDifficulty()) {
            JMenuItem difficultySetting = new JMenuItem(difficulty.getName());
            difficultySetting.addActionListener(event -> {
                game.restart(difficulty);
                mineFieldGUI = new MineFieldGUI(game);
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
}