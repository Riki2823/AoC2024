package aoc2024;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    URL FIRST_PART_PATH;

    public Day5(URL filePath) {
        this.FIRST_PART_PATH = filePath;
    }

    public Integer day5() throws IOException {
        Pair<List<String>, List<String>> inputData = parseInput(this.FIRST_PART_PATH.getPath());
        Integer result = 0;
        for (String update : inputData.getRight()) {
            String[] individualUpdateValues = update.split(",");
            boolean isHealthy = true;
            for (int i = 0; i < individualUpdateValues.length; i++) {
                List<String> afterNumbersToBe = definePreviousOrPosteriorNumbersToBe(individualUpdateValues[i], inputData.getLeft(), false, individualUpdateValues);
                List<String> previousNumbersToBe = definePreviousOrPosteriorNumbersToBe(individualUpdateValues[i], inputData.getLeft(), true, individualUpdateValues);

                if (i != 0 && i != individualUpdateValues.length - 1) {
                    isHealthy = checktoRight(i, individualUpdateValues, afterNumbersToBe) && checktoLeft(i, individualUpdateValues, previousNumbersToBe);

                } else if (i == 0) {
                    isHealthy = checktoRight(i, individualUpdateValues, afterNumbersToBe);;
                } else if (i == individualUpdateValues.length - 1) {
                    isHealthy = checktoLeft(i, individualUpdateValues, previousNumbersToBe);
                }
            }
            if (isHealthy){
                result += Integer.parseInt(individualUpdateValues[(individualUpdateValues.length - 1) / 2]);
                System.out.println("Kurwa");
            }

        }

        return result;
    }

    private static boolean checktoRight(int i, String[] individualUpdateValues, List<String> afterNumbersToBe) {
        int j = i + 1;
        boolean isGood = true;
        while (j < individualUpdateValues.length) {
            if (!afterNumbersToBe.contains(individualUpdateValues[j])) {
                isGood = false;
                break;
            }
            j++;
        }
        return isGood;
    }

    private static boolean checktoLeft(int i, String[] individualUpdateValues, List<String> afterNumbersToBe) {
        int j = i - 1;
        boolean isGood = true;
        while (j > 0) {
            if (!afterNumbersToBe.contains(individualUpdateValues[j])) {
                isGood = false;
                break;
            }
            j++;
        }
        return isGood;
    }

    private List<String> definePreviousOrPosteriorNumbersToBe(String individualUpdateValue, List<String> rules, boolean isForPrevious, String[] individualUpdateValues) {
        List<String> result = new ArrayList<>();
        List<String> individualUpdateValuesList = parseArray(individualUpdateValues);
        for (String rule : rules) {
            String[] ruleNumbers = StringUtils.split(rule, "|");
            if (isForPrevious) {
                if (Integer.parseInt(ruleNumbers[1]) == Integer.parseInt(individualUpdateValue) && individualUpdateValuesList.contains(ruleNumbers[0])) {
                    result.add(ruleNumbers[0]);
                }
            } else {
                if (Integer.parseInt(ruleNumbers[0]) == Integer.parseInt(individualUpdateValue) && individualUpdateValuesList.contains(ruleNumbers[1])) {
                    result.add(ruleNumbers[1]);
                }
            }
        }
        return result;
    }

    private List<String> parseArray(String[] individualUpdateValues) {
        List<String> result = new ArrayList<>();
        for (String s : individualUpdateValues){
            result.add(s);
        }
        return result;
    }

    private Pair<List<String>, List<String>> parseInput(String path) throws IOException {
        List<String> pageOrderingRules = new ArrayList<>();
        List<String> updates = new ArrayList<>();
        String line;


        BufferedReader br = new BufferedReader(new FileReader(path));
        boolean isRules = true;
        while ((line = br.readLine()) != null) {
            if (line.equals("")) {
                isRules = false;
                continue;
            }
            if (isRules) {
                pageOrderingRules.add(line);
            } else {
                updates.add(line);
            }
        }
        br.close();
        return Pair.of(pageOrderingRules, updates);
    }

}
