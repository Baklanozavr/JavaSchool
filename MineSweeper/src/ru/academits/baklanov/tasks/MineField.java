package ru.academits.baklanov.tasks;

import java.util.Random;

public class MineField {
    private Tile[] field;
    private int[] minesIndexes;
    private int height = 8;
    int width = height;

    public MineField() {
        field = new Tile[height * width];

        minesIndexes = null;

        for (int i = 0; i < field.length; ++i) {
            field[i] = new Tile();
        }
    }

    public int getWidth() {
        return width;
    }

    public Tile[] getField() {
        return field;
    }

    public void setMines(int startIndex) {
        field[startIndex].setMine();
        for (Tile neighbor : getNeighbors(startIndex)) {
            neighbor.setMine();
        }

        int howMany = 10;

        minesIndexes = new int[howMany];

        Random random = new Random();

        int minesCounter = 0;

        while (minesCounter < howMany) {
            int randomNumber = random.nextInt(field.length);

            if (!field[randomNumber].isMine()) {
                field[randomNumber].setMine();
                minesIndexes[minesCounter] = randomNumber;
                ++minesCounter;
            }
        }

        field[startIndex].clear();
        for (Tile neighbor : getNeighbors(startIndex)) {
            neighbor.clear();
        }

        for (int mineIndex : minesIndexes) {
            for (Tile neighbor : getNeighbors(mineIndex)) {
                if (!neighbor.isMine()) {
                    neighbor.addAdjacentMine();
                }
            }
        }
    }

    private Tile[] getNeighbors(int index) {
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
            if(candidates[i] > 0 && candidates[i] < field.length) {
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