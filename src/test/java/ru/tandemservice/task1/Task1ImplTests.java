package ru.tandemservice.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task1ImplTests {
    IStringRowsListSorter stringRowsListSorter = Task1Impl.getINSTANCE();

    List<String[]> expected;
    List<String[]> result;

    @BeforeEach
    public void fillList() {
        expected = new ArrayList<>();
        String[] str1 = {"First str", "Second str", "1 str"};
        String[] str2 = {"First str", "Second str", "111258 str"};
        String[] str3 = {"First str", "Second str", "5 str"};
        String[] str4 = {"First str", "Second str", ""};
        String[] str5 = {"First str", "Second str", null};
        String[] str6 = {"First str", "Second str"};

        String[] str7 = {"First str"};

        expected.add(null);
        expected.add(str6);
        expected.add(str5);
        expected.add(str4);
        expected.add(str1);
        expected.add(str3);
        expected.add(str2);

    }

    @Test
    public void sortedTests() {
        result = new ArrayList<>();
        String[] str1 = {"First str", "Second str", "1 str"};
        String[] str2 = {"First str", "Second str", "111258 str"};
        String[] str3 = {"First str", "Second str", "5 str"};
        String[] str4 = {"First str", "Second str", ""};
        String[] str5 = {"First str", "Second str", null};
        String[] str6 = {"First str", "Second str"};

        result.add(str1);
        result.add(str2);
        result.add(str3);
        result.add(str4);
        result.add(str5);
        result.add(str6);
        result.add(null);


        stringRowsListSorter.sort(result, 2);

        printArr(result);

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertArrayEquals(expected.get(i), result.get(i));
        }
    }

    public void printArr(List<String[]> list) {
        list.forEach(str -> System.out.println(Arrays.toString(str)));
    }
}
