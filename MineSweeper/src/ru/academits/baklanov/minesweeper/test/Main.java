package ru.academits.baklanov.minesweeper.test;

import ru.academits.baklanov.minesweeper.gui.MineSweeperGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MineSweeperGUI());
    }
}