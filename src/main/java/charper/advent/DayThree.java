package charper.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayThree {

    public DayThree() {
        run2();
    }

    public void run2() {
        List<String> treeLocations = getLocations();
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
        // List<String> treeLocations = getLocations();
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

    public List<String> getLocations() {
        List<String> treeLocations = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String str = scanner.nextLine();
            treeLocations.add(str);
        }
        scanner.close();
        return treeLocations;
    }

}
