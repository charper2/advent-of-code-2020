package charper.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static charper.advent.Utils.getIntegerList;

public class DayTen {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayTenInput.txt";

    public DayTen() {
        List<Integer> numbers = getIntegerList(FILE_PATH);
        Integer answer1 = run(numbers);
        System.out.println("1: " + answer1);
        long answer2 = run2(numbers);
        System.out.println("2: " + answer2);
    }

    public Integer run(final List<Integer> numbers) {
        Integer currentRating = 0; 
        int numberOnes = 0;
        int numberThrees = 1; 
        List<Integer> orderedJoltages = numbers.stream().sorted().collect(Collectors.toList());
        for (Integer jolt : orderedJoltages) {
            if (jolt - currentRating == 1) {
                numberOnes++;
            } else if (jolt - currentRating == 3) {
                numberThrees++;
            }
            currentRating = jolt;
        }
        return numberOnes * numberThrees;
    }

    // alternative to 1 used for 2
    public List<Integer> getDiffs(final List<Integer> numbers) {
        Integer currentRating = 0; 
        List<Integer> diffs = new ArrayList<>();
        List<Integer> orderedJoltages = numbers.stream().sorted().collect(Collectors.toList());
        for (Integer jolt : orderedJoltages) {
            if (jolt - currentRating == 1) {
                diffs.add(1);
            } else if (jolt - currentRating == 3) {
                diffs.add(3);
            }
            currentRating = jolt;
        }
        return diffs;
    }

    public long run2(final List<Integer> numbers) {
        long numberPaths = 1;
        List<Integer> diffs = getDiffs(numbers);
        // find number of consecutive 1's and get permutations
        int consecutives = 0;
        for (Integer diff : diffs) {
            if (diff == 1) {
                consecutives++;
            }
            else {
                if (consecutives >= 1) {
                    numberPaths = numberPaths * perms(consecutives);
                    consecutives = 0;
                } 
            }
        }
        if (consecutives >= 1) {
            numberPaths = numberPaths * perms(consecutives);
        } 
        return numberPaths;
    }

    public Long perms(int n) {
        if (n == 4) { return 7L; }
        if (n == 3) { return 4L; }
        if (n == 2) { return 2L; }
        if ( n > 4) { System.out.println("here oops"); }
        return 1L;
    }
}
