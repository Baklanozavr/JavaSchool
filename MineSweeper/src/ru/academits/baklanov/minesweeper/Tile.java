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
        //numberOfAdjacentMines = -1; //TODO зачем это?
    }

    boolean isMine() {
        return isMine;
    }

    boolean markTile() {
        isFlag = !isFlag;
        return isFlag;
    }

    boolean isMarked() {
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

    void clear() {
        isMine = false;
        isFlag = false;
        isOpened = false;
        numberOfAdjacentMines = 0;
    }

    void removeMine() {
        isMine = false;
        //numberOfAdjacentMines = 0;
    }
}