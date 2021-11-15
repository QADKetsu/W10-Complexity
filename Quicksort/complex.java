package Quicksort;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// [2, 1, 7, 4, 5, 6] - 66% 1/3 sorted // 
// [2, 1, 3, 4, 5, 6, 7, 8, 9, 10] // 4/5 pairs sorted // 4/4 - 100% // total - 4/5*5/5 = 80%
// [3, 4, 2, 1, 5, 6, 7, 8, 9, 10] // 4/5 pairs sorted // 3/4 - 75% // total - 3/5 - 60%
// [2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11] //


// split it into pairs - smaller arrays and check if they are sorted
// 

public class Complex {
    public List<Integer> list = new ArrayList<Integer>();
    public int upperb;
    //Integer[] arr = new Integer[] {3,4,2,1,5,6,7,8,9};

    public Complex(int n, int upperb) {
        this.upperb = upperb;
        int[]  randomIntsArray = new Random().ints(n, 0, upperb).toArray();
        this.list = Arrays.stream(randomIntsArray).boxed().collect(Collectors.toList());
        // this.list = Arrays.stream(3, 4, 2, 1, 5, 6, 7, 8, 9).boxed().collect(Collectors.toList());;
        //this.list = Arrays.asList(arr);
    }
    public static void main(String[] args) {
        //List<Integer> myList = Arrays.asList(5, 3, 8, 2, 9, 10, 11, 4, 4, 17, 2, 4, 78, 9, 3, 1, 5, 7, 9, 46 ,1, 6, 9, 4, 2, 3);
        
        // get the main 9 numbers and sort them
        Complex obj = new Complex(10000, 101);
        
        int size = obj.list.size();
        obj.list = sort(obj.list);
        List<Integer> to_pass = obj.list;
        try {
            FileWriter writer = new FileWriter("data.csv");
        

            for (int i=0; i<size-1; i++){
        
                float sort = measureSort(obj.list, obj.upperb);
                System.out.format("This is how sorted the array is: %f\n", sort);

                // measure time taken
                long start = System.currentTimeMillis();
                to_pass = sort(to_pass);
                long end = System.currentTimeMillis();


                NumberFormat formatter = new DecimalFormat("#0.00000");
                System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
                System.out.println();

                writeToCSV(writer, sort, end-start);
                
                // do interleave for arrays
                List<List<Integer>> parts = new ArrayList<List<Integer>>();
                if (size %2 == 0){
                    parts.add(new ArrayList<Integer>(obj.list.subList(0, (size/2))));
                    parts.add(new ArrayList<Integer>(obj.list.subList((size/2), size)));
                } else {
                    parts.add(new ArrayList<Integer>(obj.list.subList(0, (size/2)+1)));
                    parts.add(new ArrayList<Integer>(obj.list.subList((size/2)+1, size)));
                }

                obj.list.clear();

                while (true){
                    if (parts.get(0).size()==1){
                        obj.list.add(parts.get(0).get(0));
                        if (parts.get(1).isEmpty()){
                            break;
                        } else {
                            obj.list.add(parts.get(1).get(0));
                            break;
                        }
                    }
                    obj.list.add(parts.get(0).get(0));
                    parts.get(0).remove(0);
                    obj.list.add(parts.get(1).get(0));
                    parts.get(1).remove(0);
                }

                // System.out.println("A");
                // System.out.println(obj.list);

                int num_shuffle =obj.list.remove(0);
                obj.list.add(num_shuffle);

                to_pass = obj.list;
                // System.out.println("B");
                // System.out.println(obj.list);
        
            }
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        
    }

    public static void writeToCSV(FileWriter wr, float sort, float time){
        StringBuilder sb = new StringBuilder();
        sb.append(sort);
        sb.append(',');
        sb.append(time);
        sb.append('\n');
        try {
            wr.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static float measureSort(List<Integer> myList, int upperb) {
        float sortedness = 10;
        boolean isOdd = false;
        int sortedWithinPair= 0;
        int sortedBetweenPair = 0;
        float calc1;
        float calc2;

        // return -1 as error for sorting array with zero elements
        if (myList.size() == 0){
            return -1;
        } else if (myList.size() ==1) { // return 100 as fully sorted for array with only one element
            return 100;
        }

        // if the size is odd, add an extra number to make a pair
        // do this because comparing the original last element with a number > upperbound will always true
        if ((myList.size() % 2) == 1){
            myList.add(upperb+1);
            isOdd = true;
            // System.out.println("New array if odd:");
            // System.out.println(myList);
        }

        

        // test 1: test each pair to see if its sorted within themselves
        for (int i=0; i<myList.size(); i= i+2){
            if (myList.get(i) <= myList.get(i+1)) {
                sortedWithinPair++;
            }
        }

        calc1 = ((float)sortedWithinPair)/(myList.size()/2);
        // System.out.println("Test 1");
        // System.out.println(sortedWithinPair);
        // System.out.println(calc1);


        // test 2: test two pairs to see if they are in the right order

        List<List<Integer>> parts = new ArrayList<List<Integer>>();
        for (int i = 0; i < myList.size(); i += 2) {
            parts.add(new ArrayList<Integer>(myList.subList(i, i+2)));
            //System.out.println(myList.subList(i, i+2));
        }
        //System.out.println(parts);
        
        // compare the largest value of the first pair with the smallest value of the other pair
        List<Integer> array1 = new ArrayList<Integer>();
        List<Integer> array2 = new ArrayList<Integer>();

        for (int j=0; j<parts.size()-1; j++){
            array1 = parts.get(j);
            array2 = parts.get(j+1);

            if (Collections.max(array1) <= Collections.min(array2)){
                sortedBetweenPair++;
            }
        }

        calc2 = ((float)sortedBetweenPair)/((myList.size()/2) -1);
        // System.out.println("Test 2");
        // System.out.println(sortedBetweenPair);
        // System.out.println(calc2);

        //if the size was previously odd, remove the last element
        if (isOdd == true){
            myList.remove(myList.size()-1);
        }

        sortedness = (calc1 * calc2) * 100;

        return sortedness;
    }


    public static List<Integer> sort(List<Integer> myList) {
        int pass_length = myList.size()-1;
        //System.out.format("Length minus pivot: %d\n", pass_length);
        if (pass_length == -1){
            return myList;
        }
        
        int pivot = myList.get(pass_length);
        //System.out.format("Pivot: %d\n", pivot);

        List<Integer> smaller_array = new ArrayList<Integer>();
        List<Integer> larger_array = new ArrayList<Integer>();
        List<Integer> to_return = new ArrayList<Integer>();

        for (int i=0; i<pass_length; i++){
            int num = myList.get(i);
            if (num<pivot){
                smaller_array.add(num);
            } else {
                larger_array.add(num);
            }
        }

        // System.out.format("Length small: %d\n", smaller_array.size());
        // System.out.println(smaller_array);
        // System.out.format("Length large: %d\n", larger_array.size());
        // System.out.println(larger_array);

        smaller_array = sort(smaller_array);
        larger_array = sort(larger_array);

        to_return.addAll(smaller_array);
        to_return.add(pivot);
        to_return.addAll(larger_array);

        //return sort(smaller_array);
        //System.out.println(pivot);
        
        return to_return;
    }

}
