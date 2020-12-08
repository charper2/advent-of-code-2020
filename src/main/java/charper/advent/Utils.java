package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    
    private Utils() {}
    
    public static List<Integer> getIntegers() {
        List<Integer> integers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            integers.add(scanner.nextInt());
        }
        scanner.close();
        return integers;
    }

    public static List<String> getLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                lines.add(instruction);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return lines;
    }
}