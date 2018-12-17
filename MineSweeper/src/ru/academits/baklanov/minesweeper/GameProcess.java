package ru.academits.baklanov.minesweeper;

import java.util.*;

public class GameProcess {
    private MineField mineField;
    private int openedTilesCounter;
    private boolean isFail;
    private Difficulty difficulty;

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

        if (tile.isFlag() && !tile.isMine()) {
            return TileGameState.ERROR_MINE;
        }

        return TileGameState.MINE;
    }

    private boolean checkAdjacentFlags(int index) {
        Tile[] neighbors = mineField.getNeighbors(index);

        int flagsAndMinesCounter = 0;

        for (Tile neighbor : neighbors) {
            if (!neighbor.isOpened() && neighbor.isFlag()) {
                ++flagsAndMinesCounter;
            }
        }

        return flagsAndMinesCounter == mineField.getTile(index).getNumberOfAdjacentMines();
    }

    private ArrayList<Integer> openTilesFrom(int index) {
        ArrayList<Integer> tilesForOpen = new ArrayList<>();

        if (mineField.getTile(index).isOpened()) {
            if (checkAdjacentFlags(index)) {
                for (int indexOfNeighbor : mineField.getIndexesOfNeighbors(index)) {
                    Tile neighbor = mineField.getTile(indexOfNeighbor);

                    if (!neighbor.isOpened() && !neighbor.isFlag()) {
                        tilesForOpen.add(indexOfNeighbor);
                    }
                }
            }
        } else {
            tilesForOpen.add(index);
        }

        for (int i = 0; i < tilesForOpen.size(); ++i) {
            int indexOfTile = tilesForOpen.get(i);

            if (mineField.getTile(indexOfTile).getNumberOfAdjacentMines() == 0) {
                for (int indexOfNeighbor : mineField.getIndexesOfNeighbors(indexOfTile)) {
                    Tile neighbor = mineField.getTile(indexOfNeighbor);

                    if (!neighbor.isOpened() && !neighbor.isFlag()) {
                        tilesForOpen.add(indexOfNeighbor);
                    }
                }
            }

            mineField.getTile(indexOfTile).open();
        }

        HashSet<Integer> openedTiles = new HashSet<>(tilesForOpen);

        openedTilesCounter += openedTiles.size();

        System.out.println(openedTilesCounter);

        return new ArrayList<>(openedTiles);
    }

    public Boolean markTile(int clickedIndex) {
        if (mineField.getTile(clickedIndex).isOpened()) {
            return null;
        }

        return mineField.getTile(clickedIndex).setFlag();
    }

    public HashMap<Integer, TileGameState> openTiles(int clickedIndex) {
        if (openedTilesCounter == 0) {
            mineField.setMines(clickedIndex);
        }

        HashMap<Integer, TileGameState> tilesForChange = new HashMap<>();

        if (!mineField.getTile(clickedIndex).isFlag()) {
            ArrayList<Integer> tilesForOpen = openTilesFrom(clickedIndex);

            for (Integer i : tilesForOpen) {
                Tile selectedTile = mineField.getTile(i);

                tilesForChange.put(i, getTileGameState(selectedTile));
            }
        }

        return tilesForChange;
    }

    public boolean isFail() {
        return isFail;
    }

    public boolean isVictory() {
        return !isFail && openedTilesCounter == difficulty.getFieldSize() - difficulty.getTotalNumberOfMines();
    }
}