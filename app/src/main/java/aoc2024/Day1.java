package aoc2024;

import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.units.qual.A;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class Day1 {
    URL FIRST_PART_PATH = getClass().getClassLoader().getResource("Day1/Day1Part1.txt");


    public void day1() throws IOException {
        Integer[][] parsedInput = parseInput(FIRST_PART_PATH.getPath());

        Integer[] columnLeft = parsedInput[0];
        Integer[] columnRight = parsedInput[1];

        quickSort(columnLeft, 0, columnLeft.length - 1);
        quickSort(columnRight, 0, columnRight.length - 1);

        List<Integer> distanceNumbers = new ArrayList<>();
        Map<Integer, Integer> similarities = new HashMap<>();
        for (int i = 0; i < columnLeft.length; i++){
            Integer difference = columnLeft[i] >= columnRight[i] ? columnLeft[i] - columnRight[i] : columnRight[i] - columnLeft[i];
            distanceNumbers.add(difference);
                int counter = 0;
                for (int j = 0 ; j < columnRight.length;j++){
                    if (columnRight[j].equals(columnLeft[i])){
                        if (similarities.containsKey(columnLeft[i])){
                            similarities.put(columnLeft[i], similarities.get(columnLeft[i]) +1);
                        }else{
                            counter++;
                        }
                    }
                }
                if (!similarities.containsKey(columnLeft[i])){
                    similarities.put(columnLeft[i], counter);
                }
        }

        AtomicReference<Integer> resultPart1 = new AtomicReference<>(0);
        AtomicReference<Integer> resultPart2 = new AtomicReference<>(0);
        distanceNumbers.forEach(i ->{
            resultPart1.updateAndGet(v -> v + i);
        });
        similarities.forEach((value1, value2) ->{
            resultPart2.updateAndGet(v -> v + (value1*value2));
        });

        System.out.println("This is the result of the part 1: " + resultPart1);
        System.out.println("This is the result of the part 2: " + resultPart2);





    }

    private Integer[][] parseInput(String part1ProblemInput) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(part1ProblemInput));
        String line;
        int rowCount = 0;

        while ((line = br.readLine()) != null) {
            rowCount++;
        }
        br.close();

        Integer[] firstColumn = new Integer[rowCount];
        Integer[] secondColumn = new Integer[rowCount];

        br = new BufferedReader(new FileReader(part1ProblemInput));
        int index = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 2) {
                firstColumn[index] = Integer.parseInt(parts[0]);
                secondColumn[index] = Integer.parseInt(parts[1]);
            }
            index++;
        }
        br.close();

        return new Integer[][]{firstColumn, secondColumn};
    }

    public static void quickSort(Integer[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    public static int partition(Integer[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void swap(Integer[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
