package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DaySixteen {
    private static String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DaySixteenInput.txt";
    
    public DaySixteen() {
        System.out.println(run(FILE_PATH));
    }

    private static Long run(String filePath) {
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            Map<String, BoundaryPair> boundaries = new HashMap<>();
            List<Integer> myTicket = new ArrayList<>();
            List<List<Integer>> nearbyTickets = new ArrayList<>();
            boolean myTicketNext = false;
            boolean nearbyTicketsNext = false;
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    continue;
                }
                if (nearbyTicketsNext) {
                    nearbyTickets.add(getListOfIntegers(line));
                    continue;
                }
                if (myTicketNext) {
                    myTicket.addAll(getListOfIntegers(line));
                    myTicketNext = false;
                    continue;
                }
                if (line.trim().equals("your ticket:")) {
                    myTicketNext = true;
                    continue;
                }
                if (line.trim().equals("nearby tickets:")) {
                    nearbyTicketsNext = true;
                    continue;
                } 
                Pattern pattern = Pattern.compile("(.+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    BoundaryPair bounds = new BoundaryPair(
                        Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)),
                        Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
                    boundaries.put(m.group(1), bounds);
                }
            }

            List<Integer> invalid = new ArrayList<>();
            Set<Integer> invalidTickets = new HashSet<>();
            for (int i = 0; i < nearbyTickets.size(); i++) {
                List<Integer> ticket = nearbyTickets.get(i);
                for (Integer intValue : ticket) {
                    boolean found = false;
                    for (Entry<String, BoundaryPair> entry : boundaries.entrySet()) {
                        if (entry.getValue().isValid(intValue)) {
                            found = true;
                        }
                    }
                    if (found == false) {
                        invalid.add(intValue);
                        invalidTickets.add(i);
                        break;
                    }
                }
            }
            scanner.close();
            Integer sum1 =  invalid.stream().collect(Collectors.summingInt(Integer::intValue));
            System.out.println("Part 1: " + sum1);

            Map<String, List<Integer>> matches = new HashMap<>();
            List<List<Integer>> valid = new ArrayList<>();
            for (int i = 0; i < nearbyTickets.size(); i++) {
                if (!invalidTickets.contains(i)) {
                    valid.add(nearbyTickets.get(i));
                }
            }
            valid.add(myTicket);
            List<Integer> found = new ArrayList<>();
            List<String> foundKey = new ArrayList<>();
            for (List<Integer> ticket : valid) {
                for (Entry<String, BoundaryPair> entry : boundaries.entrySet()) {
                    if (foundKey.contains(entry.getKey())) {
                        continue;
                    }
                    List<Integer> possibilities = new ArrayList<>();
                    for (int i = 0; i < ticket.size(); i++) {
                        if (found.contains(i)) {
                            continue;
                        }
                        if (entry.getValue().isValid(ticket.get(i))) {
                            possibilities.add(i);      
                        }
                    }
                    List<Integer> current = matches.get(entry.getKey());
                    if (current == null) {
                        matches.put(entry.getKey(), new ArrayList<>(possibilities));
                    } else {
                        List<Integer> intersection = possibilities.stream()
                            .distinct().filter(current::contains).collect(Collectors.toList());
                        current.clear();
                        current.addAll(intersection);
                        matches.put(entry.getKey(), current);
                        if (current.size() == 1) {
                            found.add(current.get(0));
                            foundKey.add(entry.getKey());
                        }
                    }
                }
            }
            System.out.println(matches);

            Set<Integer> departureIndices = new HashSet<>();
            for (Entry<String, List<Integer>> match : matches.entrySet()) {
                if (match.getKey().contains("departure")) {
                    departureIndices.addAll(match.getValue());
                }
            }
            Long product = 1L;
            for (Integer index : departureIndices) {
                product *= myTicket.get(index);
            }           

            return product;
            // Part 2 should refactor out
           
        } catch (FileNotFoundException e) {
            System.out.println("oops no file here...");
        }
        return null;
    }

    private static List<Integer> getListOfIntegers(String str) {
        return Stream.of(str.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }
    
    private static class BoundaryPair {
        private final int min;
        private final int max;
        private final int min2;
        private final int max2;

        public BoundaryPair(int min, int max, int min2, int max2) {
            this.min = min;
            this.max = max;
            this.min2 = min2;
            this.max2 = max2;
        }

        public boolean isValid(int number) {
            return (number >= min && number <= max) || (number >= min2 && number <= max2);
        }
    }
}
