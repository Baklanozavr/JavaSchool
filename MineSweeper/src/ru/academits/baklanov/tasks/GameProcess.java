package ru.academits.baklanov.tasks;

import java.util.*;

public class GameProcess {
    private MineField mineField;
    private BitSet openedTiles;
    private BitSet flags;
    private int openedTilesCounter;
    private boolean isFail;

    public enum Difficulty {
        EASY(8, 8, 10, "Новичок"),
        MEDIUM(16, 16, 40, "Любитель"),
        HARD(31, 16, 99, "Профессионал");

        private final int fieldWidth;
        private final int fieldHeight;
        private final int fieldSize;
        private final int totalNumberOfMines;
        private String name;

        Difficulty(int fieldWidth, int fieldHeight, int totalNumberOfMines, String name) {
            this.fieldWidth = fieldWidth;
            this.fieldHeight = fieldHeight;
            this.fieldSize = fieldWidth * fieldHeight;
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

    public enum TileState {
        MINE, ERROR_MINE, BOOMED_MINE, FLAG, NO_FLAG, EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    public GameProcess(Difficulty difficulty) {
        if (difficulty == null) {
            throw new NullPointerException("Сложность не установлена!");
        }

        mineField = new MineField(difficulty.fieldWidth, difficulty.fieldHeight, difficulty.totalNumberOfMines);
        flags = new BitSet(difficulty.fieldSize);
        openedTiles = new BitSet(difficulty.fieldSize);
        openedTilesCounter = 0;
        isFail = false;
    }

    public MineField getMineField() {
        return mineField;
    }

    public boolean setFlag(int indexOfTile) {
        flags.flip(indexOfTile);
        return flags.get(indexOfTile);
    }

    public static Difficulty[] getVariantsOfDifficulty() {
        return Difficulty.values();
    }

    public boolean isOpen(int indexOfTile) {
        return openedTiles.get(indexOfTile);
    }

    public boolean isFlag(int indexOfTile) {
        return flags.get(indexOfTile);
    }

    public BitSet getOpenedTiles() {
        return openedTiles;
    }

    private boolean checkAdjacentFlags(int index) {
        ArrayList<Integer> neighbors = mineField.getIndexesOfNeighbors(index);

        int flagsAndMinesCounter = 0;

        for (int indexOfNeighbor : neighbors) {
            if (!openedTiles.get(indexOfNeighbor) && flags.get(indexOfNeighbor)) {
                ++flagsAndMinesCounter;
            }
        }

        return flagsAndMinesCounter == mineField.getTile(index).getNumberOfAdjacentMines();
    }

    //возвращает набор индексов для открытия
    public ArrayList<Integer> openTilesFrom(int index) {
        BitSet indexesForOpen = new BitSet(mineField.getField().length);
        ArrayList<Integer> tilesForOpen = new ArrayList<>();

        if (openedTiles.get(index)) {
            if (checkAdjacentFlags(index)) {
                for (int indexOfNeighbor : mineField.getIndexesOfNeighbors(index)) {
                    if (!openedTiles.get(indexOfNeighbor) && !flags.get(indexOfNeighbor)) {
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
                    if (!openedTiles.get(indexOfNeighbor) && !flags.get(indexOfNeighbor)) {
                        tilesForOpen.add(indexOfNeighbor);
                    }
                }
            }

            indexesForOpen.set(indexOfTile);
            openedTiles.set(indexOfTile);
        }

        openedTilesCounter += tilesForOpen.size();

        return tilesForOpen;
    }

    public HashMap<Integer, String> markTile(int clickedIndex) {
        HashMap<Integer, String> tilesForChange = new HashMap<>();

        if (!isOpen(clickedIndex)) {
            String result = "";

            if (setFlag(clickedIndex)) {
                result = "F";
            }

            tilesForChange.put(clickedIndex, result);
        }

        return tilesForChange;
    }

    public HashMap<Integer, String> openTiles(int clickedIndex) {
        HashMap<Integer, String> tilesForChange = new HashMap<>();
        ArrayList<Integer> tilesForOpen = openTilesFrom(clickedIndex);

        String result;

        for (Integer i : tilesForOpen) {
            Tile selectedTile = mineField.getTile(i);

            if (selectedTile.isMine()) {
                result = "X";
            } else {
                int numberOfAdjacentMines = selectedTile.getNumberOfAdjacentMines();

                if (numberOfAdjacentMines != 0) {
                    result = String.valueOf(numberOfAdjacentMines);
                } else {
                    result = "";
                }
            }

            tilesForChange.put(i, result);
        }

        return tilesForChange;
    }

    public boolean isFail() {
        return isFail;
    }
}