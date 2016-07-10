package homework3;


import static homework3.IntArrayMaster.*;
import java.util.Arrays;
/**
 *
 * @author josephtleiferman
 */
      
public class Homework3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TextAsList LinkedList = new TextAsList("Hello");
        System.out.println(LinkedList.asString());
        LinkedList.reverse();
        LinkedList.reverse();
        LinkedList.reverse();
        System.out.println(LinkedList.asString());
        
        
        
        System.out.println(LinkedList.length());
        char[] characters = {'a','b','c'};
        TextAsList arrayList = new TextAsList(characters);
        System.out.println(arrayList.asString());
        System.out.println(arrayList.length());
        char[] newCharacters; 
        newCharacters = arrayList.toCharArray();
        arrayList.append("abcdedfgasdf");
        System.out.println(arrayList.asString());
        System.out.println(arrayList instanceof TextAsList);
        LLNode test = new LLNode('d');
        
        System.out.println("~~~~~~~~~~~~~~~~");
        int[] intList = {1,2,4,0};
        int L = findMissing(intList);
        int k = findKth(intList, 3);
        System.out.println(L);
        mergeSort(intList);
        System.out.println(Arrays.toString(intList));
                
        
        
            
        
        
  
    }
    
}
