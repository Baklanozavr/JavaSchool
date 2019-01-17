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
    private JLabel infoLabel;

    private JTextField minesBalanceTextField;
    private JTextField timeTextField;

    private MineFieldGUI mineFieldGUI;

    public MineSweeperGUI() {
        game = new GameProcess();

        gameFrame = new JFrame("MineSweeper by Baklanozavr");
        gamePanel = new JPanel();
        menuBar = new JMenuBar();
        infoLabel = new JLabel("New");

        minesBalanceTextField = new JTextField(String.valueOf(game.getDifficulty().getTotalNumberOfMines()), 3);
        minesBalanceTextField.setToolTipText("Количество непомеченных мин");
        minesBalanceTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
        minesBalanceTextField.setHorizontalAlignment(JTextField.LEFT);
        minesBalanceTextField.setEditable(false);

        timeTextField = new JTextField("0", 3);
        timeTextField.setToolTipText("Прошло секунд с начала игры");
        timeTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
        timeTextField.setHorizontalAlignment(JTextField.RIGHT);
        timeTextField.setEditable(false);

        mineFieldGUI = new MineFieldGUI(game);
    }

    @Override
    public void run() {
        game.registerUI(this);

        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(0, 40));
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(minesBalanceTextField, BorderLayout.LINE_START);
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(timeTextField, BorderLayout.LINE_END);

        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
        gamePanel.add(infoPanel);
        gamePanel.add(mineFieldGUI);

        menuBar.add(createMenuBar());

        gameFrame.setJMenuBar(menuBar);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
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

        minesBalanceTextField.setText(String.valueOf(difficulty.getTotalNumberOfMines()));
        infoLabel.setText("New");
        timeTextField.setText("0");

        mineFieldGUI.block();

        if (oldDifficulty != difficulty) {
            gamePanel.remove(mineFieldGUI);
            mineFieldGUI = new MineFieldGUI(game);
            gamePanel.add(mineFieldGUI);

            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
        } else {
            mineFieldGUI.activate();
        }
    }

    @Override
    public void updateMinesBalance(int numberOfMines) {
        minesBalanceTextField.setText(String.valueOf(numberOfMines));
    }

    @Override
    public void updateTime(int time) {
        timeTextField.setText(String.valueOf(time));
    }

    @Override
    public void updateGameState(boolean isVictory) {
        if (isVictory) {
            infoLabel.setText("Win!");
        } else {
            infoLabel.setText("Fail!");
        }
        mineFieldGUI.block();
    }
}