package aoc2024;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Day1 day1 = new Day1();
        day1.day1();

        Day2 day2 = new Day2();
        day2.day2(false);
        day2.day2(true);

        Day3 day3 = new Day3();
        day3.day3(false);
        day3.day3(true);

        Day4 day4 = new Day4(Day4.class.getClassLoader().getResource("Day4/Day4Part1.txt"));
        day4.day4(false);
        day4.day4(true);
    }
}
