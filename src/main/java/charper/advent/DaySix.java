package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class DaySix {

    public DaySix() {
        run2();
    }

    public void run2() {
        int numValid = 0;
        try {
            File input = new File("C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DaySixInput.txt");
            Scanner scanner = new Scanner(input);
            scanner.useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE));
            while(scanner.hasNext()) {
                String group = scanner.next();
                group = group.trim();
                String lines[] = group.split("\\r?\\n");
                Set<Character> intersection = null;
                for (String str : lines) {
                    Set<Character> chars = new HashSet<Character>();
                    for (char c : str.toCharArray()) {
                        chars.add(c);
                    }
                    System.out.println(chars);
                    if (intersection == null) {
                        intersection = new HashSet<Character>();
                        intersection.addAll(chars);
                    } else {
                        intersection.retainAll(chars);
                    }
                }
                numValid += intersection.size();
                System.out.println(numValid);
            }
            System.out.println(numValid);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("here");
        }
    }

    public void run() {
        int numValid = 0;
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE));
        while(scanner.hasNext()) {
            String group = scanner.next();
            group = group.replaceAll("\\s+","");
            Set<Character> chars = new HashSet<Character>();
            for (char c : group.toCharArray()) {
                chars.add(c);
            }
            numValid += chars.size();
        }
        System.out.println(numValid);
        scanner.close();
    }
}
