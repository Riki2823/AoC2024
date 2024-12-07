package aoc2024;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Day5 {
    URL FIRST_PART_PATH;

    public Day5(URL filePath) {
        this.FIRST_PART_PATH = filePath;
    }

    public Integer day5(boolean isSecondPart) throws IOException {
        int part = isSecondPart ? 2 : 1;
        System.out.println("\nDAY 5 PART " + part + " HERE ----------------->");
        Pair<List<String>, List<String>> inputData = parseInput(this.FIRST_PART_PATH.getPath());
        int result = 0;
        int result2 = 0;
        for (String update : inputData.getRight()) {
            Boolean secondCheck = isSecondPart ? true : null;
            List<String>individualUpdateValues = parseArray(update.split(","));
            Map<String, Pair<List<String>, List<String>>> rulesPerValue = definePreviousAndPosteriorNumbersToBe(inputData.getLeft(), individualUpdateValues);

            Pair<Integer, Boolean> isHealthy = new MutablePair<>(null, false);
            isHealthy = checkValidUpdate(individualUpdateValues, isHealthy, rulesPerValue);
            if (isHealthy.getRight()) {
                result += Integer.parseInt(individualUpdateValues.get((individualUpdateValues.size() - 1) / 2));
            } else {
                if (secondCheck != null && !isHealthy.getRight()){
                    List<String> sortedUpdate = sortUpdate(individualUpdateValues, rulesPerValue);
                    if (sortedUpdate != null){
                        isHealthy = checkValidUpdate(sortedUpdate, isHealthy, rulesPerValue);
                        result2 += Integer.parseInt(sortedUpdate.get((sortedUpdate.size() - 1) / 2));
                    }
                }
            }


        }

        System.out.println("Result of the updates -----> " + (isSecondPart ? result2 : result));
        return result;
    }

    private List<String> sortUpdate(List<String> individualUpdateValues, Map<String, Pair<List<String>, List<String>>> rulesPerValue) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        for (String value : individualUpdateValues) {
            graph.put(value, new ArrayList<>());
            inDegree.put(value, 0);
        }

        for (Map.Entry<String, Pair<List<String>, List<String>>> entry : rulesPerValue.entrySet()) {
            String key = entry.getKey();
            List<String> posteriorValues = entry.getValue().getRight();

            for (String posterior : posteriorValues) {
                graph.get(key).add(posterior);
                inDegree.put(posterior, inDegree.get(posterior) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();

        for (String value : inDegree.keySet()) {
            if (inDegree.get(value) == 0) {
                queue.add(value);
            }
        }

        List<String> sortedList = new ArrayList<>();

        while (!queue.isEmpty()) {
            String current = queue.poll();
            sortedList.add(current);

            for (String neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sortedList.size() == individualUpdateValues.size()) {
            return sortedList;
        }
        else {
            return null;
        }
    }


    private Pair<Integer, Boolean> checkValidUpdate(List<String> individualUpdateValues, Pair<Integer, Boolean> isHealthy, Map<String,Pair<List<String>, List<String>>> rules) {

        for (int i = 0; i < individualUpdateValues.size(); i++) {
            Pair<List<String>, List<String>> previousAndPosteriorNumbersToBe = rules.get(individualUpdateValues.get(i));
            for (int k = 0; k <  previousAndPosteriorNumbersToBe.getLeft().size(); k++){
                boolean findIt = false;
                boolean findItOposite = false;
                for (int j = i-1; j >= 0; j--){
                    if (individualUpdateValues.get(j).equals(previousAndPosteriorNumbersToBe.getLeft().get(k))){
                        findIt= true;
                        break;
                    }
                }
                for (int j = i+1; j < individualUpdateValues.size(); j++){
                    if (individualUpdateValues.get(j).equals(previousAndPosteriorNumbersToBe.getLeft().get(k))) {
                        return new MutablePair<>(i, false);
                    }
                }
                if (!findItOposite){
                    isHealthy.setValue(findIt);
                }else{
                    isHealthy.setValue(false);
                    break;
                }
            }


            for (int k = 0; k < previousAndPosteriorNumbersToBe.getRight().size(); k++){
                boolean findIt = false;
                boolean findItOposite = false;
                for (int j = i+1; j < individualUpdateValues.size(); j++){
                    if (individualUpdateValues.get(j).equals(previousAndPosteriorNumbersToBe.getRight().get(k))){
                        findIt= true;
                        break;
                    }
                }
                for (int j = i-1; j >=0; j--){
                    if (individualUpdateValues.get(j).equals(previousAndPosteriorNumbersToBe.getRight().get(k))) {
                        return new MutablePair<>(i, false);
                    }
                }
                if (!findItOposite){
                    isHealthy.setValue(findIt);
                }else{
                    isHealthy.setValue(false);
                    break;
                }
            }

            if (!isHealthy.getRight()){
                break;
            }

        }
        return isHealthy;
    }


    private Map<String,Pair<List<String>, List<String>>> definePreviousAndPosteriorNumbersToBe(List<String> rules, List<String> values) {
        Map<String,Pair<List<String>, List<String>>> result = new HashMap<>();
        for (String value : values){
            List<String> previousRules = new ArrayList<>();
            List<String> posterirorRules = new ArrayList<>();
            for (String rule: rules){
                String[] ruleSplit = StringUtils.split(rule, "|");
                if (value.equals(ruleSplit[0]) && values.contains(ruleSplit[1])){
                    posterirorRules.add(ruleSplit[1]);
                }
                if (value.equals(ruleSplit[1]) && values.contains(ruleSplit[0])){
                    previousRules.add(ruleSplit[0]);
                }
            }
            result.put(value, Pair.of(previousRules, posterirorRules));
        }
        return result;
    }

    private List<String> parseArray(String[] individualUpdateValues) {
        return new ArrayList<>(Arrays.asList(individualUpdateValues));
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
