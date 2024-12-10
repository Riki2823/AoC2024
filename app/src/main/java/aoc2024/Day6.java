package aoc2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    URL FIRST_PART_PATH;

    public Day6(URL filePath) {
        this.FIRST_PART_PATH = filePath;
    }

    private final String GUARD = "^";
    private final String EMPTYPLACE = ".";
    private final String OBSATCLE = "#";
    private final String VISITEDPOSITION = "X";
    private final String CUSTOMOBSTACLE = "0";

    public void day6(boolean isSecondPart) throws IOException {
        Integer part = isSecondPart ? 2 : 1;
        System.out.println("\nDAY 6 PART " + part + " HERE ----------------->");
        String[][] lab = parseInput(FIRST_PART_PATH.getPath());
        int xGuard = 0;
        int yGuard = 0;

        for (int x = 0; x < lab.length; x++) {
            for (int y = 0; y < lab[x].length; y++) {
                if (lab[x][y].equals(GUARD)) {
                    xGuard = x;
                    yGuard = y;
                }
            }
        }
        int counter = 0;
        Guard guard = new Guard(xGuard, yGuard, "N", xGuard, yGuard);
        boolean walk = true;
        while (walk) {
            Integer resultMovement = getNextCounter(guard, lab, false);
            if (resultMovement == null) {
                counter += 1;
                walk = false;
            } else if (resultMovement == 1) {
                counter += 1;
            }
        }

        int possibleLoops = 0;
        if (isSecondPart) {
            List<Pair<Integer, Integer>> positionChecked = new ArrayList<>();

            guard.setPositionX(guard.initialX);
            guard.setPositionY(guard.initialY);

            for (int i = 0; i < counter; i++) {
                guard.setPositionX(guard.initialX);
                guard.setPositionY(guard.initialY);
                guard.setDirection("N");
                lab = parseInput(FIRST_PART_PATH.getPath());

                Integer resultMovment = 0;
                boolean defining = true;
                while (defining){
                    resultMovment = getNextCounter(guard, lab, isSecondPart);
                    if (resultMovment == null) {
                        continue;
                    }
                    if (!positionChecked.contains(Pair.of(guard.getPositionX(), guard.getPositionY()))){
                        positionChecked.add(Pair.of(guard.getPositionX(), guard.getPositionY()));
                        lab[guard.getPositionX()][guard.getPositionY()] = CUSTOMOBSTACLE;
                        defining = false;
                    }
                    if (positionChecked.size() == counter-1){
                        break;
                    }
                }


                guard.setPositionX(guard.initialX);
                guard.setPositionY(guard.initialY);
                guard.setDirection("N");
                Integer startPointVisited = 0;
                Integer iteration = 0;
                walk = true;
                while(walk){
                    resultMovment = getNextCounter(guard, lab, false);
                    if (resultMovment == null) {
                        walk = false;
                    }
                    if (guard.getPositionY().equals(guard.getInitialY()) && guard.getPositionX().equals(guard.getInitialX())){
                        startPointVisited += 1;
                    }
                    if (startPointVisited.equals(10000)){
                        walk = false;
                        possibleLoops +=1;
                    }
                    if (iteration.equals(10000)){
                        walk = false;
                        possibleLoops +=1;
                    }
                    iteration++;
                }
            }
            System.out.println("This is the number of possibles loops ---> " + possibleLoops);
        }

        System.out.println("This is the name of empty space we have ---> " + counter);
    }

    private Integer getNextCounter(Guard guard, String[][] lab, boolean isSecondPart) {
        String nextPositionChar;
        switch (guard.getDirection()) {
            case "N" -> {
                if (guard.getPositionX() == 0) {
                    return null;
                }
                nextPositionChar = lab[guard.getPositionX() - 1][guard.getPositionY()];
                if (nextPositionChar.equals(VISITEDPOSITION)) {
                    guard.setPositionX(guard.getPositionX() - 1);
                    return 0;
                }
                if (nextPositionChar.equals(EMPTYPLACE) || nextPositionChar.equals(GUARD)) {
                    guard.setPositionX(guard.getPositionX() - 1);
                    lab[guard.getPositionX()][guard.getPositionY()] = "X";
                    return 1;
                }
                if (nextPositionChar.equals(OBSATCLE) || nextPositionChar.equals(CUSTOMOBSTACLE)) {
                    guard.setDirection("E");
                    return isSecondPart ? Integer.valueOf(2) : getNextCounter(guard, lab, false);
                }
            }
            case "S" -> {
                if (guard.getPositionX() == lab.length - 1) {
                    return null;
                }
                nextPositionChar = lab[guard.getPositionX() + 1][guard.getPositionY()];
                if (nextPositionChar.equals(VISITEDPOSITION)) {
                    guard.setPositionX(guard.getPositionX() + 1);
                    return 0;
                }
                if (nextPositionChar.equals(EMPTYPLACE) || nextPositionChar.equals(GUARD)) {
                    guard.setPositionX(guard.getPositionX() + 1);
                    lab[guard.getPositionX()][guard.getPositionY()] = "X";
                    return 1;
                }
                if (nextPositionChar.equals(OBSATCLE) || nextPositionChar.equals(CUSTOMOBSTACLE)) {
                    guard.setDirection("W");
                    return isSecondPart ? Integer.valueOf(2) : getNextCounter(guard, lab, false);
                }
            }
            case "E" -> {
                if (guard.getPositionY() == lab[0].length - 1) {
                    return null;
                }
                nextPositionChar = lab[guard.getPositionX()][guard.getPositionY() + 1];
                if (nextPositionChar.equals(VISITEDPOSITION)) {
                    guard.setPositionY(guard.getPositionY() + 1);
                    return 0;
                }
                if (nextPositionChar.equals(EMPTYPLACE) || nextPositionChar.equals(GUARD)) {
                    guard.setPositionY(guard.getPositionY() + 1);
                    lab[guard.getPositionX()][guard.getPositionY()] = "X";
                    return 1;
                }
                if (nextPositionChar.equals(OBSATCLE) || nextPositionChar.equals(CUSTOMOBSTACLE))  {
                    guard.setDirection("S");
                    return isSecondPart ? Integer.valueOf(2) : getNextCounter(guard, lab, false);
                }
            }
            case "W" -> {
                if (guard.getPositionY() == 0) {
                    return null;
                }
                nextPositionChar = lab[guard.getPositionX()][guard.getPositionY() - 1];
                if (nextPositionChar.equals(VISITEDPOSITION)) {
                    guard.setPositionY(guard.getPositionY() - 1);
                    return 0;
                }
                if (nextPositionChar.equals(EMPTYPLACE) || nextPositionChar.equals(GUARD)) {
                    guard.setPositionY(guard.getPositionY() - 1);
                    lab[guard.getPositionX()][guard.getPositionY()] = "X";
                    return 1;

                }
                if (nextPositionChar.equals(OBSATCLE) || nextPositionChar.equals(CUSTOMOBSTACLE)) {
                    guard.setDirection("N");
                    return isSecondPart ? Integer.valueOf(2) : getNextCounter(guard, lab, false);
                }
            }
        }
        return null;
    }

    private String[][] parseInput(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;

        int rowCount = 0;
        int columnCount = 0;
        while ((line = br.readLine()) != null) {
            rowCount++;
            columnCount = line.split("").length;
        }
        br.close();

        String[][] result = new String[rowCount][columnCount];
        int index = 0;
        br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            String[] individualValue = line.split("");
            System.arraycopy(individualValue, 0, result[index], 0, individualValue.length);

            index++;
        }
        br.close();

        return result;
    }

    public static class Guard {
        private Integer positionX;
        private Integer positionY;
        private String direction;
        private Integer initialX;
        private Integer initialY;

        public Guard(Integer x, Integer y, String direction, Integer initX, Integer initY) {
            this.positionX = x;
            this.positionY = y;
            this.initialX = initX;
            this.initialY = initY;
            this.direction = direction;
        }

        public Integer getInitialX() {
            return initialX;
        }

        public void setInitialX(Integer initialX) {
            this.initialX = initialX;
        }

        public Integer getInitialY() {
            return initialY;
        }

        public void setInitialY(Integer initialY) {
            this.initialY = initialY;
        }

        public Integer getPositionX() {
            return positionX;
        }

        public void setPositionX(Integer positionX) {
            this.positionX = positionX;
        }

        public Integer getPositionY() {
            return positionY;
        }

        public void setPositionY(Integer positionY) {
            this.positionY = positionY;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
}
