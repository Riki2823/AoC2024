package aoc2024;

import com.google.common.collect.Lists;
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
            List<String>individualUpdateValues = parseArray(update.split(","));
            boolean isHealthy = true;
            for (int i = 0; i < individualUpdateValues.size(); i++) {
                Pair<List<String>, List<String>> previousAndPosteriorNumbersToBe = definePreviousAndPosteriorNumbersToBe(individualUpdateValues.get(i), inputData.getLeft(), individualUpdateValues, i);

                for (String s : previousAndPosteriorNumbersToBe.getLeft()){
                    boolean findIt = false;
                    boolean findItOposite = false;
                    for (int j = i-1; j >= 0; j--){
                        if (individualUpdateValues.get(j).equals(s)){
                            findIt= true;
                            break;
                        }
                    }
                    for (int j = i+1; j < individualUpdateValues.size(); j++){
                        if (individualUpdateValues.get(j).equals(s)) {
                            findItOposite = true;
                            break;
                        }
                    }
                    if (!findItOposite){
                        isHealthy = findIt;
                    }else{
                        isHealthy = false;
                        break;
                    }
                }


                for (String s : previousAndPosteriorNumbersToBe.getRight()){
                    boolean findIt = false;
                    boolean findItOposite = false;
                    for (int j = i+1; j < individualUpdateValues.size(); j++){
                        if (individualUpdateValues.get(j).equals(s)){
                            findIt= true;
                            break;
                        }
                    }
                    for (int j = i-1; j >=0; j--){
                        if (individualUpdateValues.get(j).equals(s)) {
                            findItOposite = true;
                            break;
                        }
                    }
                    if (!findItOposite){
                        isHealthy = findIt;
                    }else{
                        isHealthy = false;
                        break;
                    }
                }

                if (!isHealthy){
                    break;
                }

            }
            if (isHealthy) {
                result += Integer.parseInt(individualUpdateValues.get((individualUpdateValues.size() - 1) / 2));
            }

        }
        System.out.println("Result of the updates -----> " + result);
        return result;
    }


    private Pair<List<String>, List<String>>definePreviousAndPosteriorNumbersToBe(String individualUpdateValue, List<String> rules, List<String> individualUpdateValues, int i) {
        List<String> previousNumbers = new ArrayList<>();
        List<String> posteriorNumbers = new ArrayList<>();
        for (String rule : rules) {
            String[] ruleNumbers = StringUtils.split(rule, "|");
            if (Integer.parseInt(ruleNumbers[1]) == Integer.parseInt(individualUpdateValue) && individualUpdateValues.contains(ruleNumbers[0]) && i != 0) {
                previousNumbers.add(ruleNumbers[0]);
            }
            if (Integer.parseInt(ruleNumbers[0]) == Integer.parseInt(individualUpdateValue) && individualUpdateValues.contains(ruleNumbers[1]) && i != individualUpdateValues.size()-1) {
                posteriorNumbers.add(ruleNumbers[1]);
            }
        }
        return Pair.of(previousNumbers, posteriorNumbers);
    }

    private List<String> parseArray(String[] individualUpdateValues) {
        List<String> result = new ArrayList<>();
        for (String s : individualUpdateValues) {
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
