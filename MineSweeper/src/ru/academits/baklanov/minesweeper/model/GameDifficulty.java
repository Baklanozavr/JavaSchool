package ru.academits.baklanov.minesweeper.model;

import java.io.Serializable;

public enum GameDifficulty implements Serializable {
    EASY(8, 8, 10, "Easy"),
    MEDIUM(16, 16, 40, "Medium"),
    HARD(31, 16, 99, "Hard");

    private final int fieldWidth;
    private final int fieldHeight;
    private final int totalNumberOfMines;
    private String name;

    GameDifficulty(int fieldWidth, int fieldHeight, int totalNumberOfMines, String name) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.totalNumberOfMines = totalNumberOfMines;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldSize() {
        return fieldHeight * fieldWidth;
    }

    public int getTotalNumberOfMines() {
        return totalNumberOfMines;
    }
}