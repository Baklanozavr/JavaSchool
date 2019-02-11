package ru.academits.baklanov.minesweeper.gui;

import ru.academits.baklanov.minesweeper.MineSweeperUI;
import ru.academits.baklanov.minesweeper.model.GameDifficulty;
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
    private TableOfRecordsGUI tableOfRecordsGUI;

    public MineSweeperGUI() {
        game = new GameProcess();

        gameFrame = new JFrame("MineSweeper game");
        gamePanel = new JPanel();
        menuBar = new JMenuBar();
        infoLabel = new JLabel("New");

        minesBalanceTextField = new JTextField(String.valueOf(game.getGameDifficulty().getTotalNumberOfMines()), 3);
        minesBalanceTextField.setToolTipText("Number of unmarked mines");
        minesBalanceTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
        minesBalanceTextField.setHorizontalAlignment(JTextField.LEFT);
        minesBalanceTextField.setEditable(false);

        timeTextField = new JTextField("0", 3);
        timeTextField.setToolTipText("Seconds from the start of this game");
        timeTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
        timeTextField.setHorizontalAlignment(JTextField.RIGHT);
        timeTextField.setEditable(false);

        mineFieldGUI = new MineFieldGUI(game);
        tableOfRecordsGUI = new TableOfRecordsGUI(gameFrame);
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

        fillMenuBar(menuBar);

        gameFrame.setJMenuBar(menuBar);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    private void fillMenuBar(JMenuBar menuBar) {
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem newGame = new JMenuItem("Start new game");
        newGame.addActionListener(event -> restartGame(game.getGameDifficulty()));
        gameMenu.add(newGame);
        gameMenu.addSeparator();

        JMenuItem showRecords = new JMenuItem("Show records");
        showRecords.addActionListener(event -> tableOfRecordsGUI.showTable(game.getGameDifficulty()));
        gameMenu.add(showRecords);
        gameMenu.addSeparator();

        JMenu difficultyMenu = new JMenu("Set difficulty");
        for (GameDifficulty gameDifficulty : GameDifficulty.values()) {
            JMenuItem difficultySetting = new JMenuItem(gameDifficulty.getName());
            difficultySetting.addActionListener(event -> restartGame(gameDifficulty));
            difficultyMenu.add(difficultySetting);
        }
        gameMenu.add(difficultyMenu);
        gameMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(event -> System.exit(0));
        gameMenu.add(exit);
    }

    private void restartGame(GameDifficulty gameDifficulty) {
        GameDifficulty oldGameDifficulty = game.getGameDifficulty();

        game.restart(gameDifficulty);

        minesBalanceTextField.setText(String.valueOf(gameDifficulty.getTotalNumberOfMines()));
        infoLabel.setText("New");
        timeTextField.setText("0");

        mineFieldGUI.block();

        if (oldGameDifficulty != gameDifficulty) {
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
            game.registerCurrentWin(getNameOfPlayer());
            tableOfRecordsGUI.showTable(game.getGameDifficulty());
        } else {
            infoLabel.setText("Fail!");
        }
        mineFieldGUI.block();
    }

    private String getNameOfPlayer() {
        return JOptionPane.showInputDialog(gameFrame, "What's your name?");
    }
}