package ru.academits.baklanov.minesweeper.model;

import ru.academits.baklanov.minesweeper.TileUI;
import ru.academits.baklanov.minesweeper.gui.MineSweeperGUI;

import java.util.*;
import java.util.function.Consumer;

public class GameProcess {
    private static Difficulty defaultDifficulty = Difficulty.MEDIUM;

    public static boolean isFail = false;

    private Difficulty difficulty;
    private MineField mineField;
    private int openedTilesCounter;
    private int leftMinesCounter;
    private int timeInSeconds;
    private Timer timer;

    private MineSweeperGUI gameUI;

    public enum Difficulty {
        EASY(8, 8, 10, "Новичок"),
        MEDIUM(16, 16, 40, "Любитель"),
        HARD(31, 16, 99, "Профессионал");

        private final int fieldWidth;
        private final int fieldHeight;
        private final int totalNumberOfMines;
        private String name;

        Difficulty(int fieldWidth, int fieldHeight, int totalNumberOfMines, String name) {
            this.fieldWidth = fieldWidth;
            this.fieldHeight = fieldHeight;
            this.totalNumberOfMines = totalNumberOfMines;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getFieldWidth() {
            return fieldWidth;
        }

        public int getFieldHeight() {
            return fieldHeight;
        }

        public int getFieldSize() {
            return fieldHeight * fieldWidth;
        }

        public int getTotalNumberOfMines() {
            return totalNumberOfMines;
        }
    }

    private GameProcess(Difficulty difficulty) {
        this.difficulty = difficulty;

        mineField = new MineField(difficulty.fieldWidth, difficulty.fieldHeight, difficulty.totalNumberOfMines);

        setStartValues();

        gameUI = null;
    }

    public GameProcess() {
        this(defaultDifficulty);
    }

    public static Difficulty[] getVariantsOfDifficulty() {
        return Difficulty.values();
    }

    public Difficulty getDifficulty() {
        return difficulty;
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

    public void markTile(int verticalIndex, int horizontalIndex) {
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

    public boolean isVictory() {
        return !isFail && openedTilesCounter == difficulty.getFieldSize() - difficulty.totalNumberOfMines;
    }

    private void setStartValues() {
        isFail = false;
        leftMinesCounter = difficulty.totalNumberOfMines;
        openedTilesCounter = 0;
        timeInSeconds = 0;
        timer = new Timer();
    }

    public void restart() {
        timer.cancel();

        setStartValues();

        gameUI.updateTime(timeInSeconds);
        gameUI.updateMinesBalance(leftMinesCounter);
    }

    public void restart(Difficulty difficulty) {
        if (difficulty == null) {
            throw new NullPointerException("Сложность не установлена!");
        }

        if (difficulty == this.difficulty) {
            mineField.clear();
            restart();
        } else {
            this.difficulty = difficulty;
            mineField = new MineField(difficulty.fieldWidth, difficulty.fieldHeight, difficulty.totalNumberOfMines);
            restart();
        }
    }

    public void registerUI(MineSweeperGUI gameUI) {
        this.gameUI = gameUI;
    }

    public void registerTileUI(TileUI tileUI, int verticalIndex, int horizontalIndex) {
        mineField.getTile(verticalIndex, horizontalIndex).registerUI(tileUI);
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