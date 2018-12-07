package ru.academits.baklanov.tasks;

public class Main {
    public static void main(String[] args) {
        //testMineField();

        MineSweeperGUI mineSweeper = new MineSweeperGUI();
    }

    private static void testMineField() {
        MineField field = new MineField(31, 16, 99);

        int start = 31 * 16 - 1;

        field.setMines(start);

        int counter = 0;

        for (Tile tile : field.getField()) {
            if (tile.isMine()) {
                System.out.print("* ");
            } else {
                System.out.printf("%d ", tile.getNumberOfAdjacentMines());
            }

            ++counter;
            if (counter == field.getWidth()) {
                System.out.printf("%n");
                counter = 0;
            }
        }
    }
}