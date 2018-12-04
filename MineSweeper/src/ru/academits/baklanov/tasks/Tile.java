package ru.academits.baklanov.tasks;

public class Tile {
    private boolean isMine;
    private boolean isFlag;
    private int numberOfAdjacentMines;

    public Tile() {
        isMine = false;
        isFlag = false;
        numberOfAdjacentMines = 0;
    }

    public void setMine() {
        isMine = true;
    }

    public void setFlag() {
        isFlag = true;
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

    public void clear() {
        isMine = false;
        isFlag = false;
        numberOfAdjacentMines = 0;
    }
}