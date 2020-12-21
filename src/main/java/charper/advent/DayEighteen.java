package charper.advent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import static charper.advent.Utils.getCharList;

public class DayEighteen {
    private static String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayEighteenInput.txt";
    
    public DayEighteen() {
        System.out.println(run(FILE_PATH));
    }

    public Long run(String filePath) {
        List<List<Character>> problems = getCharList(filePath);
        Long total = 0L;
        for (List<Character> problem: problems) {
            total += evaluateProblem(problem);
        }
        return total;
    }

    private static Long evaluateProblem(List<Character> problem) {
        int numStartBrackets = 0;
        int numEndBrackets = 0;
        Long currentValue = null;
        Integer startBracketIndex = null;
        Character lastOperation = null;
        // Deque<Character> stack = new ArrayDeque<Character>();
        // for (int i = 0; i < problem.size(); i++) {
        //     stack.push(problem.get(i));
        // }
        // Character c = stack.pop();

        for (int i = 0; i < problem.size(); i++) {
            Character c = problem.get(i);
            if (startBracketIndex == null) {
                if (Character.isWhitespace(c)) {
                    continue;
                }
                else if (isMathOperation(c)) {
                    lastOperation = c;
                }
                else if (Character.isDigit(c)) {
                    Long numeric = (long)Character.getNumericValue(c);
                    if (currentValue == null) {
                        currentValue = numeric;
                    }
                    else if (isAddition(lastOperation)) {
                        return
                    }
                    // else if (isProduct(lastOperation)) {
                    //     currentValue *= numeric;
                    // }
                }
                else if (isStartBracket(c)) {
                    startBracketIndex = i;
                    numStartBrackets++;
                }
            }            
            else if (isStartBracket(c)) {
                numStartBrackets++;
            } 
            else if (isEndBracket(c)) {
                numEndBrackets++;
                if (numEndBrackets == numStartBrackets) {
                    List<Character> innerProblem = problem.subList(startBracketIndex+1, i);
                    if (lastOperation == null) {
                        currentValue = evaluateProblem(innerProblem);
                    }
                    else if (isAddition(lastOperation)) {
                        currentValue += evaluateProblem(innerProblem);
                    }
                    else if (isProduct(lastOperation)) {
                        currentValue *= evaluateProblem(innerProblem);
                    }
                    startBracketIndex = null;
                    numEndBrackets = 0;
                    numStartBrackets = 0;
                }
            }
        }
        return currentValue;        
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
