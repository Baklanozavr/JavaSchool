package ru.academits.baklanov.minesweeper;

import java.util.*;
import java.util.function.BiConsumer;

public class GameProcess {
    private Difficulty difficulty;
    private MineField mineField;
    private boolean isFail;
    private int openedTilesCounter;

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

    public enum TileGameState {
        MINE, ERROR_MINE, BOOMED_MINE, EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    public GameProcess(Difficulty difficulty) {
        if (difficulty == null) {
            throw new NullPointerException("Сложность не установлена!");
        }

        mineField = new MineField(difficulty.fieldWidth, difficulty.fieldHeight, difficulty.totalNumberOfMines);
        openedTilesCounter = 0;
        isFail = false;
        this.difficulty = difficulty;
    }

    public static Difficulty[] getVariantsOfDifficulty() {
        return Difficulty.values();
    }

    private TileGameState getTileGameState(Tile tile) {
        if (tile.isOpened()) {
            if (tile.isMine()) {
                return TileGameState.BOOMED_MINE;
            }

            switch (tile.getNumberOfAdjacentMines()) {
                case 0:
                    return TileGameState.EMPTY;
                case 1:
                    return TileGameState.ONE;
                case 2:
                    return TileGameState.TWO;
                case 3:
                    return TileGameState.THREE;
                case 4:
                    return TileGameState.FOUR;
                case 5:
                    return TileGameState.FIVE;
                case 6:
                    return TileGameState.SIX;
                case 7:
                    return TileGameState.SEVEN;
                case 8:
                    return TileGameState.EIGHT;
            }
        }

        if (tile.isMarked() && !tile.isMine()) {
            return TileGameState.ERROR_MINE;
        }

        return TileGameState.MINE;
    }

    private boolean checkAdjacentFlags(int i, int j) {
        ArrayList<Tile> neighbors = mineField.getNeighbors(i, j);

        int flagsAndMinesCounter = 0;

        for (Tile neighbor : neighbors) {
            if (!neighbor.isOpened() && neighbor.isMarked()) {
                ++flagsAndMinesCounter;
            }
        }

        return flagsAndMinesCounter == mineField.getTile(i, j).getNumberOfAdjacentMines();
    }

    public Boolean markTile(int clickedIndex) {
        int verticalIndex = clickedIndex / difficulty.fieldWidth;
        int horizontalIndex = clickedIndex % difficulty.fieldWidth;

        Tile clickedTile = mineField.getTile(verticalIndex, horizontalIndex);

        if (clickedTile.isOpened()) {
            return null;
        }

        return clickedTile.markTile();
    }

    public HashMap<Integer, TileGameState> openTiles(int clickedIndex) {
        int verticalStartIndex = clickedIndex / difficulty.fieldWidth;
        int horizontalStartIndex = clickedIndex % difficulty.fieldWidth;

        HashMap<Integer, TileGameState> tilesForChange = new HashMap<>();

        if (!mineField.getTile(verticalStartIndex, horizontalStartIndex).isMarked()) {
            if (openedTilesCounter == 0) {
                mineField.setMines(verticalStartIndex, horizontalStartIndex);
            }

            Queue<Integer> indexesForOpen = new LinkedList<>();
            HashSet<Integer> visitedIndexes = new HashSet<>();

            BiConsumer<Integer, HashSet<Integer>> addNeighbors = (verticalIndex, horizontalIndexes) -> {
                horizontalIndexes.forEach(j -> {
                    Tile neighbor = mineField.getTile(verticalIndex, j);

                    if (!neighbor.isOpened() && !neighbor.isMarked()) {
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

            for (Integer i : visitedIndexes) {
                int verticalIndex = i / difficulty.fieldWidth;
                int horizontalIndex = i % difficulty.fieldWidth;

                Tile selectedTile = mineField.getTile(verticalIndex, horizontalIndex);

                tilesForChange.put(i, getTileGameState(selectedTile));
            }

            openedTilesCounter += visitedIndexes.size();
        }

        return tilesForChange;
    }

    public boolean isFail() {
        return isFail;
    }

    public boolean isVictory() {
        return !isFail && openedTilesCounter == difficulty.getFieldSize() - difficulty.totalNumberOfMines;
    }
}