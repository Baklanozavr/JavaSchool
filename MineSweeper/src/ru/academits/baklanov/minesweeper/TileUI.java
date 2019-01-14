package ru.academits.baklanov.minesweeper;

import ru.academits.baklanov.minesweeper.model.GameTile;

public interface TileUI {
    void update(GameTile.State state);
}
