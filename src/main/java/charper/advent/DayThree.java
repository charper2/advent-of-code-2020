package charper.advent;

import java.util.List;
import static charper.advent.Utils.getLinesFromFile;

public class DayThree {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayThreeInput.txt";

    public DayThree() {
        run2();
    }

    public void run2() {
        List<String> treeLocations = getLinesFromFile(FILE_PATH);
        long numHits = run(1, 1, treeLocations) *
            run(3, 1, treeLocations) *
            run(5, 1, treeLocations) *
            run(7, 1, treeLocations) *
            run(1, 2, treeLocations);
        System.out.println(numHits);
    }

    public long run(int moveX, int moveY, List<String> treeLocations) {
        int xLoc = 0;
        int yLoc = 0;
        long numHits = 0;

        int maxX = treeLocations.get(0).length();
        int maxY = treeLocations.size();
        while (yLoc < maxY-1) {
            xLoc += moveX;
            yLoc += moveY;
            int calcX = xLoc % maxX;
            if (treeLocations.get(yLoc).charAt(calcX) == '#') {
                numHits++;
            }
        }
        System.out.println(numHits);
        return numHits;
    }

}
