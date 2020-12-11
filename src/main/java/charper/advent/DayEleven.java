package charper.advent;

import static charper.advent.Utils.getCharMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;;

public class DayEleven {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayElevenInput.txt";

    public DayEleven() {
        run();
    }

    public void run() {
        Map<Integer, char[]> chairMap = getCharMap(FILE_PATH);
        while(true) {
            Map<Integer, char[]> newChairMap = occupySeatsRound(chairMap);
            if (areEqualWithArrayValue(chairMap, newChairMap)) {
                break;
            }
            chairMap = newChairMap;
        }
        System.out.println(numOccupiedSeats(chairMap));
    }

    public Map<Integer, char[]> occupySeatsRound(final Map<Integer, char[]> chairMap) {
        Map<Integer, char[]> newChairMap = deepClone(chairMap);
        int maxX = chairMap.get(0).length;
        System.out.println(chairMap.get(0));
        int maxY = chairMap.keySet().size();
        for (int i = 0; i < maxX ; i++) {
            for (int j = 0; j < maxY; j++) {
                char c = chairMap.get(j)[i];
                if (isEmpty(c)) {
                    if (noOccupiedSeatsAdjacent(chairMap, i, j, maxX, maxY)) {
                        char[] chars = newChairMap.get(j);
                        chars[i] = '#';
                        newChairMap.put(j, chars);
                    }
                }
                else if (isOccupied(c)) {
                    if (fourOrMoreAdjacentOccupied(chairMap, i , j, maxX, maxY)) {
                        char[] chars = newChairMap.get(j);
                        chars[i] = 'L';
                        newChairMap.put(j, chars);
                    }
                }
            }
        }
        
        return newChairMap;
    }

    // if seat is empty and there are no adjacent seats occupied; return true
    // if seat is empty and at least 1 adjacent seat is occupied; return false;
    private boolean noOccupiedSeatsAdjacent(Map<Integer, char[]> chairMap, int x, int y, int maxX, int maxY) {
        // int seatsNotExisting = 0;
        char c = chairMap.get(y)[x];
        if (!isEmpty(c)) {
            return false;
        }
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (i < 0 || j < 0 || i >= maxX || j >= maxY) {
                    // seatsNotExisting++;
                    continue;
                }
                if (i == x && j == y) {
                    continue;
                }
                if (isOccupied(chairMap.get(j)[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    // return true if four or more seats adjacent are occupied.
    private boolean fourOrMoreAdjacentOccupied(Map<Integer, char[]> chairMap, int x, int y, int maxX, int maxY) {
        char c = chairMap.get(y)[x];
        if (!isOccupied(c)) {
            return false;
        }
        int adjacentOccupied = 0;
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (i < 0 || j < 0 || i >= maxX || j >= maxY) {
                    continue;
                }
                if (i == x && j == y) {
                    continue;
                }
                if (isOccupied(chairMap.get(j)[i])) {
                    adjacentOccupied++;
                }
            }
        }
        return adjacentOccupied >= 4;
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
