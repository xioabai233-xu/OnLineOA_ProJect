package org.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Comparator;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMpDemol {

    @Test
    public void test01(){
            int[][] intervals = {{1,4},{2,4},{3,6},{4,4}};
            int[] queries ={2,3,4,5};
            Arrays.sort(intervals, Comparator.comparingInt(arr -> arr.length));
            int[] res = new int[queries.length];
            for (int i = 0; i < queries.length; i++) {
                for (int j = 0; j < intervals.length; j++) {
                    if (queries[i] >= intervals[j][0] && queries[i] <= intervals[j][1]) {
                        res[i] = intervals[j][1] - intervals[j][0] + 1;
                        break;
                    }
                }
            }
        }
    public static void main(String[] args) {
    }



}
