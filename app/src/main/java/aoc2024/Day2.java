package aoc2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day2 {
    URL FIRST_PART_PATH = getClass().getClassLoader().getResource("Day2/Day2Part1.txt");

    public void day2(boolean isSecondPart) throws IOException {
        String part = isSecondPart ? "2" : "1";
        System.out.println("\nDAY 2 PART "+ part + " HERE ----------------->");
        String[] inputParsed = parseInput(FIRST_PART_PATH.getPath());
        List<String[]> input = new ArrayList<>();

        for (String s : inputParsed) {
            input.add(StringUtils.split(s, " "));
        }
        int count = 0;
        for (String[] inputRow : input) {
            boolean isSave = true;

            isSave = isSaveRow(inputRow, isSecondPart,isSave);
            if (isSave) {
                count++;
            } else if (isSecondPart){
                for (int i = 0; i < inputRow.length; i++) {
                    int value = Integer.parseInt(inputRow[i]);
                    inputRow[i] = null;
                    isSave = isSaveRow(inputRow, isSecondPart,true);
                    inputRow[i] = String.valueOf(value);
                    if (isSave) {
                        count++;
                        break;
                    }
                }
            }
        }
        System.out.println("Save lines -> " + count);

    }

    private static boolean isSaveRow(String[] inputRow, boolean isSecondPart, boolean isSave) {
        List<Integer> items = new ArrayList<>();
        for(String s : inputRow){
            if (s == null){
                continue;
            }
            items.add(Integer.parseInt(s));
        }
        boolean increasing = false;
        
        if (items.get(0) < items.get(1)) {
            increasing = true;
        } else if (Objects.equals(items.get(0), items.get(1))) {
            if (items.get(0) + 1 == items.get(2)) {
                increasing = true;
            } else if (items.get(0) - 1 != items.get(2) && !isSecondPart) {
                return false;
            }
        }
        try {
            for (int i = 0; i < inputRow.length; i++) {
                Integer second = null;
                if (i == inputRow.length - 1 | inputRow[i] == null) {
                    continue;
                }

                if (inputRow[i + 1] == null) {
                    if (i != inputRow.length - 2) {
                        second = Integer.parseInt(inputRow[i + 2]);
                    } else if (i == inputRow.length - 2) {
                       continue;
                    }
                } else {
                    second = Integer.parseInt(inputRow[i + 1]);
                }
                Integer first = Integer.parseInt(inputRow[i]);


                if (increasing) {
                    int difference = (first - second) * -1;
                    if (difference > 3 || difference <= 0) {
                        isSave = false;
                        break;
                    }

                    if (first > second) {
                        isSave = false;
                        break;
                    }

                } else {
                    int difference = (first - second);
                    if (difference > 3 || difference <= 0) {
                        isSave = false;
                        break;
                    }
                    if (first < second) {
                        isSave = false;
                        break;
                    }
                }
            }
        } catch (Exception e ){
            System.out.println(e);
        }

        return isSave;
    }

    private String[] parseInput(String part1ProblemInput) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(part1ProblemInput));
        String line;

        int rowCount = 0;

        while ((line = br.readLine()) != null) {
            rowCount++;
        }
        br.close();
        String[] result = new String[rowCount];
        int index = 0;
        br = new BufferedReader(new FileReader(part1ProblemInput));
        while ((line = br.readLine()) != null) {
            result[index] = line;

            index++;
        }
        br.close();

        return result;
    }
}
