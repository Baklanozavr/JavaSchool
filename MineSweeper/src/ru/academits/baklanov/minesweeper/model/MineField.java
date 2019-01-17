package ru.academits.baklanov.minesweeper.model;

import java.util.*;

class MineField {
    private GameTile[][] field;
    private int height;
    private int width;
    private int totalNumberOfMines;

    MineField(int height, int width, int totalNumberOfMines) {
        this.height = height;
        this.width = width;
        this.totalNumberOfMines = totalNumberOfMines;

        field = new GameTile[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                field[i][j] = new GameTile();
            }
        }
    }

    GameTile getTile(int i, int j) {
        checkIndexes(i, j);
        return field[i][j];
    }

    ArrayList<Tile> getNeighbors(int i, int j) {
        checkIndexes(i, j);

        ArrayList<Tile> neighbors = new ArrayList<>();

        for (int m = i - 1; m <= i + 1; ++m) {
            if (m >= 0 && m < height) {
                for (int n = j - 1; n <= j + 1; ++n) {
                    if (n >= 0 && n < width) {
                        neighbors.add(field[m][n]);
                    }
                }
            }
        }

        return neighbors;
    }

    ArrayList<Tile> getNeighbors(Tile tile) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (Objects.equals(tile, field[i][j])) {
                    return getNeighbors(i, j);
                }
            }
        }
        return null;
    }

    void setMines(int iStart, int jStart) {
        checkIndexes(iStart, jStart);

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

    void showMines() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                field[i][j].showMine();
            }
        }
    }

    void clear() {
        for (Tile[] tilesRow : field) {
            for (Tile tile : tilesRow) {
                tile.clear();
            }
        }
    }

    private void checkIndexes(int height, int width) {
        if (height < 0 || height >= this.height || width < 0 || width >= this.width) {
            throw new IllegalArgumentException("Выход за пределы поля!");
        }
    }
}