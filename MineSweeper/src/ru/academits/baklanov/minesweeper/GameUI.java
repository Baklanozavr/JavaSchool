package ru.academits.baklanov.minesweeper;

public interface GameUI {
    void updateGameState(boolean isVictory); //true if victory, false if fail
    void updateMinesBalance();
    void updateTime();
}
