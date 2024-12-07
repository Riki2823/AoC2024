package aoc2024;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Day5Test {

    @Test
    public void examplePar2() throws IOException {
        Day5 day5 = new Day5(Day4.class.getClassLoader().getResource("Day5/Day5Part1.txt"));
        Assert.assertEquals(Integer.valueOf(5391), day5.day5(false));
    }
}
