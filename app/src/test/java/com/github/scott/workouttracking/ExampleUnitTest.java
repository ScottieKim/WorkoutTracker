package com.github.scott.workouttracking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void sum() {
        List<Integer> list = new ArrayList();
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(400);

        int sumResult = 0;
        for (int i = 0; i < list.size(); i++) {
            sumResult += list.get(i);
        }

        assertEquals(1000, sumResult);
    }

    @Test
    public void average() {
        List<Integer> list = new ArrayList();
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(400);

        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        int averageResult = sum / list.size();
        assertEquals(250, averageResult);
    }

    @Test
    public void calculateCalories(){
        int gender = 1;
        int sumDistance = 1000;

        int calories = 0;
        if (gender == 1) {
            calories = (8 * sumDistance) / 100;
        } else {
            calories = (6 * sumDistance) / 100;
        }
        assertEquals(80, calories);
    }
}