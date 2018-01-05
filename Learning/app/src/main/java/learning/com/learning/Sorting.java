package learning.com.learning;

/**
 * Created by yatish on 18/12/17.
 */

public class Sorting {


    public int[] bubbleSort(int[] arr){

        for(int i = arr.length;i>=0;i--){

            for (int j = 0;j<arr.length-1;j++){

                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }


    public int[] selectionSort(int[] arr){

        int length = arr.length;

        for(int i=0;i<length-1;i++){

            int min_index = i;

            for (int j = i+1; j< length;j++){

                if(arr[j] < arr[min_index]){

                    min_index = j;
                }

            }
            int temp = arr[min_index];
            arr[min_index] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    public int[] insertionSort(int[] arr){

        int n = arr.length;

        for(int i=1;i<n;i++){

            for (int j=i;j>0;j--){

                if(arr[j]<arr[j-1]){
                    int temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }

            }

        }

        return arr;
    }


    public static class QuickSort{

        int array[];


        public int[] sort(int[] arr){
            this.array = arr;

            quickSort(0, arr.length-1);
            return array;
        }

        public void quickSort(int i, int j){

            int lowIndex = i;
            int highIndex = j;
            int pivot = array[lowIndex+(highIndex-lowIndex) / 2];

            while(i <= j){

                while(array[i] < pivot){
                    i++;
                }

                while (array[j] > pivot){
                    j--;
                }

                if(i<=j){
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;

                    i++;
                    j--;
                }

            }

            if(lowIndex < j){
                quickSort(j, lowIndex);
            }
            if(i < highIndex){
                quickSort(i, highIndex);
            }


        }


    }

    public static class MergeSort{

        int[] array;
        private int[] tempMergArr;

        public void sort(int[] arr){

            this.array = arr;
            doMergeSort(0, arr.length - 1);

        }

        public void doMergeSort(int lowerIndex, int higherIndex){
            if (lowerIndex < higherIndex) {
                int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
                // Below step sorts the left side of the array
                doMergeSort(lowerIndex, middle);
                // Below step sorts the right side of the array
                doMergeSort(middle + 1, higherIndex);
                // Now merge both sides
                mergeParts(lowerIndex, middle, higherIndex);
            }
        }

        private void mergeParts(int lowerIndex, int middle, int higherIndex) {

            for (int i = lowerIndex; i <= higherIndex; i++) {
                tempMergArr[i] = array[i];
            }
            int i = lowerIndex;
            int j = middle + 1;
            int k = lowerIndex;
            while (i <= middle && j <= higherIndex) {
                if (tempMergArr[i] <= tempMergArr[j]) {
                    array[k] = tempMergArr[i];
                    i++;
                } else {
                    array[k] = tempMergArr[j];
                    j++;
                }
                k++;
            }
            while (i <= middle) {
                array[k] = tempMergArr[i];
                k++;
                i++;
            }

        }

    }



}
