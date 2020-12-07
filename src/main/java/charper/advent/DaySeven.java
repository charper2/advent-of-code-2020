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

    // This maps all bags to those that can contain it.
    Map<String, Set<String>> outerBagMap = new HashMap<>();

    // This maps all bags to a all bags that are contained inside it. Bags contained
    // inside are included as a BagPair, containing the number of bags and the type.
    Map<String, Set<BagPair>> innerBagMap = new HashMap<>();

    public DaySeven() {
        run2();
    }

    public void run() {
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
                        if (outerBagMap.get(innerBag) != null) {
                            Set<String> types = outerBagMap.get(innerBag);
                            types.add(outerBag);
                            outerBagMap.put(innerBag, types);
                        } else {
                            Set<String> types = new HashSet<>();
                            types.add(outerBag);
                            outerBagMap.put(innerBag, types);
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
                canContainShinyGold.addAll(getOuterBags(bag));
            }
            numValid += canContainShinyGold.size();

            System.out.println(numValid);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
    }

    public void run2() {
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
                innerBagMap.put(outerBag, innerBags);
            }

            // recursively get all bags in bags
            for (BagPair bag : innerBagMap.get(SHINY_GOLD)) {
                numValid += bag.getNumBags() + (bag.getNumBags() * getNumberBags(bag.getBagType()));
            }

            System.out.println(numValid);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
    }
    
    private Set<String> getOuterBags(final String bag) {
        Set<String> outerBags = new HashSet<>();
        if (outerBagMap.get(bag) != null) {
            outerBags.addAll(outerBagMap.get(bag));
            for (String outerBag : outerBagMap.get(bag)) {
                outerBags.addAll(getOuterBags(outerBag));
            }
        }
        return outerBags;
    }

    private int getNumberBags(final String bag) {
        int numBags = 0;
        if (innerBagMap.get(bag) != null) {
            for (BagPair innerBag : innerBagMap.get(bag)) {
                numBags += innerBag.getNumBags() + (innerBag.getNumBags() * getNumberBags(innerBag.getBagType()));
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
