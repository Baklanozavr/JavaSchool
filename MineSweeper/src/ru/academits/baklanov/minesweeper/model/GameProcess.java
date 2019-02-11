package ru.academits.baklanov.minesweeper.model;

import ru.academits.baklanov.minesweeper.TileUI;
import ru.academits.baklanov.minesweeper.MineSweeperUI;
import ru.academits.baklanov.minesweeper.model.records.GameRecords;

import java.util.*;
import java.util.function.Consumer;

public class GameProcess {
    private final static GameDifficulty DEFAULT_GAME_DIFFICULTY = GameDifficulty.MEDIUM;

    static boolean isFail = false;

    private GameDifficulty gameDifficulty;
    private MineSweeperUI gameUI;
    private Timer timer;
    private MineField mineField;
    private int openedTilesCounter;
    private int leftMinesCounter;
    private int clicksCounter;
    private int timeInSeconds;

    private GameProcess(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        gameUI = null;

        mineField = new MineField(
                gameDifficulty.getFieldHeight(),
                gameDifficulty.getFieldWidth(),
                gameDifficulty.getTotalNumberOfMines());

        setStartValues();
    }

    public GameProcess() {
        this(DEFAULT_GAME_DIFFICULTY);
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void restart(GameDifficulty gameDifficulty) {
        if (gameDifficulty == null) {
            throw new NullPointerException("Сложность не установлена!");
        }

        if (gameDifficulty == this.gameDifficulty) {
            mineField.clear();
        } else {
            this.gameDifficulty = gameDifficulty;
            mineField = new MineField(
                    gameDifficulty.getFieldHeight(),
                    gameDifficulty.getFieldWidth(),
                    gameDifficulty.getTotalNumberOfMines());
        }

        timer.cancel();
        setStartValues();
    }

    public void registerUI(MineSweeperUI gameUI) {
        this.gameUI = gameUI;
    }

    public void registerTileUI(TileUI tileUI, int verticalIndex, int horizontalIndex) {
        mineField.getTile(verticalIndex, horizontalIndex).registerUI(tileUI);
    }

    public void markTile(int verticalIndex, int horizontalIndex) {
        ++clicksCounter;
        Tile clickedTile = mineField.getTile(verticalIndex, horizontalIndex);

        if (!clickedTile.isOpened()) {
            boolean isMarked = clickedTile.setFlag();

            if (isMarked) {
                --leftMinesCounter;
            } else {
                ++leftMinesCounter;
            }

            gameUI.updateMinesBalance(leftMinesCounter);
        }
    }

    public void openTile(int verticalIndex, int horizontalIndex) {
        ++clicksCounter;
        Tile clickedTile = mineField.getTile(verticalIndex, horizontalIndex);

        if (!clickedTile.isFlag()) {
            if (openedTilesCounter == 0) {
                mineField.setMines(verticalIndex, horizontalIndex);
                startTimer();
            }

            HashSet<Tile> visitedTiles = new HashSet<>();
            Queue<Tile> tilesForOpen = new LinkedList<>();

            Consumer<Tile> addNeighbor = (neighbor) -> {
                if (!neighbor.isOpened() && !neighbor.isFlag()) {
                    boolean isNotVisited = visitedTiles.add(neighbor);

                    if (isNotVisited) {
                        tilesForOpen.add(neighbor);
                    }
                }
            };

            if (clickedTile.isOpened()) {
                if (checkAdjacentFlags(verticalIndex, horizontalIndex)) {
                    mineField.getNeighbors(verticalIndex, horizontalIndex).forEach(addNeighbor);
                }
            } else {
                visitedTiles.add(clickedTile);
                tilesForOpen.add(clickedTile);
            }

            Tile tile = tilesForOpen.poll();

            while (tile != null) {
                tile.open();

                if (tile.getNumberOfAdjacentMines() == 0) {
                    mineField.getNeighbors(tile).forEach(addNeighbor);
                }

                tile = tilesForOpen.poll();
            }

            openedTilesCounter += visitedTiles.size();

            checkVictory();
        }
    }

    private boolean checkAdjacentFlags(int i, int j) {
        ArrayList<Tile> neighbors = mineField.getNeighbors(i, j);

        int flagsAndMinesCounter = 0;

        for (Tile neighbor : neighbors) {
            if (!neighbor.isOpened() && neighbor.isFlag()) {
                ++flagsAndMinesCounter;
            }
        }

        return flagsAndMinesCounter == mineField.getTile(i, j).getNumberOfAdjacentMines();
    }

    private boolean isVictory() {
        return !isFail && openedTilesCounter == gameDifficulty.getFieldSize() - gameDifficulty.getTotalNumberOfMines();
    }

    private void checkVictory() {
        if (isFail) {
            mineField.showMines();
            timer.cancel();
            gameUI.updateGameState(false);
        }

        if (isVictory()) {
            timer.cancel();
            gameUI.updateGameState(true);
        }
    }

    public void registerCurrentWin(String nameOfPlayer) {
        GameRecords.TABLE.registerNewRecord(gameDifficulty, timeInSeconds, clicksCounter, nameOfPlayer);
    }

    private void setStartValues() {
        isFail = false;
        leftMinesCounter = gameDifficulty.getTotalNumberOfMines();
        openedTilesCounter = 0;
        clicksCounter = 0;
        timeInSeconds = 0;
        timer = new Timer();
    }

    private void startTimer() {
        timer.schedule(
                new TimerTask() {
                    public void run() {
                        ++timeInSeconds;
                        gameUI.updateTime(timeInSeconds);
                    }
                },
                1000, 1000);
    }
}