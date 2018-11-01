package ru.academits.baklanov.tasks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream("Strings.txt"), StandardCharsets.UTF_8)) {
            ArrayList<String> stringsFromFile = new ArrayList<>();

            while (scanner.hasNextLine()) {
                stringsFromFile.add(scanner.nextLine());
            }

            for (String s : stringsFromFile) {
                System.out.println(s);
            }
        }

        int sizeOfTestList = 15;

        ArrayList<Integer> testListOfNumbers = new ArrayList<>(sizeOfTestList);
        for (int i = 0; i < sizeOfTestList; ++i) {
            testListOfNumbers.add((int) (Math.random() * 10));
        }
        System.out.println(testListOfNumbers);

        for (int i = 0; i < testListOfNumbers.size(); ) {
            if (testListOfNumbers.get(i) % 2 == 0) {
                testListOfNumbers.remove(i);
            } else {
                ++i;
            }
        }
        System.out.println(testListOfNumbers);

        ArrayList<Integer> listWithoutTwins = new ArrayList<>(testListOfNumbers.size());

        for (int number : testListOfNumbers) {
            if (!listWithoutTwins.contains(number)) {
                listWithoutTwins.add(number);
            }
        }
        System.out.println(listWithoutTwins);
    }
}