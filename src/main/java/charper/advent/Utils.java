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
}