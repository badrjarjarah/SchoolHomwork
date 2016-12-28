import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

public class sorting {
    
    private static int[] arr;
    private static int[] arrCopy;
    private static int[] mergeArr;
    private static BufferedReader read;
    private static Random randomGenerator;
    
    private static int size;
    private static int random;

    private static void printArray(String msg) {
    	System.out.print(msg + " [" + arr[0]);
        for(int i=1; i<size; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }
    
    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t; 
    }
    
    public static void insertSort(int left, int right) {
	  // insertSort the subarray arr[left, right]
	  int i, j;

	  for(i=left+1; i<=right; i++) {
	    int temp = arr[i];           // store a[i] in temp
	    j = i;                       // start shifts at i
	    // until one is smaller,
	    while(j>left && arr[j-1] >= temp) {
		  arr[j] = arr[j-1];        // shift item to right
		  --j;                      // go left one position
	    }
	    arr[j] = temp;              // insert stored item
	  }  // end for
    }  // end insertSort()
    
    public static void insertionSort() {
    	insertSort(0, size-1);  
    } // end insertionSort()

    
    public static void maxheapify(int i, int n) { 
	// Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;
        
        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;
        
        if (max != i) {  // node i is not maximal
            exchange(i, max);
            maxheapify(max, n);
        }
    }
    
    public static void heapsort(){
    	// Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxheapify(i, size);
         
        for(int i=size-1;i>0;i--) {
            exchange(0, i);       // move max from heap to position i.
            maxheapify(0, i);     // adjust heap
        }
    }
    
    private static void mergesort(int low, int high) {
    	// sort arr[low, high-1]
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high-1) {
          // Get the index of the element which is in the middle
          int middle = (high + low) / 2;
          // Sort the left side of the array
          mergesort(low, middle);
          // Sort the right side of the array
          mergesort(middle, high);
          // Combine them both
          merge(low, middle, high);
        }
      }
    private static void mergesort2(int low, int high) {
        
        // sort arr[low, high-1]
        // Check if low is smaller then high, and do not 
        // subdivide the problem smaller if already less than 32
        if (low < high-1 && high-low>32) {
            // Get the index of the element which is in the middle
            int middle = (high + low) / 2;
            // Sort the left side of the array
            mergesort2(low, middle);           
            // Sort the right side of the array 
            mergesort2(middle, high);
            // Combine them both
            merge(low, middle, high);
        }
        else 
            insertSort(low,high-1);
        
    }

    private static void merge(int low, int middle, int high) {
    	// merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1]
    	
        // Copy first part into the arrCopy array
        for (int i = low; i < middle; i++) mergeArr[i] = arr[i];

        int i = low;
        int j = middle;
        int k = low;

        // Copy the smallest values from either the left or the right side back        // to the original array
        while (i < middle && j < high) 
          if (mergeArr[i] <= arr[j]) 
            arr[k++] = mergeArr[i++];
          else 
            arr[k++] = arr[j++];
	
        // Copy the rest of the left part of the array into the original array
        while (i < middle) arr[k++] = mergeArr[i++];
    }
    
    public static void naturalMergesort() {          
    	int run[], i, j, s, t, m;                                                                         
    	                                                                                                                                                                           
    	run = new int[size/2];                                                       
    	                                                                                                   
	    // Step 1: identify runs from the input array arr[]                                                                               
    	i = m = 1; 
    	run[0] = 0;                                                                          
        while (i < size) {                                                                                
    	    if (arr[i-1] > arr[i])                                                                   
    	       if (run[m-1]+1 == i) {     // make sure each run has at least two                          
    	                                                                                                  
    	          j = i+1;                                                                                 
    	          s = 0;                                                                                   
    	          while (j < size && arr[j-1] >= arr[j]) j++;     // not stable                                    
          
    	          // reverse arr[i-1, j-1];     
    	          s = i - 1;
    	          t = j - 1;
    	          while (s < t) exchange(s++, t--);
    	          
    	          i = j;                                                                                   
    	        } else                                                                                     
    	          run[m++] = i++;                                                                          
    	     else i++;                                                                                    
    	}                               	                                                                                                                                                                                            
    	                                                                                                   
        // Step 2: merge runs bottom-up into one run                                                                       
        t = 1;                                                                                         
    	while (t < m) {                                                                                
    	    s = t;                                                                                       
    	    t = s<<1;                                                                                    
    	    i = 0;                                                                                       
    	    while (i+t < m) {                                                                            
    	        merge(run[i], run[i+s], run[i+t]);                                                    
    	        i += t;                                                                                    
    	    }                                                                                            
    	    if (i+s < m) merge(run[i], run[i+s], size);                                                
    	}                                                                                              
                                                
    }   
      
    private static void quicksort(int low, int high) {
	    int i = low, j = high;

	    // Get the pivot element from the middle of the list
	    int pivot = arr[(high+low)/2];

	    // Divide into two lists
	    while (i <= j) {
    	      // If the current value from the left list is smaller then the pivot
    	      // element then get the next element from the left list
    	      while (arr[i] < pivot) i++;
    	      
    	      // If the current value from the right list is larger then the pivot
    	      // element then get the next element from the right list
    	      while (arr[j] > pivot) j--;

    	      // If we have found a value in the left list which is larger than
    	      // the pivot element and if we have found a value in the right list
    	      // which is smaller then the pivot element then we exchange the
    	      // values.
    	      // As we are done we can increase i and j
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }

    	// Recursion
    	if (low < j)
    	      quicksort(low, j);
    	if (i < high)
    	      quicksort(i, high);
    }
    private static void quicksort2(int low, int high) {
        int i = low, j = high;

        // Get the pivot element from the middle of the list
        int pivot = arr[(high+low)/2];

        // Divide into two lists
        while (i<=j && j-i>32) {
          // If the current value from the left list is smaller then the pivot
          // element then get the next element from the left list
          while (arr[i] < pivot) i++;

          // If the current value from the right list is larger then the pivot
          // element then get the next element from the right list
          while (arr[j] > pivot) j--;

          // If we have found a value in the left list which is larger than
          // the pivot element and if we have found a value in the right list
          // which is smaller then the pivot element then we exchange the
          // values.
          // As we are done we can increase i and j
          if (i < j) {
            exchange(i, j);
            i++;
            j--;
          } else if (i == j) { 
              i++; 
              j--; 
          }
        }
        
        
    	// Recursion
    	if (low < j){
            if(j-low>32)
                quicksort2(low, j);
            else {
                // subproblem less than 32 integers
                insertSort(low,j); }
        }
    	if (i < high) {
            if(high-i>32) 
                quicksort2(i, high);
            else {
                // subproblem is less than 32 integers
                insertSort(i,high); }
        }
    }
    private static void quicksort3(int low, int high) {
	    int i = low, j = high;

	    // Get the pivot element from the middle of the list
	    int pivot = arr[(high+low)/2];

	    // Divide into two lists
	    while (i <= j) {
    	      // If the current value from the left list is smaller then the pivot
    	      // element then get the next element from the left list
    	      while (arr[i] < pivot) i++;
    	      
    	      // If the current value from the right list is larger then the pivot
    	      // element then get the next element from the right list
    	      while (arr[j] > pivot) j--;

    	      // If we have found a value in the left list which is larger than
    	      // the pivot element and if we have found a value in the right list
    	      // which is smaller then the pivot element then we exchange the
    	      // values.
    	      // As we are done we can increase i and j
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }

    	// Recursion call checks to see if subproblem is already sorted 
        // before recursively calling 
    	if (low < j)
    	    if(!isSorted(low,j))
                quicksort3(low, j);
            
    	if (i < high)
            if(!isSorted(i,high))
                quicksort3(i, high);
    }
    private static void quicksort4(int low, int high) {
         int i = low, j = high;

        // Get the pivot element from either the first 
        int pivot = findMedian(arr[low],arr[(high+low)/2],arr[high]);
        
        
        // Divide into two lists
        while (i<=j && j-i>32) {
          // If the current value from the left list is smaller then the pivot
          // element then get the next element from the left list
          while (arr[i] < pivot) i++;

          // If the current value from the right list is larger then the pivot
          // element then get the next element from the right list
          while (arr[j] > pivot) j--;

          // If we have found a value in the left list which is larger than
          // the pivot element and if we have found a value in the right list
          // which is smaller then the pivot element then we exchange the
          // values.
          // As we are done we can increase i and j
          if (i < j) {
            exchange(i, j);
            i++;
            j--;
          } else if (i == j) { 
              i++; 
              j--; 
          }
        }
        
        
    	// Recursion
    	if (low < j){
            if(j-low>32)
                quicksort4(low, j);
            else {
                // subproblem less than 32 integers
                insertSort(low,j); }
        }
    	if (i < high) {
            if(high-i>32) 
                quicksort4(i, high);
            else {
                // subproblem is less than 32 integers
                insertSort(i,high); }
        }
    }
    private static void quicksort5(int low, int high) {
        int i = low, j = high;

        // Get the pivot element from the middle of the list
        randomGenerator = new Random();
        int[] pivots = new int[9];
        
        
        for(int x = 0; x<9;x++) {
            // pick random value between low and high
            int value = arr[randomGenerator.nextInt((high-low)+1) + low];
            // make sure this value hasn't already been picked
            while(Arrays.asList(pivots).contains(value)) {
                value = arr[randomGenerator.nextInt((high-low)+1) + low];
            }
            pivots[x] = value;   
        }
        
        int pivot = findMedian(pivots);
        
        
        // Divide into two lists
        while (i<=j && j-i>32) {
          // If the current value from the left list is smaller then the pivot
          // element then get the next element from the left list
          while (arr[i] < pivot) i++;

          // If the current value from the right list is larger then the pivot
          // element then get the next element from the right list
          while (arr[j] > pivot) j--;

          // If we have found a value in the left list which is larger than
          // the pivot element and if we have found a value in the right list
          // which is smaller then the pivot element then we exchange the
          // values.
          // As we are done we can increase i and j
          if (i < j) {
            exchange(i, j);
            i++;
            j--;
          } else if (i == j) { 
              i++; 
              j--; 
          }
        }
        
        
    	// Recursion
    	if (low < j){
            if(j-low>32)
                quicksort5(low, j);
            else {
                // subproblem less than 32 integers
                insertSort(low,j); }
        }
    	if (i < high) {
            if(high-i>32) 
                quicksort5(i, high);
            else {
                // subproblem is less than 32 integers
                insertSort(i,high); }
        }
    }
    public static void demo1 (String input) {
    	// demonstration of sorting algorithms on random input
    	
        long start, finish;
        System.out.println();
        
        // Heap sort      
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("heapsort on " + input + " input: " + (finish-start) + " milliseconds.");
             
        // Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        mergesort(0, size);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("mergesort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Natural Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        naturalMergesort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("natural mergesort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Quick sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        quicksort(0, size-1);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");   
        System.out.println("quicksort on " + input + " input: " + (finish-start) + " milliseconds.");
    }
    
    public static void demo2 (String input) {
       	// demonstration of sorting algorithms on nearly sorted input
    	
        long start, finish;
	    
        demo1(input); 

        // Insert sort on nearly-sorted array      
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
	insertionSort();	    
	finish = System.currentTimeMillis();
	if (size < 101) printArray("out");
	System.out.println("insertsort on " + input + " input: " + (finish-start) + " milliseconds.");
    }
    
    public static void task1(String msg, int arraySize) {
        System.out.println(msg);
        size = arraySize;
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        // hold the run times of each sorting algorithm in 
        // order mergsort -> mergesort2 -> quicksort -> quicksort2
        long[] times = new long[4];
        long start,finish;
        
        randomGenerator = new Random();
        
        // fill array
	random = size*10;
        System.out.print("Sorting array of random values with 10,000,000 integers...");
        // create 100 arrays of size 10000000
        for(int x = 0; x<100;x++) {
            
            // fill array with random integers
            for(int i=0; i<size; i++) {
                
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            
            // mergesort
            start = System.currentTimeMillis();
            mergesort(0,size);
            finish = System.currentTimeMillis();
            times[0] += (finish-start);
            
            
            // mergsort2
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            mergesort2(0,size);
            finish = System.currentTimeMillis();
            times[1] += (finish-start);
            
            
            // quicksort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start);
            
            
            // quicksort2
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort2(0,size-1);
            finish = System.currentTimeMillis();
            times[3] +=  (finish-start);    
        }
        System.out.println("done");
        System.out.println("Average Running times: ");
        System.out.println("mergesort(): " + (times[0]/100)*.001 + "seconds");
        System.out.println("mergesort2(): " + (times[1]/100)*.001 + "seconds");
        System.out.println("quicksort(): " + (times[2]/100)*.001 + "seconds");
        System.out.println("quicksort2(): " + (times[3]/100)*.001 + "seconds");
        
    /** from findings of completing this with array size 10,000,000 both merge sorts performed 
    * slower than the quick sorts with quicksort() being the best and mergesort() being the worst
    * with almost a half a second difference per array sort
    *mergesort(): 1.401seconds
    *mergesort2(): 1.243seconds
    *quicksort(): 1.035seconds
    *quicksort2(): 1.227seconds
    */
    }
    public static void task2(String msg, int arraySize) {
        System.out.println(msg);
        size = arraySize;
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        // hold the run times of each sorting algorithm in 
        // order quick -> quickesort2 -> quicksort3 
        long[] times = new long[3];
        long start,finish;
        
        randomGenerator = new Random();
        
        // fill array
	random = size*10;
        // create 10 arrays of size 10000000 of random values
        System.out.print("Sorting random array of 10,000,000 integers...");
        for(int x = 0; x<10;x++) {
            
            // fill array with random integers
            for(int i=0; i<size; i++) {
                
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            
            // quicksort
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[0] += (finish-start);
            
            
            // quicksort2
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort2(0,size-1);
            finish = System.currentTimeMillis();
            times[1] += (finish-start);
            
            
            // quicksort3
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort3(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start);
            
 
        }
        System.out.println(" done");
        System.out.print("Sorting presorted array of 10,000,000...");
        // runtimes for already sorted list
        for(int x=0;x<10;x++) {
            for(int i= 0;i<size;i++) {
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            // sort array
            quicksort(0,size-1);
            
            // quicksort
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[0] += (finish-start);
            
            
            // quicksort2
            
            start = System.currentTimeMillis();
            quicksort2(0,size-1);
            finish = System.currentTimeMillis();
            times[1] += (finish-start);
            
            
            // quicksort3
            start = System.currentTimeMillis();
            quicksort3(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start);  
        }
        System.out.println(" done");
        System.out.print("Sorting reverse sorted array of 10,000,000...");
        // runtimes for already reverse sorted list
        for(int x=0;x<10;x++) {
            for(int i= 0;i<size;i++) {
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            // reverse sort array by first converting to Integer array than reverse sort
            // than convertint it back to a int[] array
            Integer[] integerArray = Arrays.stream( arr ).boxed().toArray( Integer[]::new );
            Arrays.sort(integerArray, Collections.reverseOrder());
            for(int i=0;i<size;i++) {
                arr[i] = arrCopy[i] = (int) integerArray[i];
            }
            
            
            // quicksort
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[0] += (finish-start);
            
            
            // quicksort2
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort2(0,size-1);
            finish = System.currentTimeMillis();
            times[1] += (finish-start);
            
            
            // quicksort3
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort3(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start);  
        }
        System.out.println(" done");
        System.out.println("Average Running times: ");
        System.out.println("quicksort(): " + (times[0]/30)*.001 + " seconds");
        System.out.println("quicksort2(): " + (times[1]/30)*.001 + " seconds");
        System.out.println("quicksort3(): " + (times[2]/30)*.001 + " seconds");
    /** quicksort3() had the best running times due to it checking for a specific case
     * where the array is already sorted 
     * Average Running times: 
     *quicksort(): 0.447 seconds
     *quicksort2(): 0.468 seconds
     *quicksort3(): 0.34500000000000003 seconds
     */      
    }
    public static void task3(String msg, int arraySize) {
        
        System.out.println(msg);
        size = arraySize;
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        // hold the run times of each sorting algorithm in 
        // order heapsort -> quicksort -> quicksort3 -> quicksort4 -> quicksort5 -> natural mergesort
        long[] times = new long[6];
        long start,finish;
        
        randomGenerator = new Random();
        
        // fill array
	random = size*10;
        
        // create 10 arrays of size 10000000 of random numbers
        System.out.print("Sorting random array of 1,000,000...");
        for(int x = 0; x<10;x++) {
            
            // fill array with random integers
            for(int i=0; i<size; i++) {
                
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            
            // heapsort
            start = System.currentTimeMillis();
            heapsort();
            finish = System.currentTimeMillis();
            times[0] += (finish-start);
            
            
            // quicksort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[1] += (finish-start);
            
            
            // quicksort3
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort3(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start);
            
            
            // quicksort4
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort4(0,size-1);
            finish = System.currentTimeMillis();
            times[3] +=  (finish-start); 
            
            // quicksort5
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort5(0,size-1);
            finish = System.currentTimeMillis();
            times[4] +=  (finish-start); 
            
            // naturalMergesort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            naturalMergesort();
            finish = System.currentTimeMillis();
            times[5] +=  (finish-start);
        }
        System.out.println(" done");
        System.out.print("Sorting reverse sorted array of 1,000,000...");
        
        // reverse sorted run times 
        for(int x=0;x<10;x++) {
            
            for(int i= 0;i<size;i++) {
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            // reverse sort array by first converting to Integer array than reverse sort
            // than convertint it back to a int[] array
            Integer[] integerArray = Arrays.stream( arr ).boxed().toArray( Integer[]::new );
            Arrays.sort(integerArray, Collections.reverseOrder());
            for(int i=0;i<size;i++) {
                arr[i] = arrCopy[i] = (int) integerArray[i];
            }
            
            
            // heapsort
            start = System.currentTimeMillis();
            heapsort();
            finish = System.currentTimeMillis();
            times[0] += (finish-start);
            
            
            // quicksort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[1] += (finish-start);
            
            
            // quicksort3
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort3(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start);
            
            // quicksort4
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort4(0,size-1);
            finish = System.currentTimeMillis();
            times[3] +=  (finish-start);
            
            // quicksort5
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort5(0,size-1);
            finish = System.currentTimeMillis();
            times[4] +=  (finish-start);
            
            // naturalMergesort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            naturalMergesort();
            finish = System.currentTimeMillis();
            times[5] +=  (finish-start);
        }
        
        // had to change the size due to a stack over flow caused by the 
        // quick sort 4 on this type of input. So to counter this I multiplied 
        // run times by 10
        size = 100000;
        random = size*10;
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        System.out.println(" done");
        System.out.print("Sorting organ-pipe sorted array of 100,000...");
        // organ-pipe sorted run times 
        for(int x=0;x<10;x++) {
            
            for(int i= 0;i<size;i++) {
                arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
            }
            // reverse sort array by first converting to Integer array than reverse sort
            // than convertint it back to a int[] array
            Integer[] integerArray = Arrays.stream( arr ).boxed().toArray( Integer[]::new );
            Arrays.sort(integerArray, Collections.reverseOrder());
            //make sure n to n/2
            int counter = 0;
            for(int i=(size/2);i>0;i--) {
                arr[counter] = (int) integerArray[i];
                counter++;
            }
            //make sure 1 to n/2
            for(int i=(size-1);i>(size/2);i--) {
                arr[counter] = (int) integerArray[i];
                counter++;
            }
            // ensure copy array equals arr
            for(int i=0;i<size;i++) {
                arrCopy[i] = arr[i];
            }
            
            
            
            // heapsort
            start = System.currentTimeMillis();
            heapsort();
            finish = System.currentTimeMillis();
            times[0] += (finish-start)*10;
            
           
            // quicksort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort(0,size-1);
            finish = System.currentTimeMillis();
            times[1] += (finish-start)*10;
            
            
            // quicksort3
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort3(0,size-1);
            finish = System.currentTimeMillis();
            times[2] += (finish-start)*10;
            
            
            
            // quicksort4
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort4(0,size-1);
            finish = System.currentTimeMillis();
            times[3] +=  (finish-start)*10;
            
            
            // quicksort5
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            quicksort5(0,size-1);
            finish = System.currentTimeMillis();
            times[4] +=  (finish-start)*10;
            
            // naturalMergesort
            for (int t=0; t<size; t++) arr[t] = arrCopy[t];
            start = System.currentTimeMillis();
            naturalMergesort();
            finish = System.currentTimeMillis();
            times[5] +=  (finish-start);
            
           
        }
        
        System.out.println(" done");
        System.out.println("Average Running times: ");
        System.out.println("heapsort(): " + (times[0]/30)*.001 + " seconds");
        System.out.println("quicksort(): " + (times[1]/30)*.001 + " seconds");
        System.out.println("quicksort3(): " + (times[2]/30)*.001 + " seconds");
        System.out.println("quicksort4(): " + (times[3]/30)*.001 + " seconds");
        System.out.println("quicksort5(): " + (times[4]/30)*.001 + " seconds");
        System.out.println("naturalMergesort(): " + (times[5]/30)*.001 + " seconds");
    /*
    * quicksort 3 with the modified addition of checking whether the list is sorted had the fasted runing time while heapsort() had the slowest
    * almost 3 times slower than quicksort 3 both modified pivot versions of quicksort did not perform as well as the original quicksort
    * in testing heap sort did not perform better in reverse sorted arrays with quicksort3 still performing 49 times better
    *heapsort(): 0.11900000000000001 seconds
    *quicksort(): 0.039 seconds
    *quicksort3(): 0.031 seconds
    *quicksort4(): 0.23800000000000002 seconds
    *quicksort5(): 0.082 seconds
    *naturalMergesort(): 0.04 seconds
    */   
    }
    // task 4 takes in a msg that gives a a description of the given task
    // arraySize which dictates the size of each array and a int k 
    // which determines either how many exchanges each array to be sorted 
    // will have or how far each integer is from where it should be 
    public static void task4(String msg, int arraySize,int k) {
        System.out.println(msg);
        size = arraySize;
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        // hold the run times of each sorting algorithm in 
        // order quicksort3 -> heapsort -> mergesort -> naturalMergesort -> insertionsort
        long[] times = new long[5];
        long start,finish;
        
        randomGenerator = new Random();
        // fill array
	random = size*10;
        // fill an array of size 10,000,000 of random int values
        for(int i= 0;i<size;i++) {
            arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
        }
        // make k exchanges
        for (int i = 0; i < k; i++) {
	    int j  = randomGenerator.nextInt(size);
	    int t  = randomGenerator.nextInt(size);
	    exchange(j, t);
	}
        //ensure copy equals the new k-exchange array
        for (int t=0; t<size; t++) arrCopy[t] = arr[t];
        
        System.out.print("Sorting array of size 10,000,000 random int values that has k exchanges...");
        // perform the various sorting algorithms and gather running times
        
        // quicksort3
        start = System.currentTimeMillis();
        quicksort3(0,size-1);
        finish = System.currentTimeMillis();
        times[0] += (finish-start);
        
        // heapsort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        times[1] += (finish-start);
        
        // mergesort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        mergesort(0,size);
        finish = System.currentTimeMillis();
        times[2] += (finish-start);
        
        // naturalMergesort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        naturalMergesort();
        finish = System.currentTimeMillis();
        times[3] += (finish-start);
        
        // insertsort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        insertionSort();
        finish = System.currentTimeMillis();
        times[4] += (finish-start);
        
        System.out.println(" done");
        System.out.print("Sorting an array 10,000,000 random integers at most k distance from destined place... ");
        // fill an array of size 10,000,000 of random int values
        for(int i= 0;i<size;i++) {
            arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
        }
        quicksort3(0,size-1);
        kshift(k);
        
        // quicksort3
        start = System.currentTimeMillis();
        quicksort3(0,size-1);
        finish = System.currentTimeMillis();
        times[0] += (finish-start);
        
        // heapsort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        times[1] += (finish-start);
        
        // mergesort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        mergesort(0,size);
        finish = System.currentTimeMillis();
        times[2] += (finish-start);
        
        // naturalMergesort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        naturalMergesort();
        finish = System.currentTimeMillis();
        times[3] += (finish-start);
        
        // insertsort
        for (int t=0; t<size; t++) arr[t] = arrCopy[t];
        start = System.currentTimeMillis();
        insertionSort();
        finish = System.currentTimeMillis();
        times[4] += (finish-start);
        
        
        System.out.println(" done");
        System.out.println("Average Running times: for K = " + k);
        System.out.println("quicksort3(): " + (times[0]/2) + " milliseconds");
        System.out.println("heapsort(): " + (times[1]/2) + " milliseconds");
        System.out.println("mergsort(): " + (times[2]/2) + " milliseconds");
        System.out.println("naturalMergeSort(): " + (times[3]/2) + " milliseconds");
        System.out.println("insertionSort(): " + (times[4]/2) + " milliseconds");
        
        
    }
    public static void main(String[] args) {
        
       
        task1("Completing task1... ",  10000000);
        task2("Completing task2... " , 10000000);
        task3("completing task3... " ,  1000000);
        // reduced to 1,000,000 due to slow running times
        task4("Completing task4... " ,  1000000, 30);
        task4("Completing task4... " ,  1000000, 100);
        task4("Completing task4... " ,  1000000, 200);
        task4("Completing task4... " ,  1000000, 300);
        
    }
    
    // helper functions
    // will return true if each value is less than the value 
    private static boolean isSorted(int low, int high) {
        
        for(int i =low;i<high;i++) {
            if(arr[i]>arr[i+1]) {
                return false;
            }
        }
        return true;
    }
    // finds a median between 3 ints
    public static int findMedian(int x, int y, int z) {
        return max(min(x,y), min(max(x,y),z));
    }
    public static int max(int x, int y) {
        if(x>=y) {
            return x;
        }else 
            return y;
    }
    public static int min(int x, int y) {
        if(x<=y) {
            return x;
        }else 
            return y;
    }
    // finds the median of nine numbers grouped in three
    public static int findMedian(int[] data) {
        int first = findMedian(data[0],data[1],data[2]);
        int second = findMedian(data[3],data[4],data[5]);
        int third = findMedian(data[6],data[7],data[8]);
        return findMedian(first,second,third);
    }
    // shifte arr by k making each value a k distance away from correct spot
    public static void kshift(int k) {
        int[] shiftedArr = new int[size];
        
        for(int i=0; i<size;i++) {
            shiftedArr[(i+k)%size] = arr[i];
        }
        for(int i=0;i<size;i++) {
            arr[i] = arrCopy[i] = shiftedArr[i];
        }
    }
    
    
    
}