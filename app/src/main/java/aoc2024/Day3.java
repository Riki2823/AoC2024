package aoc2024;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    URL FIRST_PART_PATH = getClass().getClassLoader().getResource("Day3/Day3Part1.txt");

    String regexGetMultiplicationNumber = "\\d+";
    String regexGetMultiplicationsValid = "mul\\(\\d+,\\d+\\)";
    String regexGetMultiplicationsValidInstructons = "mul\\(\\d+\\,\\d+\\)|do\\(\\)|don't\\(\\)";
    public void day3(boolean isSecondPart) throws IOException {
        //String inputParsed = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        String part = isSecondPart ? "2" : "1";
        System.out.println("\nDAY 2 PART "+ part + " HERE ----------------->");
        String inputParsed = parseInput(FIRST_PART_PATH.getPath());
        Pattern validMultiplicationPattern = Pattern.compile(isSecondPart ? regexGetMultiplicationsValidInstructons : regexGetMultiplicationsValid);
        Pattern multiplicationNumbersPattern = Pattern.compile(regexGetMultiplicationNumber);
        Matcher validMultiplicationMatcher = validMultiplicationPattern.matcher(inputParsed);
        Integer result = 0;
        boolean status = true;
        while (validMultiplicationMatcher.find()) {
            String multiplication = validMultiplicationMatcher.group();
            Matcher multiplicationNumersMatcher = multiplicationNumbersPattern.matcher(multiplication);
            int [] numbers = new int[2];
            int index = 0;
            if (validMultiplicationMatcher.group().equals("do()")){
                status = true;
                continue;
            } else if (validMultiplicationMatcher.group().equals("don't()")) {
                status = false;
                continue;
            }
            while (multiplicationNumersMatcher.find()){

                if (status){
                    numbers[index++] = Integer.parseInt(multiplicationNumersMatcher.group());
                }
            }
            result += numbers[0] * numbers[1];
        }
        System.out.println("Result multiplication -----> " + result);
    }

    private String parseInput(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
}