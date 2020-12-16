package charper.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayFifteen {
    
    public DayFifteen() {
        List<Integer> seedNumbers = Arrays.asList(9,12,1,4,17,0,18);
        System.out.println(run(seedNumbers));
    }

    private static Integer run(List<Integer> seedNumbers) {
        Map<Integer, List<Integer>> previousNumberMap = new HashMap<>();
        for (int i = 1; i <= seedNumbers.size(); i++) {
            previousNumberMap.put(seedNumbers.get(i-1), Arrays.asList(i));
        }
        int i = seedNumbers.size() + 1;
        Integer previousNumber = seedNumbers.get(seedNumbers.size() - 1);
        while (i <= 30000000) {
            List<Integer> last2Turns = previousNumberMap.get(previousNumber);
            Integer nextNumber;
            if (last2Turns.size() >= 2) {
                nextNumber = last2Turns.get(1) - last2Turns.get(0);
            } else {
                nextNumber = 0;
            }
            List<Integer> newNumbers = new ArrayList<>();
            if (previousNumberMap.get(nextNumber) != null) {
                newNumbers.addAll(previousNumberMap.get(nextNumber));
            }
            if (newNumbers.size() >= 2) {
                newNumbers.remove(0);
            }
            newNumbers.add(i);
            previousNumberMap.put(nextNumber, newNumbers);
            previousNumber = nextNumber;
            i++;
        }
        return previousNumber;
    }
    
}
