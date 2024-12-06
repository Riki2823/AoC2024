package aoc2024;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Day4Test {

    @Test
    public void examplePar2() throws IOException {
        Day4 day4 = new Day4(Day4.class.getClassLoader().getResource("Day4/Day4Example.txt"));
        Assert.assertEquals(Integer.valueOf(9), day4.day4(true));
    }

}
