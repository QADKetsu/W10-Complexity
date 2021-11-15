import java.io.*;
import java.util.*;
import java.util.stream.*;

public class DataGeneration {
    public static void main(String[] args) throws IOException {
        generateData(100, 100);
    }

    public static void generateData(int n, int timesShuffled) throws IOException {
        // create random List Integer of n size
        int[] randomNArray = new Random().ints(n, 0, 10 * n).toArray();
        List<Integer> randomList = Arrays.stream(randomNArray).boxed().collect(Collectors.toList());
        // sort random list
        Collections.sort(randomList);
        FileWriter writer = new FileWriter("../data/" + n + "LengthData.csv");

        for (int i = 0; i < timesShuffled; i++) {
            // deep copy random list
            List<Integer> randomListCopy = new ArrayList<Integer>(randomList);
            // calculate sortedness of deep copy
            float sortedness = measureSortedness(randomListCopy, 10 * n);
            // calculate time taken to sort the deep copy
            long startTime = System.nanoTime();
            sort(randomListCopy);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            // write to file
            writer.write(sortedness + "," + duration + "\n");

            // shuffle randomlist
            randomList = shuffle(randomListCopy, i);
        }
        writer.close();
    }

    public static List<Integer> interleaveShuffle(List<Integer> list) {
        // cut in half
        int half = list.size() / 2;
        List<Integer> firstHalf = list.subList(0, half);
        List<Integer> secondHalf = list.subList(half, list.size());
        
        // interleave
        List<Integer> interleaved = new ArrayList<Integer>();
        for (int i = 0; i < firstHalf.size(); i++) {
            interleaved.add(firstHalf.get(i));
            interleaved.add(secondHalf.get(i));
        }
        return interleaved;
    }

    public static List<Integer> shuffle(List<Integer> list, int i) {
        // deep copy list
        List<Integer> listCopy = new ArrayList<Integer>(list);
        // split into 4 parts
        listCopy = interleaveShuffle(listCopy);
        int size = list.size();
        int partSize = size / 4;
        // create 4 new lists
        List<Integer> part1 = new LinkedList<Integer>();
        List<Integer> part2 = new LinkedList<Integer>();
        List<Integer> part3 = new LinkedList<Integer>();
        List<Integer> part4 = new LinkedList<Integer>();

        // for loop transfer from listCopy to 4 new lists
        for (int j = 0; j < size; j++) {
            if (j < partSize) {
                part1.add(listCopy.get(j));
            } else if (j < 2 * partSize) {
                part2.add(listCopy.get(j));
            } else if (j < 3 * partSize) {
                part3.add(listCopy.get(j));
            } else {
                part4.add(listCopy.get(j));
            }
        }

        switch (i % 4) {
            case 0:
                // sort part 2
                sort(part2);
                part1.addAll(part2);
                part1.addAll(part3);
                part1.addAll(part4);
                break;
            case 1:
                // sort part 2 and 3
                sort(part2);
                sort(part3);
                part1.addAll(part2);
                part1.addAll(part3);
                part1.addAll(part4);
                break;
            case 2:
                // sort 2 3 4
                sort(part2);
                sort(part3);
                sort(part4);
                part1.addAll(part2);
                part1.addAll(part3);
                part1.addAll(part4);
                break;
            case 3:
                // add all
                part1.addAll(part2);
                part1.addAll(part3);
                part1.addAll(part4);
                break;
        }

        return part1;
    }

    // ! sorting algos

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

    public static float measureSortedness(List<Integer> myList, int upperb) {
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
}
