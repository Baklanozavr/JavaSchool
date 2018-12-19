package ru.academits.baklanov.minesweeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

class MineField {
    private Tile[][] field;
    private int width;
    private int height;
    private int fieldSize; //TODO это вообще надо???
    private int totalNumberOfMines;

    MineField(int width, int height, int totalNumberOfMines) {
        field = new Tile[height][width];
        this.width = width;
        this.height = height;
        fieldSize = width * height;
        this.totalNumberOfMines = totalNumberOfMines;

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                field[i][j] = new Tile();
            }
        }
    }

    Tile getTile(int i, int j) {
        return field[i][j];
    }

    void setMines(int iStart, int jStart) {
        field[iStart][jStart].setMine();
        getNeighbors(iStart, jStart).forEach(Tile::setMine);

        Random random = new Random();
        int minesCounter = 0;

        while (minesCounter < totalNumberOfMines) {
            Tile randomTile = field[random.nextInt(height)][random.nextInt(width)];

            if (!randomTile.isMine()) {
                randomTile.setMine();
                ++minesCounter;
            }
        }

        field[iStart][jStart].removeMine();
        getNeighbors(iStart, jStart).forEach(Tile::removeMine);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (field[i][j].isMine()) {
                    getNeighbors(i, j).forEach(Tile::addAdjacentMine);
                }
            }
        }
    }

    HashMap<Integer, HashSet<Integer>> getIndexesOfNeighbors(int i, int j) {
        if (i < 0 || i >= height || j < 0 || j >= width) {
            throw new IllegalArgumentException("Выход за пределы поля!");
        }

        HashMap<Integer, HashSet<Integer>> indexesOfNeighbors = new HashMap<>();

        for (int m = i - 1; m <= i + 1; ++m) {
            if (m >= 0 && m < height) {
                HashSet<Integer> horizontalIndexes = new HashSet<>();

                for (int n = j - 1; n <= j + 1; ++n) {
                    if (n >= 0 && n < width) {
                        horizontalIndexes.add(n);
                    }
                }

                indexesOfNeighbors.put(m, horizontalIndexes);
            }
        }

        return indexesOfNeighbors;
    }

    ArrayList<Tile> getNeighbors(int i, int j) {
        ArrayList<Tile> neighbors = new ArrayList<>();

        HashMap<Integer, HashSet<Integer>> indexesOfNeighbors = getIndexesOfNeighbors(i, j);

        indexesOfNeighbors.keySet().forEach(verticalIndex ->
                indexesOfNeighbors.get(verticalIndex).forEach(horizontalIndex ->
                        neighbors.add(field[verticalIndex][horizontalIndex])));

        return neighbors;
    }
}