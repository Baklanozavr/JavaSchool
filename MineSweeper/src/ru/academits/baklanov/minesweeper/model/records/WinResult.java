package ru.academits.baklanov.minesweeper.model.records;

import java.io.Serializable;
import java.util.Comparator;

public class WinResult implements Serializable {
    private int timeOfWinInSeconds;
    private int numberOfClicks;
    private String nameOfPlayer;

    WinResult(int time, int numberOfClicks, String nameOfPlayer) {
        timeOfWinInSeconds = time;
        this.numberOfClicks = numberOfClicks;
        this.nameOfPlayer = nameOfPlayer;
    }

    public int getTime() {
        return timeOfWinInSeconds;
    }

    public int getNumberOfClicks() {
        return numberOfClicks;
    }

    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    static ComparatorForWinResult getComparator() {
        return new ComparatorForWinResult();
    }

    private static class ComparatorForWinResult implements Comparator<WinResult> {
        @Override
        public int compare(WinResult result1, WinResult result2) {
            int resultOfTimeComparison = Integer.compare(result1.getTime(), result2.getTime());

            if (resultOfTimeComparison == 0) {
                return -Integer.compare(result1.getNumberOfClicks(), result2.getNumberOfClicks());
            }

            return resultOfTimeComparison;
        }
    }
}