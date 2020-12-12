package charper.advent;

import static charper.advent.Utils.getCharMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;;

public class DayEleven {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayElevenInput.txt";

    public DayEleven() {
        run();
        run2();
    }

    public void run() {
        Map<Integer, char[]> chairMap = getCharMap(FILE_PATH);
        while(true) {
            Map<Integer, char[]> newChairMap = occupySeatsPart1(chairMap);
            if (areEqualWithArrayValue(chairMap, newChairMap)) {
                break;
            }
            chairMap = newChairMap;
        }
        System.out.println("Part 1: " + numOccupiedSeats(chairMap));
    }

    public void run2() {
        Map<Integer, char[]> chairMap = getCharMap(FILE_PATH);
        while(true) {
            Map<Integer, char[]> newChairMap = occupySeatsPart2(chairMap);
            if (areEqualWithArrayValue(chairMap, newChairMap)) {
                break;
            }
            chairMap = newChairMap;
        }
        System.out.println("Part 2: " + numOccupiedSeats(chairMap));
    }

    private Map<Integer, char[]> occupySeatsPart1(final Map<Integer, char[]> chairMap) {
        Map<Integer, char[]> newChairMap = deepClone(chairMap);
        int maxX = chairMap.get(0).length;
        int maxY = chairMap.keySet().size();
        for (int i = 0; i < maxX ; i++) {
            for (int j = 0; j < maxY; j++) {
                char c = chairMap.get(j)[i];
                if (isEmpty(c)) {
                    // instead of using number Adjacent occupied here we could use a similar 
                    // function and break early, i.e. on an occupied. But I didn't need the speed.
                    if (numberAdjacentOccupied(chairMap, i, j, maxX, maxY) == 0) {
                        char[] chars = newChairMap.get(j);
                        chars[i] = '#';
                        newChairMap.put(j, chars);
                    }
                }
                else if (isOccupied(c)) {
                    if (numberAdjacentOccupied(chairMap, i , j, maxX, maxY) >= 4) {
                        char[] chars = newChairMap.get(j);
                        chars[i] = 'L';
                        newChairMap.put(j, chars);
                    }
                }
            }
        }
        
        return newChairMap;
    }

    // @TODO yuck probs shouldn't just copy this. Should pull out common code.
    private Map<Integer, char[]> occupySeatsPart2(final Map<Integer, char[]> chairMap) {
        Map<Integer, char[]> newChairMap = deepClone(chairMap);
        int maxX = chairMap.get(0).length;
        int maxY = chairMap.keySet().size();
        for (int i = 0; i < maxX ; i++) {
            for (int j = 0; j < maxY; j++) {
                char c = chairMap.get(j)[i];
                if (isEmpty(c)) {
                    if (numberAdjacentOccupiedPart2(chairMap, i, j, maxX, maxY) == 0) {
                        char[] chars = newChairMap.get(j);
                        chars[i] = '#';
                        newChairMap.put(j, chars);
                    }
                }
                else if (isOccupied(c)) {
                    if (numberAdjacentOccupiedPart2(chairMap, i , j, maxX, maxY) >= 5) {
                        char[] chars = newChairMap.get(j);
                        chars[i] = 'L';
                        newChairMap.put(j, chars);
                    }
                }
            }
        }
        
        return newChairMap;
    }

    // return number of directly adjacent seats that are occupied.
    private int numberAdjacentOccupied(Map<Integer, char[]> chairMap, int x, int y, int maxX, int maxY) {
        int adjacentOccupied = 0;
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (i < 0 || j < 0 || i >= maxX || j >= maxY || (i == x && j == y)) {
                    continue;
                }
                if (isOccupied(chairMap.get(j)[i])) {
                    adjacentOccupied++;
                }
            }
        }
        return adjacentOccupied;
    }

    // return the number of adjacent seats that are occupied. This includes any seat in a particular direction.
    private int numberAdjacentOccupiedPart2(Map<Integer, char[]> chairMap, int x, int y, int maxX, int maxY) {
        int adjacentOccupied = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) { continue; }
                if (isDirectionOccupied(chairMap, x, y, maxX, maxY, i, j)) {
                    adjacentOccupied++;
                }
            }
        }
        return adjacentOccupied;
    }

    // part 2 check all seats in a particular direction.
    // return true if found occcupied
    // return false if empty
    // continue in direction if neither.
    private boolean isDirectionOccupied(
        final Map<Integer, char[]> chairMap, 
        final int startX, 
        final int startY, 
        final int maxX, 
        final int maxY, 
        final int changeX, 
        final int changeY
    ) {
        int i = startX + changeX;
        int j = startY + changeY;
        while (i >= 0 && i < maxX && j>= 0 && j < maxY) {
            char seat = chairMap.get(j)[i];
            if (isOccupied(seat)) {
                return true;
            }
            if (isEmpty(seat)) {
                return false;
            }
            i = i + changeX;
            j = j + changeY;
        }
        return false;
    }

    private boolean isOccupied(char seat) {
        if (seat == '#') {
            return true;
        }
        return false;
    }

    private boolean isEmpty(char seat) {
        if (seat == 'L') {
            return true;
        }
        return false;
    }

    private boolean areEqualWithArrayValue(Map<Integer, char[]> first, Map<Integer, char[]> second) {
        if (first.size() != second.size()) {
            return false;
        }
    
        return first.entrySet().stream()
          .allMatch(e -> Arrays.equals(e.getValue(), second.get(e.getKey())));
    }

    private Map<Integer, char[]> deepClone(Map<Integer, char[]> originalMap) {
        Map<Integer, char[]> deepClone = new HashMap<>();
        for (Map.Entry<Integer, char[]> entry : originalMap.entrySet()) {
            char[] origChars = entry.getValue();
            char[] chars = new char[origChars.length];
            for (int i=0; i < origChars.length; i++) {
                chars[i] = origChars[i];
            }
            deepClone.put(entry.getKey(), chars);
        }
        return deepClone;
    }

    private int numOccupiedSeats(Map<Integer, char[]> seatMap) {
        int numOccupied = 0;
        for (Map.Entry<Integer, char[]> entry : seatMap.entrySet()) {
            for (char c : entry.getValue()) {
                if (isOccupied(c)) {
                    numOccupied++;
                }
            }
        }
        return numOccupied;
    }
}
