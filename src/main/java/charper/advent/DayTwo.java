package charper.advent;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DayTwo {
    
    public DayTwo() {
        run();
    }
    
    public void run() {
        int numValid = 0;
        Scanner scanner = new Scanner(System.in);
        String pattern = "(\\d+)-(\\d+) ([a-z]): (.*)";
        Pattern r = Pattern.compile(pattern);
        while(scanner.hasNext()) {
            String str = scanner.nextLine();
            Matcher m = r.matcher(str);
            if (m.find()) {
                Integer min = Integer.parseInt(m.group(1));
                Integer max = Integer.parseInt(m.group(2));
                char c = m.group(3).charAt(0);
                String password = m.group(4);
                int numOccurences = 0;
                for (int i=0; i < password.length(); i++) {
                   if (password.charAt(i) == c) {
                       numOccurences++;
                   } 
                }
                if (numOccurences >= min && numOccurences <= max) {
                    numValid++;
                }
            }
        }
        scanner.close();
        System.out.println(numValid);
    }
    
    public void run2() {
        int numValid = 0;
        Scanner scanner = new Scanner(System.in);
        String pattern = "(\\d+)-(\\d+) ([a-z]): (.*)";
        Pattern r = Pattern.compile(pattern);
        while(scanner.hasNext()) {
            String str = scanner.nextLine();
            Matcher m = r.matcher(str);
            if (m.find()) {
                Integer min = Integer.parseInt(m.group(1));
                Integer max = Integer.parseInt(m.group(2));
                char c = m.group(3).charAt(0);
                String password = m.group(4);
                int numOccurences = 0;
                if (password.charAt(min-1) == c) {
                    numOccurences++;
                }
                if (password.charAt(max-1) == c) {
                    numOccurences++;
                }
                if (numOccurences == 1) {
                    numValid++;
                }
            }
        }
        scanner.close();
        System.out.println(numValid);
    }

}