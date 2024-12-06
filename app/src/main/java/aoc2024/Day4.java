package aoc2024;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {
    private final URL FIRST_PART_PATH;

    public Day4(URL pathFile){
        this.FIRST_PART_PATH = pathFile;
    }
    public Integer day4(boolean isSecondPart) throws IOException {
        String partNumber = isSecondPart ? "2" : "1";
        System.out.println("\nDAY 4 PART " + partNumber + " HERE ----------------->");
        List<String[]> inputParsed = parseInput(FIRST_PART_PATH.getPath());
        Integer ret = null;
        if (isSecondPart) {
            ret = part2(inputParsed);
        } else {
            ret =part1(inputParsed);
        }
        return ret;
    }

    private Integer part2(List<String[]> inputParsed) {
        int result = 0;
        int rows = inputParsed.size();
        int colums = inputParsed.get(0).length;


        String[] chars = {"M", "A", "S"};
        Map<Integer, List<Integer[]>> mapStorePositions = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                if (!inputParsed.get(i)[j].equals(chars[0])) {
                    continue;
                }

                String[] directions = {"UR", "UL", "DR", "DL"};
                for (String dir : directions) {
                    boolean validPattern = true;
                    Integer char2Positions = null;
                    Integer[] char1Positions = {i, j};
                    for (int k = 0; k < chars.length; k++) {
                        int newRow = i, newCol = j;


                        switch (dir) {
                            case "UR" -> {
                                newRow -= k;
                                newCol += k;
                            }
                            case "UL" -> {
                                newRow -= k;
                                newCol -= k;
                            }
                            case "DR" -> {
                                newRow += k;
                                newCol += k;
                            }
                            case "DL" -> {
                                newRow += k;
                                newCol -= k;
                            }
                        }


                        if (!isWithinBounds(newRow, newCol, rows, colums) ||
                                !inputParsed.get(newRow)[newCol].equals(chars[k])) {
                            validPattern = false;
                            break;
                        }else if (k == 1){
                            char2Positions  = newRow + newCol;
                        }
                    }

                    if (validPattern) {
                        if (!mapStorePositions.containsKey(char2Positions)){
                            List<Integer[]> aux = new ArrayList<>();
                            aux.add(char1Positions);
                            mapStorePositions.put(char2Positions, aux);
                        }else{
                            mapStorePositions.get(char2Positions).add(char1Positions);
                        }
                    }
                }
            }
        }

        for (Map.Entry<Integer, List<Integer[]>> e: mapStorePositions.entrySet()){

            if (e.getValue().size()%2 == 0){
                result += e.getValue().size()/2;
            }else {
                result += (e.getValue().size()-1)/2;
            }

        }
        System.out.println("Number of X-MAS -----> " + result);
        return result;
    }

    private Integer part1(List<String[]> inputParsed) {
        int result = 0;

        int rows = inputParsed.size();
        int colums = inputParsed.get(0).length;


        String[] chars = {"X", "M", "A", "S"};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                if (!inputParsed.get(i)[j].equals(chars[0])) {
                    continue;
                }


                String[] directions = {"R", "L", "U", "D", "UR", "UL", "DR", "DL"};
                for (String dir : directions) {
                    boolean validPattern = true;

                    for (int k = 0; k < chars.length; k++) {
                        int newRow = i, newCol = j;


                        switch (dir) {
                            case "R" -> newCol += k;
                            case "L" -> newCol -= k;
                            case "U" -> newRow -= k;
                            case "D" -> newRow += k;
                            case "UR" -> {
                                newRow -= k;
                                newCol += k;
                            }
                            case "UL" -> {
                                newRow -= k;
                                newCol -= k;
                            }
                            case "DR" -> {
                                newRow += k;
                                newCol += k;
                            }
                            case "DL" -> {
                                newRow += k;
                                newCol -= k;
                            }
                        }


                        if (!isWithinBounds(newRow, newCol, rows, colums) ||
                                !inputParsed.get(newRow)[newCol].equals(chars[k])) {
                            validPattern = false;
                            break;
                        }
                    }

                    if (validPattern) {
                        result++;
                    }
                }
            }
        }

        System.out.println("Number of XMAS -----> " + result);
        return result;
    }


    private List<String[]> parseInput(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;

        int rowCount = 0;

        while ((line = br.readLine()) != null) {
            rowCount++;
        }
        br.close();
        List<String[]> result = new ArrayList<>(rowCount);

        br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            result.add(line.split(""));
        }
        br.close();

        return result;
    }

    private boolean isWithinBounds(int i, int j, int rows, int cols) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

}
