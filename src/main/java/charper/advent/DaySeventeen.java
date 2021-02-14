package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DaySeventeen {

    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DaySeventeenInput.txt";

    public DaySeventeen() {
        run();
        // run2();
    }

    public void run() {
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, Region>>>> cube = new HashMap<>();
        Map<Integer, Map<Integer, Map<Integer, Region>>> zSlice = new HashMap<>();
        zSlice.put(0, getSlice(FILE_PATH));
        cube.put(0, zSlice);
        for (int i = 1; i <= 6; i++) {
            cube = cycle(cube, i);
        }
    }

    public Map<Integer, Map<Integer, Map<Integer, Map<Integer, Region>>>> cycle(
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, Region>>>> cube,
         int cycle
    ) {
        int numActive = 0;
        int wSize = cube.entrySet().size();
        int zSize = cube.get(0).entrySet().size();
        int ySize = cube.get(0).get(0).entrySet().size();
        int xSize = cube.get(0).get(0).get(0).entrySet().size();
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, Region>>>> nextState = new HashMap<>();

        for (int h = -1*cycle; h < wSize + cycle; h++) {
            Map<Integer, Map<Integer, Map<Integer, Region>>> wSlice = nextState.get(h);
            if (wSlice == null) {
                wSlice = new HashMap<>();
            }
            for (int i = -1*cycle; i < zSize + cycle; i++) {
                Map<Integer, Map<Integer, Region>> zSlice = wSlice.get(i);
                if (zSlice == null) {
                    zSlice = new HashMap<>();
                }
                for (int j = -1*cycle; j < ySize + cycle; j++) {
                    Map<Integer, Region> ySlice = zSlice.get(j);
                    if (ySlice == null) {
                        ySlice = new HashMap<>();
                    }
                    for (int k = -1*cycle; k < xSize + cycle; k++) {
                        Region cubeVal = getCurrentValue(cube, h, i, j, k);  
                        Region nextVal = new Region('.');                 
                        int active = numberAdjacentActive(cube, h, i, j, k);
                        if ((cubeVal.isActive() && (active == 2 || active == 3)) ||
                            ((!cubeVal.isActive()) && active == 3)) {
                            nextVal.setActive();
                            numActive++;
                        }
                        ySlice.put(k, nextVal);
                    }
                    zSlice.put(j, ySlice);
                }
                wSlice.put(i, zSlice);
            }
            nextState.put(h, wSlice);
        }
        System.out.println(numActive);
        // System.out.println(nextState.get(0));
        return nextState;
    }
    
    private Region getCurrentValue(
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, Region>>>> cube,
        int w,
        int z,
        int y,
        int x
    ) {
        Map<Integer, Map<Integer, Map<Integer, Region>>> wSlice = cube.get(w);
        if (wSlice == null) {
            return new Region('.');
        }
        Map<Integer, Map<Integer, Region>> zSlice = wSlice.get(z);
        if (zSlice == null) {
            return new Region('.');
        }
        Map<Integer, Region> ySlice = zSlice.get(y);
        if (ySlice == null) {
            return new Region('.');
        }
        Region xReg = ySlice.get(x);
        if (xReg == null) {
            return new Region('.');
        }
        return xReg;
    }
 
    // return number of directly active regions.
    private int numberAdjacentActive(
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, Region>>>> cube,
        int w,
        int z,
        int y,
        int x
    ) {
        int active = 0;
        for (int h = w-1; h <= w+1; h++) {
            Map<Integer, Map<Integer, Map<Integer, Region>>> wSlice = cube.get(h);
            if (wSlice == null) {
                continue;
            }
            for (int i = z-1; i <= z+1; i++) {
                Map<Integer, Map<Integer, Region>> zSlice = wSlice.get(i);
                if (zSlice == null) {
                    continue;
                }
                for (int j = y-1; j <= y+1; j++) {
                    Map<Integer, Region> ySlice = zSlice.get(j);
                    if (ySlice == null) {
                        continue;
                    }
                    for (int k = x-1; k <= x+1; k++) {
                        if (h == w && i == z && j == y && k == x) {
                            continue;
                        }
                        Region xSlice = ySlice.get(k);
                        if (xSlice == null) {
                            continue;
                        } 
                        if (xSlice.isActive()) {
                            active++;
                            if (active > 3) {
                                return active;
                            }
                        }
                    }
                }
            }
        }
        return active;
    }
    
    private static Map<Integer, Map<Integer, Region>> getSlice(String filePath) {
        Map<Integer, Map<Integer, Region>> cube = new HashMap<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            int i = 0;
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                Map<Integer, Region> row = convertStringToRegionMap(line);
                cube.put(i, row);
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return cube;
    }

    private static Map<Integer, Region> convertStringToRegionMap(String str) {
        Map<Integer, Region> regions = new HashMap<>();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            regions.put(i, new Region(chars[i]));
        }
        return regions;
    }

    private static class Region {
        private boolean active;

        public Region(char character) {
            if (character == '.') {
                this.active = false;
            } else {
                this.active = true;
            }
        }

        public boolean isActive() {
            return active;
        }

        public void setActive() {
            active = true;
        }

        public String toString() {
            return active == true ? "true" : "false";
        }
    }

}
