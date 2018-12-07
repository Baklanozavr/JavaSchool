package ru.academits.baklanov.tasks;

import javax.swing.*;
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
        MEDIUM(16, 16, 20),
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

    public BitSet getOpenedTiles() {
        return openedTiles;
    }

    //возвращает набор индексов для открытия
    public BitSet openTilesFrom(int index) {
        BitSet indexesForOpen = new BitSet(difficulty.fieldSize);
        ArrayList<Integer> tilesForOpen = new ArrayList<>();

        tilesForOpen.add(index);
/*
        tilesForOpen.forEach(indexOfTile -> {
            if (mineField.getTile(indexOfTile).getNumberOfAdjacentMines() == 0) {
                mineField.getIndexesOfNeighbors(indexOfTile).forEach(indexOfNeighbor -> {
                    if (!openedTiles.get(indexOfNeighbor)) {
                        tilesForOpen.add(indexOfNeighbor);
                    }
                });
            }
            indexesForOpen.set(indexOfTile);
            openedTiles.set(indexOfTile);
        });*/


        for (int i = 0; i < tilesForOpen.size(); ++i) {
            int indexOfTile = tilesForOpen.get(i);

            if (mineField.getTile(indexOfTile).getNumberOfAdjacentMines() == 0) {
                mineField.getIndexesOfNeighbors(indexOfTile).forEach(indexOfNeighbor -> {
                    if (!openedTiles.get(indexOfNeighbor)) {
                        tilesForOpen.add(indexOfNeighbor);
                    }
                });
            }

            indexesForOpen.set(indexOfTile);
            openedTiles.set(indexOfTile);
        }

        return indexesForOpen;
    }


    public void openTiles(ArrayList<JButton> fieldButtonsArray, MineField field, int indexOfClickedTile) {
        SwingUtilities.invokeLater(() -> {
            Tile selectedTile = field.getTile(indexOfClickedTile);

            int numberOfAdjacentMines = selectedTile.getNumberOfAdjacentMines();

            HashSet<JButton> buttonsForOpen = new HashSet<>();
            buttonsForOpen.add(fieldButtonsArray.get(indexOfClickedTile));


        });
    }
}