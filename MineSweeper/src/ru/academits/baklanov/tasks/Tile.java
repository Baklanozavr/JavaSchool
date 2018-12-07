package ru.academits.baklanov.tasks;

public class Tile {
    private boolean isMine;
    private boolean isFlag;
    private boolean isOpened;
    private int numberOfAdjacentMines;

    public Tile() {
        isMine = false;
        isFlag = false;
        numberOfAdjacentMines = 0;
    }

    public void setMine() {
        isMine = true;
        numberOfAdjacentMines = -1;
    }

    public void setFlag() {
        isFlag = true;
    }

    public boolean open() {
        if (isOpened) {
            return false;
        } else {
            return isOpened = true;
        }
    }

    public void addAdjacentMine() {
        ++numberOfAdjacentMines;
    }

    public int getNumberOfAdjacentMines() {
        return numberOfAdjacentMines;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void clear() {
        isMine = false;
        isFlag = false;
        numberOfAdjacentMines = 0;
    }
}