package ru.academits.baklanov.minesweeper;

public interface MineSweeperUI {
    void updateGameState(boolean isVictory); //true if victory, false if fail
    void updateMinesBalance(int minesLeftNumber);
    void updateTime(int timeInSeconds);
}
