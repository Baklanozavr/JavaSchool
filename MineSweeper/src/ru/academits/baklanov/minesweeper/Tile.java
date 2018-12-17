package ru.academits.baklanov.minesweeper;

class Tile {
    private boolean isMine;
    private boolean isFlag;
    private boolean isOpened;
    private int numberOfAdjacentMines;

    Tile() {
        isMine = false;
        isFlag = false;
        isOpened = false;
        numberOfAdjacentMines = 0;
    }

    void setMine() {
        isMine = true;
        numberOfAdjacentMines = -1;
    }

    boolean isMine() {
        return isMine;
    }

    boolean setFlag() {
        if (isFlag) {
            return isFlag = false;
        } else {
            return isFlag = true;
        }
    }

    boolean isFlag() {
        return isFlag;
    }

    void open() {
        isOpened = true;
    }

    boolean isOpened() {
        return isOpened;
    }

    void addAdjacentMine() {
        ++numberOfAdjacentMines;
    }

    int getNumberOfAdjacentMines() {
        return numberOfAdjacentMines;
    }
}