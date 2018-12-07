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
        EASY(8, 8, 10),
        MEDIUM(16, 16, 40),
        HARD(31, 16, 99);

        private final int width;
        private final int height;
        private final int fieldSize;
        private final int totalNumberOfMines;

        Difficulty(int width, int height, int totalNumberOfMines) {
            this.width = width;
            this.height = height;
            this.fieldSize = width * height;
            this.totalNumberOfMines = totalNumberOfMines;
        }
    }

    public MineField getMineField() {
        return mineField;
    }

    public boolean setFlag(int indexOfTile) {
        flags.flip(indexOfTile);
        return flags.get(indexOfTile);
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

    //возвращает набор индексов для открытия
    public BitSet openTilesFrom(int index) {
        BitSet indexesForOpen = new BitSet(difficulty.fieldSize);
        ArrayList<Integer> tilesForOpen = new ArrayList<>();

        tilesForOpen.add(index);

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

        return indexesForOpen;
    }


}