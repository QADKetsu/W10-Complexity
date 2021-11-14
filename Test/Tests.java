package Test;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Quicksort.complex;


public class Tests {
    
    @Test
    public void quickSortWorking() {
        complex obj = new complex(10, 10);
        float sort = complex.measureSort(obj.list, obj.upperb);

        // get the sorted array by calling collections
        List<Integer> originalList = obj.list;
        Collections.sort(originalList);

        // get sorted array from own code
        obj.list = complex.sort(obj.list);

        // check if equals
        int i = 0;
        while (i<obj.list.size()){
            assertEquals(originalList.get(i), obj.list.get(i));
            i++;
        }
    }

    @Test 
}
