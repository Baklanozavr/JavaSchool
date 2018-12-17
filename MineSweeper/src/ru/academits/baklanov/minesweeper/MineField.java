package ru.academits.baklanov.minesweeper;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

class MineField {
    private Tile[] field;
    private int width;
    private BitSet mines;
    private int totalNumberOfMines;

    MineField(int width, int height, int totalNumberOfMines) {
        field = new Tile[width * height];
        this.width = width;
        mines = new BitSet(width * height);
        this.totalNumberOfMines = totalNumberOfMines;

        for (int i = 0; i < field.length; ++i) {
            field[i] = new Tile();
        }
    }

    Tile getTile(int index) {
        return field[index];
    }

    void setMines(int startIndex) {
        mines.set(startIndex);
        getIndexesOfNeighbors(startIndex).forEach(mines::set);

        Random random = new Random();
        int minesCounter = 0;

        while (minesCounter < totalNumberOfMines) {
            int randomPosition = random.nextInt(field.length);

            if (!mines.get(randomPosition)) {
                mines.set(randomPosition);
                field[randomPosition].setMine();
                ++minesCounter;
            }
        }

        mines.flip(startIndex);
        getIndexesOfNeighbors(startIndex).forEach(mines::flip);

        for (int i = mines.nextSetBit(0); i >= 0; i = mines.nextSetBit(i + 1)) {
            for (Tile neighbor : getNeighbors(i)) {
                if (!neighbor.isMine()) {
                    neighbor.addAdjacentMine();
                }
            }
        }

        /*for (int i = 0; i < field.length; ++i) {
            if (field[i].isMine()) {
                for (Tile neighbor : getNeighbors(i)) {
                    if (!neighbor.isMine()) {
                        neighbor.addAdjacentMine();
                    }
                }
            }
        }*/
    }

    ArrayList<Integer> getIndexesOfNeighbors(int index) {
        if (index < 0 || index > field.length) {
            throw new IllegalArgumentException("Выход за пределы поля!");
        }

        int[] candidates = {index - width - 1, index - width, index - width + 1,
                index - 1, index + 1, index + width - 1, index + width, index + width + 1};

        if (index % width == 0) {
            candidates[0] = -1;
            candidates[3] = -1;
            candidates[5] = -1;
        }

        if ((index + 1) % width == 0) {
            candidates[2] = -1;
            candidates[4] = -1;
            candidates[7] = -1;
        }

        ArrayList<Integer> indexesOfNeighbors = new ArrayList<>();

        for (int i : candidates) {
            if (i >= 0 && i < field.length) {
                indexesOfNeighbors.add(i);
            }
        }

        return indexesOfNeighbors;
    }

    Tile[] getNeighbors(int index) {
        if (index < 0 || index > field.length) {
            throw new IllegalArgumentException("Выход за пределы поля!");
        }

        int[] candidates = {index - width - 1, index - width, index - width + 1,
                index - 1, index + 1, index + width - 1, index + width, index + width + 1};

        if (index % width == 0) {
            candidates[0] = -1;
            candidates[3] = -1;
            candidates[5] = -1;
        }

        if (index % width == width - 1) {
            candidates[2] = -1;
            candidates[4] = -1;
            candidates[7] = -1;
        }

        int neighborsCounter = 0;

        for (int i = 0; i < candidates.length; ++i) {
            if (candidates[i] >= 0 && candidates[i] < field.length) {
                ++neighborsCounter;
            } else {
                candidates[i] = -1;
            }
        }

        Tile[] neighbors = new Tile[neighborsCounter];
        neighborsCounter = 0;

        for (int i : candidates) {
            if (i >= 0) {
                neighbors[neighborsCounter] = field[i];
                ++neighborsCounter;
            }
        }

        return neighbors;
    }
}