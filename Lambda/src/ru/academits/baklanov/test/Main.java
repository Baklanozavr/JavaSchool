package ru.academits.baklanov.test;

import ru.academits.baklanov.tasks.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String[] names = {"Иван", "Никифор", "Никодим", "Пётр", "Александр", "Игорь", "Андрей", "Павел", "Ярослав", "Иосиф"};
        ArrayList<Person> people = new ArrayList<>();

        int numberOfPeople = 20;
        int maxAge = 100;

        for (int i = 0; i < numberOfPeople; ++i) {
            String name = names[(int) (Math.random() * names.length)];
            int age = (int) (Math.random() * maxAge);

            people.add(new Person(name, age));

            System.out.print(people.get(i).getName());
            System.out.println(people.get(i).getAge());
        }

        List<String> uniqueNames = people.stream().map(Person::getName).distinct().collect(Collectors.toList());

        String stringOfUniqueNames = uniqueNames.stream().collect(Collectors.joining(", ","Имена: ","."));
        System.out.println(stringOfUniqueNames);

        List<Person> peopleUnder18 = people.stream().filter(p -> p.getAge() < 18).collect(Collectors.toList());
        peopleUnder18.stream().mapToInt(Person::getAge).average().ifPresent(System.out::println);


    }
}