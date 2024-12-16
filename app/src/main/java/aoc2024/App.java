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

        Day5 day5 = new Day5(Day5.class.getClassLoader().getResource("Day5/Day5Part1.txt"));
        day5.day5(false);
        day5.day5(true);

        //Day6 day6 = new Day6(Day6.class.getClassLoader().getResource("Day6/Day6Part1.txt"));
        //day6.day6(false);
        //day6.day6(true);

        Day7 day7 = new Day7(Day7.class.getClassLoader().getResource("Day7/Day7Part1.txt"));
        //day7.day7(false);
        day7.day7(true);
    }
}
