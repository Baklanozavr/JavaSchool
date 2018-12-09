package ru.academits.baklanov.tasks;

import java.util.*;

public class GameProcess {
    private Difficulty difficulty;
    private MineField mineField;
    private BitSet openedTiles;
    private BitSet flags;

    public GameProcess(Difficulty difficulty) {
        if (difficulty == null) {
            throw new NullPointerException("Сложность не установлена!");
        }

        this.difficulty = difficulty;
        mineField = new MineField(difficulty.width, difficulty.height, difficulty.totalNumberOfMines);
        flags = new BitSet(difficulty.fieldSize);
        openedTiles = new BitSet(difficulty.fieldSize);
    }

    public enum Difficulty {
        EASY(8, 8, 10, "Новичок"),
        MEDIUM(16, 16, 40, "Любитель"),
        HARD(31, 16, 99, "Профессионал");

        private final int width;
        private final int height;
        private final int fieldSize;
        private final int totalNumberOfMines;
        private String name;

        Difficulty(int width, int height, int totalNumberOfMines, String name) {
            this.width = width;
            this.height = height;
            this.fieldSize = width * height;
            this.totalNumberOfMines = totalNumberOfMines;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public MineField getMineField() {
        return mineField;
    }

    public void setNewDifficulty(Difficulty difficulty) {
        mineField = new MineField(difficulty.width, difficulty.height, difficulty.totalNumberOfMines);
        flags = new BitSet(difficulty.fieldSize);
        openedTiles = new BitSet(difficulty.fieldSize);
    }

    public boolean setFlag(int indexOfTile) {
        flags.flip(indexOfTile);
        return flags.get(indexOfTile);
    }

    public Difficulty[] getVariantsOfDifficulty() {
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
        BitSet indexesForOpen = new BitSet(difficulty.fieldSize);
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

        return tilesForOpen;
    }
}