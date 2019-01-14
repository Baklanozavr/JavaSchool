package ru.academits.baklanov.minesweeper.model;

class Tile {
    private boolean isMine;
    private boolean isFlag;
    private boolean isOpened;
    private int numberOfAdjacentMines;

    Tile() {
        clear();
    }

    void setMine() {
        isMine = true;
    }

    void removeMine() {
        isMine = false;
    }

    boolean isMine() {
        return isMine;
    }

    boolean setFlag() {
        if (!isOpened) {
            isFlag = !isFlag;
        }
        return isFlag;
    }

    boolean isFlag() {
        return isFlag;
    }

    void open() {
        if (!isFlag) {
            isOpened = true;
        }
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

    void clear() {
        isMine = false;
        isFlag = false;
        isOpened = false;
        numberOfAdjacentMines = 0;
    }
}