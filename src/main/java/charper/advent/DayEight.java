package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DayEight {

    public DayEight() {
        run2();
    }

    public void run() {
        long acc = 0;
        List<String> instructions = getInstructions();
        Set<Integer> pastLocations = new HashSet<>();
        int location = 0;
        while (true) {
            if (pastLocations.contains(location)) {
                break;
            }
            pastLocations.add(location);
            String instruction = instructions.get(location);
            String action = instruction.substring(0, 3);
            Integer number = Integer.valueOf(instruction.substring(3).trim());
            if (action.equals("nop")) {
                location++;
                continue;
            }
            if (action.equals("acc")) {
                acc = acc + number;
                location++;
                continue;
            }
            if (action.equals("jmp")) {
                location = location + number;
            }
        }
        System.out.println(acc);
    }

    public void run2() {
        long acc = 0;
        List<String> instructions = getInstructions();
        List<Integer> pastLocations = new ArrayList<>();
        int location = 0;
        int instToChange = 0;
        int numOfNopsOrJmps = 0;
        while (true) {
            if (location >= instructions.size()) {
                break;
            }
            if (pastLocations.contains(location)) {
                // start again but change the next nop or jmp
                pastLocations.clear();
                instToChange++;
                numOfNopsOrJmps = 0;
                location = 0;
                acc = 0;
            }
            pastLocations.add(location);
            String instruction = instructions.get(location);
            String action = instruction.substring(0, 3);
            Integer number = Integer.valueOf(instruction.substring(3).trim());
            if (action.equals("nop")) {
                if (numOfNopsOrJmps == instToChange) {
                    location = location + number;
                } else {
                    location++;
                }
                numOfNopsOrJmps++;
                continue;
            }
            if (action.equals("acc")) {
                acc = acc + number;
                location++;
                continue;
            }
            if (action.equals("jmp")) {
                if (numOfNopsOrJmps == instToChange) {
                    location++;
                } else {
                    location = location + number;
                }
                numOfNopsOrJmps++;
            }
        }
        System.out.println(acc);
    }

    private List<String> getInstructions() {
        List<String> instructions = new ArrayList<>();
        try {
            File input = new File("C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayEightInput.txt");
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                instructions.add(instruction);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return instructions;
    }
    
}
