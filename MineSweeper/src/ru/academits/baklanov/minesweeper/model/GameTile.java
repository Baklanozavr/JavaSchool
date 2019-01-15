package ru.academits.baklanov.minesweeper.model;

import ru.academits.baklanov.minesweeper.TileUI;

public class GameTile extends Tile {
    private TileUI tileUI;

    GameTile() {
        super();
        tileUI = null;
    }

    public enum State {
        CLOSED, FLAG, MINE, ERROR_MINE, BOOMED_MINE, EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    @Override
    public void clear() {
        super.clear();
        tileUI.update(getState());
    }

    @Override
    public void open() {
        super.open();
        tileUI.update(getState());
    }

    @Override
    public boolean setFlag() {
        boolean result = super.setFlag();
        if (result) {
            tileUI.update(State.FLAG);
        } else {
            tileUI.update(State.CLOSED);
        }

        return result;
    }

    void showMine() {
        if (isMine() && !isOpened()) {
            tileUI.update(State.MINE);
        }

        if (isFlag() && !isMine()) {
            tileUI.update(State.ERROR_MINE);
        }
    }

    void registerUI(TileUI tileUI) {
        this.tileUI = tileUI;
    }

    private State getState() {
        if (isOpened()) {
            if (isMine()) {
                GameProcess.isFail = true;
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

        return State.CLOSED;
    }
}