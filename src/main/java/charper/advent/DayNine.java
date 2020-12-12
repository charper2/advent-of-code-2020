package charper.advent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static charper.advent.Utils.getLongs;

public class DayNine {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayNineInput.txt";
    private static final int PREAMBLE_SIZE = 25;

    public DayNine() {
        List<Long> numbers = getLongs(FILE_PATH);
        Long answer1 = run(numbers);
        System.out.println("1: " + answer1);
        Long answer2 = run2(numbers, answer1);
        System.out.println("2: " + answer2);
    }

    public Long run(final List<Long> numbers) {  
        for (int i = PREAMBLE_SIZE+1 ; i < numbers.size(); i++) {
            Long valueToCheck = numbers.get(i);
            List<Long> slice = numbers.subList(i - PREAMBLE_SIZE - 1, i);
            if (!isValid(valueToCheck, slice)) {
                return valueToCheck;
            }
        }
        return null;
    }

    public Long run2(final List<Long> numbers, final Long required) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i+1; j <= numbers.size(); j++) {
                List<Long> slice = numbers.subList(i, j);
                Long sum = slice.stream().collect(Collectors.summingLong(Long::longValue));
                if (sum.equals(required)) {
                    return getSumOfMaxMinInRange(slice);
                } else if (sum > required) {
                    break;
                }
            }
        }
        return null;
    }

    private Long getSumOfMaxMinInRange(final List<Long> slice) {
        Long max = slice.stream().max(Comparator.comparing(Long::valueOf)).get();
        Long min = slice.stream().min(Comparator.comparing(Long::valueOf)).get();
        return min + max;
    }
    
    private boolean isValid(Long toCheck, List<Long> numbers) {
        // @TODO similar to Day One, could refactor as util
        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == toCheck) {
                    return true;
                }
            }
        }
        return false;
    }
}
