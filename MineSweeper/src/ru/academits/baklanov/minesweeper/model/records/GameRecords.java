package ru.academits.baklanov.minesweeper.model.records;

import ru.academits.baklanov.minesweeper.model.GameDifficulty;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public enum GameRecords implements Serializable {
    TABLE;

    private final String fileNameToSave = "minesweeper/src/ru/academits/baklanov/minesweeper/model/records/tableOfChampions.bin";

    private final int maxNumberOfWinnersPerDifficulty = 10;

    private HashMap<String, TreeSet<WinResult>> tableOfChampions;

    GameRecords() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNameToSave))) {
            //noinspection unchecked
            tableOfChampions = (HashMap<String, TreeSet<WinResult>>) in.readObject();
        } catch (IOException e) {
            tableOfChampions = new HashMap<>();

            for (GameDifficulty difficulty : GameDifficulty.values()) {
                tableOfChampions.put(difficulty.getName(), new TreeSet<>(WinResult.getComparator()));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Получить таблицу рекордов не удалось!");
        }
    }

    public void registerNewRecord(GameDifficulty difficulty, int time, int numberOfClicks, String nameOfPlayer) {
        WinResult winResult = new WinResult(time, numberOfClicks, nameOfPlayer);

        int numberOfBetterRecords = tableOfChampions.get(difficulty.getName()).tailSet(winResult).size();

        boolean isNewRecord = numberOfBetterRecords < maxNumberOfWinnersPerDifficulty;

        if (isNewRecord) {
            tableOfChampions.get(difficulty.getName()).add(winResult);
            save();
        }
    }

    public String[] getWinParameters() {
        return new String[]{"Time", "Number of clicks", "Clicks per second", "Player's name"};
    }

    public int getMaxNumberOfWinners() {
        return maxNumberOfWinnersPerDifficulty;
    }

    public ArrayList<WinResult> getRecords(GameDifficulty difficulty) {
        ArrayList<WinResult> results = new ArrayList<>(tableOfChampions.get(difficulty.getName()).size());

        results.addAll(tableOfChampions.get(difficulty.getName()));

        return results;
    }

    private void save() {
       try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileNameToSave, false))) {
            out.writeObject(tableOfChampions);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить таблицу лидеров!");
        }
    }
}