package ru.academits.baklanov.minesweeper.model;

import ru.academits.baklanov.minesweeper.TileUI;
import ru.academits.baklanov.minesweeper.gui.MineSweeperGUI;

import java.util.*;
import java.util.function.BiConsumer;

public class GameProcess {
    private static Difficulty defaultDifficulty = Difficulty.MEDIUM;

    private Difficulty difficulty;
    private MineField mineField;
    private boolean isFail;
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

    public GameProcess(Difficulty difficulty) {
        if (difficulty == null) {
            throw new NullPointerException("Сложность не установлена!");
        }

        mineField = new MineField(difficulty.fieldWidth, difficulty.fieldHeight, difficulty.totalNumberOfMines);
        openedTilesCounter = 0;
        timeInSeconds = 0;
        leftMinesCounter = difficulty.totalNumberOfMines;
        isFail = false;
        this.difficulty = difficulty;

        timer = new Timer();

        gameUI = null;
    }

    public MineField getMineField() {
        return mineField;
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

    public void markTile(int clickedIndex) {
        int verticalIndex = clickedIndex / difficulty.fieldWidth;
        int horizontalIndex = clickedIndex % difficulty.fieldWidth;

        Tile clickedTile = mineField.getTile(verticalIndex, horizontalIndex);

        boolean isMarked = clickedTile.setFlag();

        if (isMarked) {
            --leftMinesCounter;
        } else {
            ++leftMinesCounter;
        }

        gameUI.updateMinesBalance(leftMinesCounter);
    }

    public void openTile(int clickedIndex) {
        int verticalStartIndex = clickedIndex / difficulty.fieldWidth;
        int horizontalStartIndex = clickedIndex % difficulty.fieldWidth;

        if (!mineField.getTile(verticalStartIndex, horizontalStartIndex).isFlag()) {
            if (openedTilesCounter == 0) {
                mineField.setMines(verticalStartIndex, horizontalStartIndex);
                startTimer();
            }

            Queue<Integer> indexesForOpen = new LinkedList<>();
            HashSet<Integer> visitedIndexes = new HashSet<>();

            BiConsumer<Integer, HashSet<Integer>> addNeighbors = (verticalIndex, horizontalIndexes) -> {
                horizontalIndexes.forEach(j -> {
                    Tile neighbor = mineField.getTile(verticalIndex, j);

                    if (!neighbor.isOpened() && !neighbor.isFlag()) {
                        int newIndex = verticalIndex * difficulty.fieldWidth + j;

                        if (visitedIndexes.add(newIndex)) {
                            indexesForOpen.add(newIndex);
                        }
                    }
                });
            };

            if (mineField.getTile(verticalStartIndex, horizontalStartIndex).isOpened()) {
                if (checkAdjacentFlags(verticalStartIndex, horizontalStartIndex)) {
                    mineField.getIndexesOfNeighbors(verticalStartIndex, horizontalStartIndex).forEach(addNeighbors);
                }
            } else {
                visitedIndexes.add(clickedIndex);
                indexesForOpen.add(clickedIndex);
            }

            Integer index = indexesForOpen.poll();
            while (index != null) {
                int verticalIndex = index / difficulty.fieldWidth;
                int horizontalIndex = index % difficulty.fieldWidth;

                Tile selectedTile = mineField.getTile(verticalIndex, horizontalIndex);
                selectedTile.open();

                if (selectedTile.getNumberOfAdjacentMines() == 0) {
                    mineField.getIndexesOfNeighbors(verticalIndex, horizontalIndex).forEach(addNeighbors);
                }

                index = indexesForOpen.poll();
            }

            openedTilesCounter += visitedIndexes.size();
        }
    }

    public boolean isFail() {
        return isFail;
    }

    public boolean isVictory() {
        return !isFail && openedTilesCounter == difficulty.getFieldSize() - difficulty.totalNumberOfMines;
    }

    public void restart() {
        leftMinesCounter = difficulty.totalNumberOfMines;
        openedTilesCounter = 0;
        mineField.clear();
        isFail = false;

        timeInSeconds = 0;
        timer.cancel();
        timer = new Timer();

        gameUI.updateTime(timeInSeconds);
        gameUI.updateMinesBalance(leftMinesCounter);
    }

    public void restart(Difficulty difficulty) {
        if (difficulty == this.difficulty) {
            restart();
        } else {
            defaultDifficulty = difficulty;

            this.difficulty = difficulty;
            mineField = new MineField(difficulty.fieldWidth, difficulty.fieldHeight, difficulty.totalNumberOfMines);
            openedTilesCounter = 0;
            leftMinesCounter = difficulty.totalNumberOfMines;
            isFail = false;
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