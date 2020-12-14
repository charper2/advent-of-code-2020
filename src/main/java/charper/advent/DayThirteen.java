package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Map<Long, Long> times = getTimes();
        
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
                    timetable.addBus(Long.parseLong(timesArray[i]));
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
    }
}
