package ru.academits.baklanov.minesweeper;

import ru.academits.baklanov.minesweeper.gui.GameTileUI;

public class GameTile extends Tile {
    private State state;
    private GameTileUI gameTileUI;

    public GameTile() {
        super();
        state = State.CLOSED;
    }

    public enum State {
        CLOSED, FLAG, MINE, ERROR_MINE, BOOMED_MINE, EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    @Override
    public void open() {
        super.open();
        gameTileUI.update(getState());
    }

    @Override
    public boolean setFlag() {
        boolean result = super.setFlag();
        gameTileUI.update(getState());
        return result;
    }

    public void showMine() {
        if (isMine() && !isOpened()) {
            gameTileUI.update(State.MINE);
        }
    }

    private State getState() {
        if (isOpened()) {
            if (isMine()) {
                return State.BOOMED_MINE;
            }

            switch (getNumberOfAdjacentMines()) {
                case 0:
                    return State.EMPTY;
                case 1:
                    return State.ONE;
                case 2:
                    return State.TWO;
                case 3:
                    return State.THREE;
                case 4:
                    return State.FOUR;
                case 5:
                    return State.FIVE;
                case 6:
                    return State.SIX;
                case 7:
                    return State.SEVEN;
                case 8:
                    return State.EIGHT;
            }
        }

        if (isFlag() && !isMine()) {
            return State.ERROR_MINE;
        }

        /*if (isMine() && !isFlag()) {
            return State.MINE;
        }*/

        return State.CLOSED;
    }
}