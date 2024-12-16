package aoc2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day7 {
    URL FIRST_PART_PATH;

    public Day7(URL filePath) {
        this.FIRST_PART_PATH = filePath;
    }

    public void day7(boolean isSecondPart) throws IOException {
        Integer partNumber = isSecondPart ? 2 : 1;
        System.out.println("\nDAY 7 PART " + partNumber + " HERE ----------------->");
        List<Pair<Long, List<Integer>>> inputParsed = parseInput(FIRST_PART_PATH.getPath());
        long sum = 0L;
        for (Pair<Long, List<Integer>> row : inputParsed) {
            int numberOfNumbers = row.getRight().size();
            Long result = row.getLeft();

            if (isSecondPart){
                int combinationBase3 = (int) Math.pow(3, numberOfNumbers - 1);
                List<String[]> possibleOperationCombiList = new ArrayList<>();
                for (int i = 0; i < combinationBase3; i++){
                    String operations = String.valueOf(asBase3(i));
                    if (operations.length() < numberOfNumbers -1){
                        while (operations.length() != numberOfNumbers -1){
                            operations = addCharToString(operations, '0', 0);
                        }
                    }
                    possibleOperationCombiList.add(operations.split(""));
                }
                for (String[] strings : possibleOperationCombiList) {
                    List<Integer> inputNumbersMod = new ArrayList<>();
                    int indexNumbers = 0;
                    for (int j = 0; j < strings.length; j++) {
                        indexNumbers = j;
                        if (strings[j].equals("2")) {
                            String newNuber = String.valueOf(row.getRight().get(j)) + String.valueOf(row.getRight().get(j + 1));
                            indexNumbers ++;
                            inputNumbersMod.add(Integer.parseInt(newNuber));
                        } else if (j == strings.length-1){
                            inputNumbersMod.add(row.getRight().get(j));
                            inputNumbersMod.add(row.getRight().get(j+1));
                        }else{
                            inputNumbersMod.add(row.getRight().get(j));

                        }
                    }
                    Long resolution = resolve(strings, inputNumbersMod);
                    if (resolution.equals(result)){
                        sum += resolution;
                        break;
                    }
                }
            }else {
                int numberPossibleOperationCombination = (int) Math.pow(2, numberOfNumbers-1);
                List<String[]> possibleOperationCombiList = new ArrayList<>();
                for (int i = 0; i < numberPossibleOperationCombination; i++){
                    String operations = Integer.toBinaryString(i);
                    if (operations.length() < numberOfNumbers -1){
                        while (operations.length() != numberOfNumbers -1){
                            operations = addCharToString(operations, '0', 0);
                        }
                    }
                    possibleOperationCombiList.add(operations.split(""));
                }
                for (String[] operators : possibleOperationCombiList){
                    Long resolution = resolve(operators, row.getRight());
                    if (resolution.equals(result)){
                        sum += resolution;
                        break;
                    }
                }
            }
        }
        System.out.println("Result of sum of all possible operation is -----> " + sum);
    }

    public static long asBase3(int num) {
        long ret = 0, factor = 1;
        while (num > 0) {
            ret += num % 3 * factor;
            num /= 3;
            factor *= 10;
        }
        return ret;
    }

    private Long resolve(String[] operators, List<Integer> numbers) {
        Long result = Long.valueOf(numbers.get(0));
        int indexNumbers = 1;
        if (numbers.size() == 1){
            return Long.valueOf(numbers.get(0));
        }
        for (String operator : operators) {
            if (operator.equals("0")) {
                result *= numbers.get(indexNumbers);
                indexNumbers++;
            }
            if (operator.equals("1")) {
                result += numbers.get(indexNumbers);
                indexNumbers++;
            }
        }

        return result;
    }

    public static String addCharToString(String str, char c, int pos) {
        StringBuilder stringBuffer = new StringBuilder(str);
        stringBuffer.insert(pos, c);
        return stringBuffer.toString();
    }

    private List<Pair<Long, List<Integer>>> parseInput(String path) throws IOException {
        List<Pair<Long, List<Integer>>> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        while ((line = br.readLine()) != null) {
            String[] lineSplit = line.split(":");
            String[] valuesOperations = lineSplit[1].split(" ");
            List<Integer> valuesOperationsInt = new ArrayList<>();
            for (int i = 1; i < valuesOperations.length; i++) {
                valuesOperationsInt.add(Integer.parseInt(valuesOperations[i]));
            }
            result.add(Pair.of(Long.parseLong(lineSplit[0]), valuesOperationsInt));
        }
        br.close();
        return result;
    }
}
