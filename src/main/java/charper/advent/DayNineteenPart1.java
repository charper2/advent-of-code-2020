package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayNineteenPart1 {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayNineteenInput.txt";
    private Map<String, List<String>> rules = new HashMap<>();
    private List<String> messages = new ArrayList<>();

    public DayNineteenPart1() {
        System.out.println(run(FILE_PATH));
    }

    private Long run(String filePath) {
        getMessagesAndRules(filePath);

        Long total = 0L;
        List<String> valid = resolveRule("0");
        for (String message: messages) {
            total += doesMatch(valid, message);
        }
        return total;
    }
    
    // return 1 if match 0 if not
    private int doesMatch(List<String> valid, String message) {
        if (message.length() == valid.get(0).length() && valid.contains(message)) {
            return 1;
        }
        return 0;   
    }

    private List<String> resolveRule(String ruleNumber) {
        List<String> combinations = new ArrayList<>();
        String ruleToSolve = rules.get(ruleNumber).get(0);
        List<String> operands = Arrays.asList(ruleToSolve.split(" \\| "));
        for (String operand: operands) {
            List<String> matches = new ArrayList<>();
            List<String> items = Arrays.asList(operand.split(" "));
            for (String item : items) {
                List<String> rule = rules.get(item);
                if (isResolved(rule)) {
                    List<String> product = getCombos(matches, rule);
                    matches.clear();
                    matches.addAll(product);
                }
                else {
                    List<String> cRule = resolveRule(item);
                    rules.put(item, cRule);
                    List<String> product = getCombos(matches, cRule);
                    matches.clear();
                    matches.addAll(product);
                }
            }
            combinations.addAll(matches);
        }
        return combinations;
    }

    private boolean isResolved(List<String> rule) {
        return !containsDigit(rule.get(0));
    }

    private boolean containsDigit(String str) {
        char[] chars = str.toCharArray();
        for(char c: chars) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getCombos(List<String> array1, List<String> array2) {
        List<String> combos = new ArrayList<>();
        for (String i: array1) {
            for (String j: array2) {
                combos.add(i + j);
            }
        }
        if (combos.isEmpty()) {
            combos.addAll(array1);
            combos.addAll(array2);
        }
        return combos;
    }
    
    public List<String> getMessagesAndRules(String filePath) {
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                if (instruction.length() > 0 && Character.isLetter(instruction.charAt(0))) {
                    messages.add(instruction);
                }
                else if (instruction.length() > 0 && Character.isDigit(instruction.charAt(0))) {
                    String[] strs = instruction.split(": ");
                    String key = strs[0];
                    String req = strs[1].trim().replace("\"", "");
                    this.rules.put(key, Arrays.asList(req));
                }
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return messages;
    }

}
