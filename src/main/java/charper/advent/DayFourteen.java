package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DayFourteen {
    private static String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayFourteenInput.txt";
    
    
    public DayFourteen() {
        System.out.println(run(FILE_PATH));
        System.out.println(run2(FILE_PATH));
    }

    private static Long run2(String filePath) {
        Map<Long, Long> memory = new HashMap<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            Long firstMask = 0L;
            Long firstXMask = 0L;
            List<Integer> xIndices = new ArrayList<>();
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.substring(0, 4).equals("mask")) {
                    String currentMask = line.substring(7);
                    firstMask = getOnesMask(currentMask);
                    firstXMask = getXsAsZeros(currentMask);
                    xIndices.clear();
                    xIndices.addAll(getXIndices(currentMask));
                    continue;
                } 
                Pattern pattern = Pattern.compile("mem\\[([0-9]+)\\] = ([0-9]+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    Long memoryLocation = Long.parseLong(matcher.group(1));
                    Long number = Long.parseLong(matcher.group(2));
                    memoryLocation |= firstMask;
                    memoryLocation &= firstXMask;
                    List<Long> memoryLocations = new ArrayList<>();
                    memoryLocations.add(memoryLocation);
                    for (Integer index : xIndices) {
                        List<Long> newLocations = new ArrayList<>();
                        for (Long mem : memoryLocations) {
                            newLocations.add(mem + (long)Math.pow(2, index)); 
                        }
                        memoryLocations.addAll(newLocations);
                    }

                    for (Long location : memoryLocations) {
                        memory.put(location, number);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }

        Long sum = 0L;
        for (Map.Entry<Long, Long> entry : memory.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    private static List<Integer> getXIndices(String currentMask) {
        List<Integer> xIndices = new ArrayList<>();
        char[] chars = currentMask.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'X') {
                xIndices.add(chars.length - i - 1);
            }
        }
        return xIndices;
    }

    private static Long run(String filePath) {
        Map<Integer, Long> memory = new HashMap<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            Long onesMask = 0L;
            Long zerosMask = 0L;
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.substring(0, 4).equals("mask")) {
                    String currentMask = line.substring(7);
                    onesMask = getOnesMask(currentMask);
                    zerosMask = getZerosMask(currentMask);
                    continue;
                } 
                Pattern pattern = Pattern.compile("mem\\[([0-9]+)\\] = ([0-9]+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    Integer memoryLocation = Integer.parseInt(matcher.group(1));
                    Long number = Long.parseLong(matcher.group(2));
                    number &= zerosMask;
                    number |= onesMask;
                    memory.put(memoryLocation, number);
                }
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }

        Long sum = 0L;
        for (Map.Entry<Integer, Long> entry : memory.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }
    
    private static Long getOnesMask(String mask) {
        String onesStr = mask.replace('X', '0');
        return Long.parseLong(onesStr, 2);
    }

    private static Long getZerosMask(String mask) {
        String zerosStr = mask.replace('X', '1');
        return Long.parseLong(zerosStr, 2);
    }

    private static Long getXsAsZeros(String mask) {
        String xStr = mask.replace('0', '1').replace('X', '0');
        return Long.parseLong(xStr, 2);
    }
}
