
/* 
* charMaster.java contains the class charMaster and all of its
* supported methods including : char2str, str2char, reverse, count, clone, equals
* @author josephLeiferman
*/
public class charMaster {
    public static String char2str( char[] x) {
        // creates a new string out of the char array
        String charString = "";
        for (int i = 0; i<x.length;i++){
            charString = charString + x[i];
        }
        return charString;
    }
    
    // creates a character arrary from a string
    public static char[] str2char(String x) {
        char[] charArray = x.toCharArray();
        return charArray;
    }
    
    // reverse order of a string
    public static char[] reverse(char[] x) {
        // creates an array of equal size to x
        char[] reverseArray = new char[x.length];
        //index starts from last char in x
        int index = x.length-1;
        // increments through new array adding the
        // last char in x as it goes
        for (int i = 0; i<(x.length); i++) {
            
            reverseArray[i] = x[index];
            --index;
        }
        return reverseArray;
    }
    
    // return the number of time char c appears in x
    public static int count( char c, char[] x) {
        // holds the amount of times c appears
        int numOfChar = 0;
        for (int i =0;i<(x.length); i++) {
            if (x[i] == c) {
                numOfChar++;
            }
        }
        return numOfChar;
    }
    // returns clone of array x
    public static char[] clone(char[] x) {
        char[] newCharArray = x;
        return newCharArray;
    }
    
    // checks to see if two arrays x,y have the same content
    public static boolean equals(char[] x, char[] y) {
        boolean equalTo = true;
        // checks char array legnth of x,y 
        if ((x.length) != (y.length)) {
            equalTo = false;
        }
        if (equalTo == true) {
            for (int i = 0; i<(x.length);i++){
                if( x[i] != y[i]) {
                    equalTo = false;
                }
            }
        }
        return equalTo;
    }
}
