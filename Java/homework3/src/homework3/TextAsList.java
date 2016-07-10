package homework3;
import java.util.*;

/**
 *
 * @author josephtleiferman
 */

class LLNode {
    char value;
    LLNode next;
    
    
    // constructor to assign a new node its value
    public LLNode(char data) {
        value = data;
    }
    // this is for testing purpuses to check links between nodes
    @Override
    public String toString() {
        return "" + value + "|" + this.next.value + "\n";
    }
    
}
public class TextAsList {
    
    // head of the internal linked list
    LLNode head;
    // internal linked list
    LinkedList<LLNode> LLNodeList = new LinkedList<>();
    
    //Constructor call with no parameters creates head with value null
    public TextAsList() {
        head = null;
    }
    //Constructor call with a string as a parameter
    public TextAsList(String s) {
       // will only add values if string is length greater than 1 
       if (s.length() > 1) {
           // adds the head LLNode to LLNodeList first
           head = new LLNode(s.charAt(0));
           head.next = new LLNode(s.charAt(1));
           LLNodeList.addFirst(head);
           // will iterate through remaing string values
           for(int i = 1; i<s.length(); i++) {
               // checks to for last element and sets next value to null
              if (i == s.length()-1) {
                  LLNode temp = new LLNode(s.charAt(i));
                  temp.next = null;
                  LLNodeList.addLast(temp);
                  
              }
              else {
                LLNode temp = new LLNode(s.charAt(i));
                temp.next = new LLNode(s.charAt(i+1));
                LLNodeList.addLast(temp);
              }
           }
        
       }
       // case when string has one character
       else if (s.length()==1) {
           head = new LLNode(s.charAt(0));
           head.next = null;
           LLNodeList.addFirst(head);
                   
       }
       // case when String is empty
       else {
           head = null;
       }
       
       
  
    }
    //Constructor call with char[] as a parameter
    public TextAsList(char[] c) {
        
        // case when char[] has more than 1 item 
       if (c.length > 1) {
           // adds the head LLNode to LLNodeList first
           head = new LLNode(c[0]);
           head.next = new LLNode(c[1]);
           LLNodeList.addFirst(head);
           // will iterate through remaing string values
           for(int i = 1; i<c.length; i++) {
               // checks to for last element and sets next value to null
              if (i == c.length-1) {
                  LLNode temp = new LLNode(c[i]);
                  temp.next = null;
                  LLNodeList.addLast(temp);
                  
              }
              else {
                LLNode temp = new LLNode(c[i]);
                temp.next = new LLNode(c[i+1]);
                LLNodeList.addLast(temp);
              }
           }
        
       }
       // case when there is axactly 1 item in char[]
       else if (c.length==1) {
           head = new LLNode(c[0]);
           head.next = null;
           LLNodeList.addLast(head);
                   
       }
       // case when char[] is empty
       else {
           head = null;
       }
    }
    
    // length() returns an integer corresponding to the linked lists length
    public int length() {
        
        return LLNodeList.size();
    }
    // asString() converts internal linked list to a string and returns its value
    public String asString() {
        // listAsString will hold LLNode values 
        String listAsString = "";
        for (LLNode node: LLNodeList) {
            
            listAsString+= node.value;
        }
        return listAsString;
    }
    // toCharArray() converts internal linked list to array of characters
    public char[] toCharArray() {
        char[] listToCharArray = new char[this.length()];
        int counter = 0;
        for (LLNode node: LLNodeList) {
            listToCharArray[counter] = node.value;
            counter+=1;
            
        }
        return listToCharArray;
    }
    // append can append a char, char[], or a string to linked list
    public void append(char c) {
        // newNode holds value of node being added
        LLNode newNode = new LLNode(c);
        // get last node in LLNodeList
        LLNode lastNode = LLNodeList.getLast();
        // set last node.next to equal new node
        lastNode.next = newNode;
        // add the new nod to the end of LLNodeList
        LLNodeList.addLast(newNode);
    }
    //append can append a char, char[], or a string to linked list
    public void append(char[] c) {
        for(char x: c) {
            // newNode holds value of node being added
            LLNode newNode = new LLNode(x);
            // get last node in LLNodeList
            LLNode lastNode = LLNodeList.getLast();
            // set last node.next to equal new node
            lastNode.next = newNode;
            // add the new nod to the end of LLNodeList
            LLNodeList.addLast(newNode);
            
            
        }
    }
    //append can append a char, char[], or a string to linked list
    public void append(String s) {
        char[] c = s.toCharArray();
         for(char x: c) {
            // newNode holds value of node being added
            LLNode newNode = new LLNode(x);
            // get last node in LLNodeList
            LLNode lastNode = LLNodeList.getLast();
            // set last node.next to equal new node
            lastNode.next = newNode;
            // add the new nod to the end of LLNodeList
            LLNodeList.addLast(newNode);
         }
    }
    
    // reverse() reverses order of linked list 
    public void reverse() {
        
        // will be used to create new reverse LinkedList
        LinkedList<LLNode> tempNodeList = new LinkedList<>();
        
        // checks for remaining nodes
        while (LLNodeList.size()>0) {
            // if last node 
           if (LLNodeList.size() == 1) {
               // last node will hold current last node in LLNodeList
               LLNode lastNode = LLNodeList.getLast();
               // will assign its next value to be null since it is the last node
               lastNode.next = null;
               // adds last node to tempNodeList
               tempNodeList.addLast(lastNode);
               break;
           }
           // lastNode holds last value of LLNodeList
           LLNode lastNode = LLNodeList.getLast();
           // removes this node from LLNodeList
           LLNodeList.removeLast();
           // assigns current LastNode to next of the previous last node
           lastNode.next = LLNodeList.getLast();
           // adds this to tempNodeList
           tempNodeList.addLast(lastNode);
           
            
        }
        // asigns LLNodeList to new tempNodeList
        LLNodeList = tempNodeList;
    }
    
    // any additional methods will be added below and will be left private

    
}
    

    
    
