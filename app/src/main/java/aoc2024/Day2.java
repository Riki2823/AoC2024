package aoc2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    URL FIRST_PART_PATH = getClass().getClassLoader().getResource("Day2/Day2Part1.txt");

    public void day2() throws IOException {
        String[] inputParsed = parseInput(FIRST_PART_PATH.getPath());
        List<String[]> input = new ArrayList<>();

        for (String s : inputParsed){
            input.add(StringUtils.split(s, " "));
        }
        int count = 0;
        for (String[] inputRow : input) {
            boolean isSave = true;
            boolean increasing = false;
            if (Integer.parseInt(inputRow[0]) < Integer.parseInt(inputRow[1])){
                increasing = true;
            } else if (Integer.parseInt(inputRow[0]) == Integer.parseInt(inputRow[1])) {
                continue;
            }
            for (int i = 0; i < inputRow.length; i++ ){
                if (i == inputRow.length - 1) {
                    continue;
                }
                Integer first = Integer.parseInt(inputRow[i]);
                Integer second = Integer.parseInt(inputRow[i+1]);

                if (increasing){
                    int difference = (first - second) * -1;
                    if (difference > 3 || difference <= 0){
                        isSave = false;
                        break;
                    }
                    if (first > second){
                        isSave = false;
                        break;
                    }
                }else{

                    int difference = (first - second);
                    if (difference > 3 || difference <= 0){
                        isSave = false;
                        break;
                    }
                    if (first < second){
                        isSave = false;
                        break;
                    }
                }
            }
            if (isSave){
                count++;
            }
        }
        System.out.println("Save lines -> " + count);

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
