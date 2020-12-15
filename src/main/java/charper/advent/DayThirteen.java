package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayThirteen {

    private static String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayThirteenInput.txt";
    
    public DayThirteen() {
        System.out.println(run(FILE_PATH));
        System.out.println(run2(FILE_PATH));
    }

    private Long run(String filePath) {
        Timetable timetable = getTimetable(filePath);
        Long timestamp = timetable.getTimestamp();
        Long smallestDiff = timetable.getBuses().get(0) - (timestamp % timetable.getBuses().get(0));
        Long closestBus = timetable.getBuses().get(0);
        for (Long bus : timetable.getBuses()) {
            Long waitTime = bus - (timestamp % bus);
            if (waitTime < smallestDiff) {
                closestBus = bus;
                smallestDiff = waitTime;
            }
        }
        return smallestDiff * closestBus;
    }

    private Long run2(String filePath) {
        Timetable timetable = getTimetable(filePath);
        List<Long> times = timetable.getBuses();
        List<Integer> remainders = timetable.getRemainders();
        // remainders.remove(remainders.size() - 1);
        // remainders.add(0);
        System.out.println(times);
        System.out.println(remainders);
        
        // Chinese remainder theorem
        Long product = 1L;
        for (Long time : times) {
             product = product * time;
        }

        List<Long> partialProducts = new ArrayList<>();
        for (Long time : times) {
            partialProducts.add(product / time);
        }
        
        Long sum = 0L;
        for (int i = 0; i < times.size(); i++) {
            sum += partialProducts.get(i) * computeInverse(partialProducts.get(i), times.get(i)) * remainders.get(i);
        }
        return sum % product;
    }
    
    private static long computeInverse(long a, long b) { 
        long m = b, t, q; 
        long x = 0, y = 1; 
      
        if (b == 1) 
            return 0; 
      
        // Apply extended Euclid Algorithm 
        while (a > 1) 
        { 
            // q is quotient 
            q = a / b; 
            t = b; 
      
            // now proceed same as Euclid's algorithm 
            b = a % b;a = t; 
            t = x; 
            x = y - q * x; 
            y = t; 
        } 
      
        // Make x1 positive 
        if (y < 0) 
         y += m; 
      
        return y; 
    }

    private Timetable getTimetable(String filePath) {
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            Long timestamp = Long.parseLong(scanner.nextLine());
            String times = scanner.nextLine();
            String[] timesArray = times.split(",");
            Timetable timetable = new Timetable(timestamp);
            for (int i = 0; i < timesArray.length; i++) {
                if (!timesArray[i].equals("x")) {
                    Long bus = Long.parseLong(timesArray[i]);
                    Integer busInt = Integer.parseInt(timesArray[i]);
                    timetable.addBus(bus);
                    timetable.addRemainder(busInt-i);
                }
            }
            scanner.close();
            return timetable;
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return null;
    }

    private static class Timetable {
        private final Long timestamp;
        private List<Long> buses = new ArrayList<>();
        private List<Integer> remainders = new ArrayList<>();

        public Timetable(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Long getTimestamp() {
            return this.timestamp;
        }

        public List<Long> getBuses() {
            return this.buses;
        }

        public void addBus(Long bus) {
            buses.add(bus);
        }
        
        public List<Integer> getRemainders() {
            return this.remainders;
        }

        public void addRemainder(Integer remainder) {
            remainders.add(remainder);    
        }
    }
}
