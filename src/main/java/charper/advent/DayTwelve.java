package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.toRadians;
import static java.lang.Math.abs;

public class DayTwelve {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayTwelveInput.txt";

    public DayTwelve() {
        System.out.println(run());
        System.out.println(run2());
    }

    public int run() {
        List<Movement> movements = getMovements(FILE_PATH);
        int currentX = 0;
        int currentY = 0;
        int currentBearing = 90;
        
        for (Movement movement : movements) {
            char direction = movement.getDirection();
            int measurement = movement.getMeasurement();
            switch (direction) {
                case 'E':
                    currentX += measurement;
                    break;
                case 'W':
                    currentX += measurement * -1;
                    break;
                case 'N':
                    currentY += measurement;
                    break;
                case 'S':
                    currentY += measurement * -1;
                    break;
                case 'F':
                    Location loc = getLocationChange(currentBearing, measurement);
                    currentX += loc.getX();
                    currentY += loc.getY();
                    break;
                case 'R':
                    currentBearing = normaliseAngle(currentBearing + measurement);
                    break;
                case 'L':
                    currentBearing = normaliseAngle(currentBearing - measurement);
                    break;
            }
        }
        return abs(currentX) + abs(currentY); 
    }

    public int run2() {
        List<Movement> movements = getMovements(FILE_PATH);
        int currentX = 0;
        int currentY = 0;
        int wayX = 10;
        int wayY = 1;
        
        for (Movement movement : movements) {
            char direction = movement.getDirection();
            int measurement = movement.getMeasurement();
            switch (direction) {
                case 'E':
                    wayX += measurement;
                    break;
                case 'W':
                    wayX += measurement * -1;
                    break;
                case 'N':
                    wayY += measurement;
                    break;
                case 'S':
                    wayY += measurement * -1;
                    break;
                case 'F':
                    currentX += measurement * wayX;
                    currentY += measurement * wayY;
                    break;
                case 'R':
                    Location rightRotated = rotateWaypoint(wayX, wayY, measurement);
                    wayX = rightRotated.getX();
                    wayY = rightRotated.getY();
                    break;
                case 'L':
                    int normalisedAngle = normaliseAngle(measurement * -1);
                    Location leftRotated = rotateWaypoint(wayX, wayY, normalisedAngle);
                    wayX = leftRotated.getX();
                    wayY = leftRotated.getY();
                    break;
            }
        }
        return abs(currentX) + abs(currentY); 
    }

    private List<Movement> getMovements(String filePath) {
        List<Movement> movements = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                Movement movement = new Movement(instruction.charAt(0), Integer.valueOf(instruction.substring(1)));
                movements.add(movement);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return movements;
    }

    private static class Movement {
        private final char direction;
        private final int measurement;

        public Movement(char direction, int measurement) {
            this.direction = direction;
            this.measurement = measurement;
        }

        public char getDirection() {
            return this.direction;
        }

        public int getMeasurement() {
            return this.measurement;
        }
    }

    public Location getLocationChange(int degrees, int distance) {
        double radians = toRadians(degrees);
        int x = (int) (distance * sin(radians));
        int y = (int) (distance * cos(radians));

        return new Location(x, y);
    }

    private static class Location {
        private int x;
        private int y;

        public Location(int x,  int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void rotate90() {
            int tempX = this.x;
            this.x = this.y;
            this.y = tempX * -1;
        }
    }

    // keep angle between 0 and 360
    private static int normaliseAngle(int angle) {
        return (angle + 360) % 360;
    }

    // rotate in increments of 90 around the 0, 0 clockwise
    private static Location rotateWaypoint(int currentX, int currentY, int degreesRotation) {
        Location loc = new Location(currentX, currentY);
        while(degreesRotation > 0) {
            loc.rotate90();
            degreesRotation -= 90;
        }
        return loc;
    }
    
}
