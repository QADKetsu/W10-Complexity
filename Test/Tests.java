package Test;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Quicksort.Complex;


public class Tests {

    //! check that instantiating new Complex object creates a list (list object is not null)
    @Test
    public void newComplexGeneratesArrayOfCorrectZeroANdNotNull() {
        int size = 0;
        Complex obj = new Complex(size, 10);
        assertEquals(0, obj.list.size()); 
        assertNotNull(obj.list);
    }

    //! check that instantiating new Complex object creates a list of correct odd size upon request
    @Test
    public void newComplexGeneratesArrayOfCorrectSizeOdd() {
        int size = 5;
        Complex obj = new Complex(size, 10);
        assertEquals(5, obj.list.size()); 
    }

    //! check that instantiating new Complex object creates a list of correct even size upon request
    @Test
    public void newComplexGeneratesArrayOfCorrectSizeEven() {
        int size = 6;
        Complex obj = new Complex(size, 10);
        assertEquals(6, obj.list.size()); 
    }

    //! check if numbers within their pair is sorted and calculated correctly - keep test 2 (always 100%)
    // for last element, a temporary element that MUST be larger than it will added to the end of the list
    // since the temp is alwayts larger, the last pair for odd size will always be sorted
    //!  same results for both test1 odd and even tests
    @Test
    public void checkTest1WithOddSize(){
        // [2, 1, 3, 4, 5, 6, 7, 8, 9] // 4/5 pairs sorted // 4/4 - 100% // total - 4/5*5/5 = 80%
        List<Integer> initialisedList =  Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 4)/5)*100;
        assertEquals(sort, correctSortedness);
    }

    //! check if numbers within their pair is sorted and calculated correctly - keep test 2 (always 100%)
    //!  same results for both test1 odd and even tests
    @Test
    public void checkTest1WithEvenSize(){
        // [2, 1, 3, 4, 5, 6, 7, 8, 9, 10] // 4/5 pairs sorted // 4/4 - 100% // total - 4/5*5/5 = 80%
        List<Integer> initialisedList =  Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 4)/5)*100;
        assertEquals(sort, correctSortedness);
    }

    //! check if numbers between pairs are sorted and calculated correctly - keep test 1 (always 100%)
    // test 2 compares the LARGEST element of the first pair with the SMALLEST element of the second pair
    //  temporary element that MUST be larger than it will added to the end of the list
    //  but since the temp is always larger, the actual last element will always be used to compare with the pervious pair
    //  therefore adding the temporary last element will not affect results
    //!  same results for both test2 odd and even tests
    @Test
    public void checkTest2WithOddSize(){
        // [1, 2, 5, 6, 3, 4, 7, 8, 9, 10] // 5/5 pairs sorted // 3/4 - 100% // total - 5/5*3/4 = 75%
        List<Integer> initialisedList =  Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 8);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 3)/4)*100;
        assertEquals(sort, correctSortedness);
    }

    //! check if numbers between pairs are sorted and calculated correctly - keep test 1 (always 100%)
    // test 2 compares the LARGEST element of the first pair with the SMALLEST element of the second pair
    //!  same results for both test2 odd and even tests
    @Test
    public void checkTest2WithEvenSize(){
        // [1, 2, 5, 6, 3, 4, 7, 8, 9] // 5/5 pairs sorted // 3/4 - 100% // total - 5/5*3/4 = 75%
        List<Integer> initialisedList =  Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 8, 10);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 3)/4)*100;
        assertEquals(sort, correctSortedness);
    }

    //! list with no elements is returns sortedness value of -1
    @Test
    public void checkMeasureSortWithSizeZero() {
        int size = 0;
        Complex obj = new Complex(size, 10);

        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = -1;
        assertEquals(sort, correctSortedness);
    }

    //! list with only one element is fully sorted (100%)
    @Test
    public void checkMeasureSortWithSizeOne() {
        int size = 1;
        Complex obj = new Complex(size, 10);

        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = 100;
        assertEquals(sort, correctSortedness);
    }

    //! check if sortedness is correct for odd array size
    @Test
    public void checkMeasureSortWithOddSize(){
        // [3, 4, 2, 1, 5, 6, 7, 8, 9, 10] // 4/5 pairs sorted // 2/4 - 50% // total - 2/5 - 40%
        List<Integer> initialisedList =  Arrays.asList(3, 4, 2, 1, 5, 6, 9, 10, 7);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 2)/5)*100;
        assertEquals(sort, correctSortedness);
        
    }
    //! check if sortedness is correct for even array size
    @Test
    public void checkMeasureSortWithEvenSize(){
        // [3, 4, 2, 1, 5, 6, 9, 10, 7, 8] // 4/5 pairs sorted // 2/4 - 50% // total - 2/5 - 40%
        List<Integer> initialisedList =  Arrays.asList(3, 4, 2, 1, 5, 6, 9, 10, 7, 8);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 2)/5)*100;
        assertEquals(sort, correctSortedness);
    }

    //! check if sortedness is correct for increasing (fully) sorted array of any size
    @Test
    public void checkMeasureSortWithSortedArray(){
        // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] // 5/5 pairs sorted // 4/4 - 100% // total - 1/1 - 100%
        List<Integer> initialisedList =  Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 1)/1)*100;
        assertEquals(sort, correctSortedness);
    }

    //! check if sortedness is correct for decreasing sorted array of any size
    @Test
    public void checkMeasureSortWithOppSortedArray(){
        // [10, 9, 8, 7, 6, 5, 4, 3, 2, 1] // 0/5 pairs sorted // 0/4 - 0% // total - 0/0 - 0%
        List<Integer> initialisedList =  Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = 0;
        assertEquals(sort, correctSortedness);
    }

    //! check if sortedness is correct for array of any size with all the same elements
    @Test
    public void checkMeasureSortIdenticalArray(){
        // [3, 3, 3, 3, 3, 3, 3, 3, 3, 3] // 5/5 pairs sorted // 4/4 - 100% // total - 1/1 - 100%
        List<Integer> initialisedList =  Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3);
        Complex obj = new Complex(10, 10);
        obj.list.clear();

        for (int i = 0; i<initialisedList.size(); i++){
            obj.list.add(initialisedList.get(i));
        }
        
        //obj.list = initialisedList;
        float sort = Complex.measureSort(obj.list, obj.upperb);
        float correctSortedness = (((float) 1)/1)*100;
        assertEquals(sort, correctSortedness);
    }

    //! check if my quicksort is correct for array of size zero by comparing it with the collection sort function
    @Test
    public void sizeZeroSort(){
        Complex obj = new Complex(0, 10);

        // get the sorted array by calling collections
        List<Integer> collectionSortedList = obj.list;
        Collections.sort(collectionSortedList);

        // get sorted array from own code
        obj.list = Complex.sort(obj.list);

        // check if equals
        
        assertTrue(collectionSortedList.equals(obj.list));
    }

    //! check if my quicksort is correct for array of size odd size by comparing it with the collection sort function
    public void randomSizeOfOdd() {
        Complex obj = new Complex(5, 10);
        //float sort = Complex.measureSort(obj.list, obj.upperb);

        // get the sorted array by calling collections
        List<Integer> collectionSortedList = obj.list;
        Collections.sort(collectionSortedList);

        // get sorted array from own code
        obj.list = Complex.sort(obj.list);

        // check if equals
        assertTrue(collectionSortedList.equals(obj.list));

        // int i = 0;
        // while (i<obj.list.size()){
        //     assertEquals(collectionSortedList.get(i), obj.list.get(i));
        //     i++;
        // }
    }

    //! check if my quicksort is correct for array of even size by comparing it with the collection sort function
    @Test
    public void randomSizeOfEven(){
        Complex obj = new Complex(6, 10);

        // get the sorted array by calling collections
        List<Integer> collectionSortedList = obj.list;
        Collections.sort(collectionSortedList);

        // get sorted array from own code
        obj.list = Complex.sort(obj.list);

        // check if equals
        assertTrue(collectionSortedList.equals(obj.list));

        // int i = 0;
        // while (i<obj.list.size()){
        //     assertEquals(collectionSortedList.get(i), obj.list.get(i));
        //     i++;
        // }
    }

    
}
