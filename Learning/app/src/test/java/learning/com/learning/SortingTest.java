package learning.com.learning;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yatish on 18/12/17.
 */

public class SortingTest {

    private Sorting sorting;
    private Sorting.QuickSort qSorting;

    int[] arr = {2,1,6,4,5,3};
    int[] exp = {1,2,3,4,5,6};

    @Before
    public void setup() throws Exception{
        sorting = new Sorting();
        qSorting = new Sorting.QuickSort();
    }

    @Test
    public void bubbleTest() throws Exception{
        //worst-case and average complexity both О(n2)
        //best-case is O(n)
        assertArrayEquals(exp, sorting.bubbleSort(arr));

    }

    @Test
    public void selectionTest() throws Exception{
        //Θ(n2)
        assertArrayEquals(exp, sorting.selectionSort(arr));

    }

    @Test
    public void insertionTest() throws Exception{
        //O(n2)
        assertArrayEquals(exp, sorting.insertionSort(arr));

    }

    @Test
    public void quickTest() throws Exception{

        //average case is Θ(n log(n)) and in the worst case is Θ(n2).

       // assertArrayEquals(exp, qSorting.sort(arr));

    }

    @Test
    public void mergeTest() throws Exception{

        //O(n*log(n))

        // assertArrayEquals(exp, qSorting.sort(arr));

    }


}
