package homework3;
import java.util.Arrays;

/**
 *
 * @author josephtleiferman
 */
public class IntArrayMaster {
    
    // private to prevent creation of objects
    private IntArrayMaster() {}
    
    // returns an int k that is less than or equal to the length of list A 
    // and is not in the the list A
    
    public static int findMissing(int[] A) {
        // create a empty list of zeros will correspond to which values are in
        // the list A
        int[] missingK = new int[A.length+1];
        
        for (int x: A) {
            
            // for each value change its corresponding value in missingK
            missingK[x] = 1;
        }
        for (int x = 0; x<missingK.length;x++) {
            // look for the first zero corresponding to the missing k int
            if (missingK[x] == 0) {
                return x;
            }
        }
        // all values are present except for n
        return A.length;
    }
    
    // find the Kth return the kth greatest element in A
    public static int findKth(int[] A, int k) {
        // sort array in nlogn
        Arrays.sort(A);
     
        return A[k-1];
    }
    // classic selection sort algorithm
    public static void selectionSort(int[] A) {
        // increment through array A
        for (int i = 0; i<A.length -1; i++) {
            int index = i;
            for (int k= i+1 ; k<A.length; k++) {
                if(A[k]<A[index]) {
                    index = k;
            
                }
                int smallInt = A[index];
                A[index] = A[i];
                A[i] = smallInt;
                
            }
            
        }
    }
    // classic merge sort algorithm
    public static void mergeSort(int[] A) {
        if (A==null || A.length==0) { 
            return;
        }
        mergeSort(A,0,A.length-1);
    }
    
    // classic merge sort
    public static void mergeSort(int[] A, int x, int y) {
        // no need to sort if A only has one item
        if (A.length > 1) {
            // find mid by dividing length by two
            int mid = A.length/2;
            // split into into two arrays for left and right side
            int[] leftArray = Arrays.copyOfRange(A, x, mid);
            int[] rightArray = Arrays.copyOfRange(A,mid,y+1);
            // recursive step 
            mergeSort(leftArray);
            mergeSort(rightArray);
            // merge the sorted parts together 
            merge(A,leftArray,rightArray);
        }
    }
    // private method merge is used in mergeSort to combine sorted lists
    private static void merge(int[] a, int[] l, int[] r) {
        // holds the total amount of elements
        int totElem = l.length + r.length;
        int i,li,ri;
        i = li = ri = 0;
        while ( i < totElem) {
            // recombine lists while left index is less then right index
            if ((li < l.length) && (ri<r.length)) {
                if (l[li] < r[ri]) {
                    a[i] = l[li];
                    i++;
                    li++;
                }
                else {
                    a[i] = r[ri];
                    i++;
                    ri++;
                }
            }
            else {
                if (li >= l.length) {
                    while (ri < r.length) {
                        a[i] = r[ri];
                        i++;
                        ri++;
                    }
                }
                if (ri >= r.length) {
                    while (li < l.length) {
                        a[i] = l[li];
                        li++;
                        i++;
                    }
                }
            }
        }
        

    }

}
