package ru.academits.baklanov.minesweeper.model;

import java.util.*;

class MineField {
    private GameTile[][] field;
    private int width;
    private int height;
    private int totalNumberOfMines;

    MineField(int width, int height, int totalNumberOfMines) {
        this.width = width;
        this.height = height;
        this.totalNumberOfMines = totalNumberOfMines;

        field = new GameTile[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                field[i][j] = new GameTile();
            }
        }
    }

    GameTile getTile(int i, int j) {
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

    private HashMap<Integer, HashSet<Integer>> getIndexesOfNeighbors(int i, int j) {
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

    ArrayList<Tile> getNeighbors(Tile tile) {
        for(int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (Objects.equals(tile, field[i][j])) {
                    return getNeighbors(i, j);
                }
            }
        }
        return null;
    }

    void clear() {
        for (Tile[] tiles : field) {
            for (Tile tile : tiles) {
                tile.clear();
            }
        }
    }

    void showMines() {
        for(int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                field[i][j].showMine();
            }
        }
    }
}