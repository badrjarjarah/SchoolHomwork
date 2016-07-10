import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class myParser {

    
    public static void main(String[] args) {
        
        Stack container = new Stack<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter expression: ");
        char[] splitExpression = sc.nextLine().toCharArray();
        try {
            int result = evaluate(splitExpression);
            System.out.println(result);
        } 
        catch (EmptyStackException e) {
            System.out.println("Invalid expression");
        }
        catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
                  
    }
       
    // evaluates an expression by converting infix to postfix and will return 
    // an int, if invalid formatin will throw exception and an error message will be printed
    private static int evaluate(char[] expression) throws java.util.EmptyStackException
    {
        
 
         // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<>();
 
        // Stack for Operators: 'operators'
        Stack<Character> operators = new Stack<>();
 
        for (int i = 0; i < expression.length; i++)
        {
            
             // Current token equals whitespace: skip
            if (expression[i] == ' ')
               
                continue;
            // current token is a variable
            if (Character.isAlphabetic(expression[i])) 
                throw new UnsupportedOperationException("Invalid expresssion");
    
            // Current expression is a number, push it to stack for numbers
            if (expression[i] >= '0' && expression[i] <= '9')
            {
                StringBuilder wholeNumber = new StringBuilder();
                // There may be more than one digits in number
                while (i < expression.length && expression[i] >= '0' && expression[i] <= '9')
                    wholeNumber.append(expression[i++]);
                values.push(Integer.parseInt(wholeNumber.toString()));
                i--;
            }
 
            // current token equals '(': push onto operator stack
            else if (expression[i] == '(')
                operators.push(expression[i]);
 
            // token equals ')': solve all previous elements in stacks
            else if (expression[i] == ')')
            {
                while (operators.peek() != '(')
                  values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                operators.pop();
            }
 
            // current token is an operator
            else if (expression[i] == '+' || expression[i] == '-' ||
                     expression[i] == '*' || expression[i] == '/')
            {
                // While top of 'operators' has same or greater precedence to current
                // expression, which is an operator. Apply operator on top of 'operators'
                // to top two elements in values stack
                while (!operators.empty() && hasPrecedence(expression[i], operators.peek()))
                  values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
 
                // Push current expression to 'operators'.
                operators.push(expression[i]);
            }
        }
        // expression has been parsed apply operators
        while (!operators.empty())
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
 
        // The result is contained in top of the stack values
        return values.pop();
    }
 
    
    // returns true only if operation 2 has a higher or some 
    // precedence as operation 1
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op1 == '(' || op2 == '(')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
 
    // applys an operation to two integers
    public static int applyOperation(char op, int b, int a)
    {       
        switch (op)
        {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '/':
            if (b == 0)
                throw new
                UnsupportedOperationException("Error division by zero");
            return a / b;
        }
        return 0;
    }
}
        
       
 


    

