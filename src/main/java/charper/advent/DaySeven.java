package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaySeven {
    private static final String SHINY_GOLD = "shiny gold";

    public DaySeven() {
        run2();
    }

    public void run() {
        Map<String, Set<String>> bagMap = new HashMap<>();
        int numValid = 0;
        try {
            File input = new File("C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DaySevenInput.txt");
            Scanner scanner = new Scanner(input);
            Pattern pattern = Pattern.compile("([a-z ]+)(?:(?=bag|bags))");

            // build the bag type map & types that directly contain "shiny gold"
            Set<String> directlyContainingShinyGold = new HashSet<>();
            while(scanner.hasNext()) {
                String rule = scanner.nextLine();
                String str[] = rule.split(" bags contain ");
                String outerBag = str[0].trim();
                String bagValues[] = str[1].split(", ");

                for (String bag : bagValues) {
                    Matcher matcher = pattern.matcher(bag);
                    if (matcher.find()) {
                        String innerBag = matcher.group(1).trim();
                        if (bagMap.get(innerBag) != null) {
                            Set<String> types = bagMap.get(innerBag);
                            types.add(outerBag);
                            bagMap.put(innerBag, types);
                        } else {
                            Set<String> types = new HashSet<>();
                            types.add(outerBag);
                            bagMap.put(innerBag, types);
                        }
                        if (innerBag.equals(SHINY_GOLD)) {
                            directlyContainingShinyGold.add(outerBag);
                        }
                    }
                }
            }

            // recursively check to see what bags contain the bags that directly contain shiny gold
            Set<String> canContainShinyGold = new HashSet<>(directlyContainingShinyGold);
            for (String bag : directlyContainingShinyGold) {
                canContainShinyGold.addAll(getOuterBags(bagMap, bag));
            }
            numValid += canContainShinyGold.size();

            System.out.println(numValid);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
    }

    private Set<String> getOuterBags(Map<String, Set<String>> bagMap, final String bag) {
        Set<String> outerBags = new HashSet<>();
        if (bagMap.get(bag) != null) {
            outerBags.addAll(bagMap.get(bag));
            for (String outerBag : bagMap.get(bag)) {
                outerBags.addAll(getOuterBags(bagMap, outerBag));
            }
        }
        return outerBags;
    }

    public void run2() {
        Map<String, Set<BagPair>> bagMap = new HashMap<>();
        int numValid = 0;
        try {
            File input = new File("C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DaySevenInput.txt");
            Scanner scanner = new Scanner(input);
            Pattern pattern = Pattern.compile("([0-9])([a-z ]+)(?:(?=bag|bags))");

            // build the bag type map & types
            while(scanner.hasNext()) {
                String rule = scanner.nextLine();
                String str[] = rule.split(" bags contain ");
                String outerBag = str[0].trim();
                String bagValues[] = str[1].split(", ");

                Set<BagPair> innerBags = new HashSet<>();
                for (String bag : bagValues) {
                    Matcher matcher = pattern.matcher(bag);
                    if (matcher.find()) {
                        String innerBag = matcher.group(2).trim();
                        Integer numBags = Integer.parseInt(matcher.group(1));
                        innerBags.add(new BagPair(innerBag, numBags));
                    }
                }
                bagMap.put(outerBag, innerBags);
            }

            // recursively get all bags in bags
            for (BagPair bag : bagMap.get(SHINY_GOLD)) {
                numValid += bag.getNumBags() + (bag.getNumBags() * getNumberBags(bagMap, bag.getBagType()));
            }

            System.out.println(numValid);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
    }

    private int getNumberBags(Map<String, Set<BagPair>> bagMap, final String bag) {
        int numBags = 0;
        if (bagMap.get(bag) != null) {
            for (BagPair innerBag : bagMap.get(bag)) {
                numBags += innerBag.getNumBags() + (innerBag.getNumBags() * getNumberBags(bagMap, innerBag.getBagType()));
            }
        }
        return numBags;
    }

    private static class BagPair {
        private final String bagType;
        private final int numBags;

        public BagPair(String bagType, int numBags) {
            this.bagType = bagType;
            this.numBags = numBags;
        }

        public String getBagType() {
            return this.bagType;
        }

        public int getNumBags() {
            return this.numBags;
        }
    }
}
