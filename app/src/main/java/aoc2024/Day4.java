package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day4 {
    URL FIRST_PART_PATH = getClass().getClassLoader().getResource("Day4/Day4Part1.txt");

    public void day4() throws IOException {
        List<String[]> inputParsed = parseInput(FIRST_PART_PATH.getPath());
        Integer result = 0;

        Integer rows = inputParsed.size();
        Integer colums = inputParsed.get(0).length;

        String[] chars = {"X", "M", "A", "S"};

        for (int i = 0; i < rows; i++){
            //0, 0 means UpRight corner
            //0,1 means UpLeft corner
            //1, 0 means DownLeft corner
            //1, 1 means DownRight corner
            //NULL, 0 means nonFirstOrLastRowButFirstColumn
            //NULL, 1 means nonFirstOrLastRowButFirstColumn
            //1, NULL means nonFirstOrLastColumnButLastRow
            //0, NULL means nonFirstOrLastColumnbutFIrstRow
            //
            List<Integer> corners = new ArrayList<>();
            setCorner(i, corners, rows, 0);

            String[] aux= inputParsed.get(i);
            for (int j = 0; j < colums; j++){
                List<String> possibleDirrections = new ArrayList<>();
                setCorner(j, corners, colums, 1);

                if (corners.get(0) == null && corners.get(1) == null){
                    if (aux[j].equals(chars[0])){
                        possibleDirrections = definePossibleDirrections(null, null, chars, inputParsed, i, j);
                    }else {
                        continue;
                    }
                }
                if (corners.get(0) == null){

                }

            }
        }
    }

    private List<String> definePossibleDirrections(Integer row, Integer column, String[] chars, List<String[]> inputParsed, Integer rowNumber, Integer columnNumber) {
        List<String> ret = new ArrayList<>();
        if (row == null && column ==null){
            String[] dirrections = {"R", "L", "U", "D", "UR", "UL", "DR", "DL"};
            ret = switchDirrections(dirrections, inputParsed, chars[1], rowNumber, columnNumber);
        }
        if (row == 0 && column == null){

        }
        return ret;
    }

    private List<String> switchDirrections(String[] dirrections, List<String[]> inputParsed, String charToCheck, Integer rowNumber, Integer columnNumber) {
        List<String> ret = new ArrayList<>();
        for (String s: dirrections){
            switch (s){
                case "R" -> {
                    if (inputParsed.get(rowNumber)[columnNumber+1].equals(charToCheck)){
                        ret.add("R");
                    }
                }
                case "L" -> {
                    if (inputParsed.get(rowNumber)[columnNumber-1].equals(charToCheck)) {
                        ret.add("L");
                    }
                }
                case "U" ->{
                    if (inputParsed.get(rowNumber-1)[columnNumber].equals(charToCheck)) {
                        ret.add("U");
                    }
                }
                case "D" -> {
                    if (inputParsed.get(rowNumber+1)[columnNumber].equals(charToCheck)){
                    ret.add("D");
                    }
                }
                case "UR" -> {
                    if (inputParsed.get(rowNumber-1)[columnNumber+1].equals(charToCheck)) {
                        ret.add("UR");
                    }
                }
                case "UL" -> {
                    if (inputParsed.get(rowNumber-1)[columnNumber-1].equals(charToCheck)){
                        ret.add("UL");
                    }
                }
                case "DR" -> {
                    if (inputParsed.get(rowNumber+1)[columnNumber+1].equals(charToCheck)){
                        ret.add("DR");
                    }
                }
                case "DL" -> {
                    if (inputParsed.get(rowNumber+1)[columnNumber-1].equals(charToCheck)){
                        ret.add("DL");
                    }
                }
            }
        }

        return ret;
    }

    private static void setCorner(int i, List<Integer> corners, Integer rows, Integer squarePostion) {
        if (i == 0){
            corners.add(squarePostion, 0);;
        } else if (i == rows -1) {
            corners.add(squarePostion, 1);
        }else {
            corners.add(squarePostion, null);
        }
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
        int index = 0;
        br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            result.add(line.split(""));
            index++;
        }
        br.close();

        return result;
    }

}
