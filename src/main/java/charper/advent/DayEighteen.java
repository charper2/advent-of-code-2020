package charper.advent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static charper.advent.Utils.getCharList;

public class DayEighteen {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayEighteenInput.txt";
    private static final Map<Character, Integer> PRECEDENCE;
    static {
        PRECEDENCE = new HashMap<>();
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('*', 0);
    };
    
    public DayEighteen() {
        System.out.println(run(FILE_PATH));
    }

    private Long run(String filePath) {
        List<List<Character>> problems = getCharList(filePath);
        Long total = 0L;
        for (List<Character> problem: problems) {
            total += evaluateProblem(problem);
        }
        return total;
    }
    
    private static Long evaluateProblem(List<Character> problem) {
        List<Character> rpn = convertInfixToRpn(problem);
        return evaluateRPN(rpn);
    }
    
    private static List<Character> convertInfixToRpn(List<Character> infixInput) {
        List<Character> rpnOutput = new ArrayList<>();
        Deque<Character> stack = new ArrayDeque<>();
        
        for (Character c : infixInput) {
            if (isMathOperation(c)) {
                // While stack not empty AND stack top element 
                // is an operator
                while (!stack.isEmpty() && isMathOperation(stack.peek())) {                   
                    // if c has higher precedence than the top of the stack
                    // then add it to the output
                    if (comparePrecedence(c, stack.peek()) <= 0) {
                        rpnOutput.add(stack.pop());   
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack
                stack.push(c);
            }
            else if (isStartBracket(c)) {
                stack.push(c);
            } 
            else if (isEndBracket(c)) {                
                while (!stack.isEmpty() && !isStartBracket(stack.peek())) 
                {
                    rpnOutput.add(stack.pop()); 
                }
                stack.pop(); 
            } 
            // If token is a number
            else {
                rpnOutput.add(c); 
            }
        }
        while (!stack.isEmpty()) {
            rpnOutput.add(stack.pop()); 
        }
        return rpnOutput;
    }
    
    private static int comparePrecedence(Character operand1, Character operand2) {
        if (!isMathOperation(operand1) || !isMathOperation(operand2)) {
            throw new IllegalArgumentException("Invalid tokens: " + operand1
                    + " " + operand2);
        }
        return PRECEDENCE.get(operand1) - PRECEDENCE.get(operand2);
    }
    
    private static long evaluateRPN(List<Character> problem) {
        Deque<String> stack = new ArrayDeque<>();
         
        for (Character c : problem) {
            if (!isMathOperation(c)) {
                stack.push(Character.toString(c));                
            }
            else {
                long val1 = Long.valueOf(stack.pop());
                long val2 = Long.valueOf(stack.pop());
                 
                if (isAddition(c)) {
                    stack.push(Long.toString(val1 + val2));
                }            
                else if (isProduct(c)) {
                    stack.push(Long.toString(val1 * val2));
                }                                             
            }                        
        }        
        return Long.valueOf(stack.pop());
    }
    
    private static boolean isStartBracket(Character c) {
        return c.equals('(');
    }

    private static boolean isEndBracket(Character c) {
        return c.equals(')');
    }

    private static boolean isProduct(Character c) {
        return c.equals('*');
    }

    private static boolean isAddition(Character c) {
        return c.equals('+');
    }

    private static boolean isMathOperation(Character c) {
        return isAddition(c) || isProduct(c);
    }
}
