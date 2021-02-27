package charper.advent.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DayTwenty {
    private static final String FILE_PATH = "C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\day20\\DayTwentyInput.txt";

    public DayTwenty() {
        System.out.println(run(FILE_PATH));
    }

    private Long run(String filePath) {
        List<Tile> tiles = getTiles(filePath);
        for (int i = 0; i < tiles.size() - 1; i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tileMatches(tiles.get(i), tiles.get(j))) {
                    tiles.get(i).addEdgeMatch();
                    tiles.get(j).addEdgeMatch();
                }
            }
        }

        Long total = 1L;

        for (Tile tile: tiles) {
            if (tile.getEdgeMatches() == 2) {
                System.out.println(tile.getId());
                total *= tile.getId();
            }
        }

        return total;
    }

    private static List<Tile> getTiles(String filePath) {
        List<Tile> tiles = new ArrayList<>();
        int index = 0;
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                if (instruction.startsWith("Tile ")) {
                    Integer id = Integer.parseInt(instruction.substring(5, instruction.length() - 1));
                    tiles.add(new Tile(id));
                    index = tiles.size() - 1;
                } else if (instruction.length() > 0) {
                    tiles.get(index).addLine(getCharacters(instruction));
                }
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return tiles;
    }

    private static List<Character> getCharacters(String instruction) {
        return instruction.chars()
        .mapToObj(e -> (char)e)
        .collect(Collectors.toList());
    }

    private static boolean tileMatches(Tile tile1, Tile tile2) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (edgeMatches(tile1.getSide(i), tile2.getSide(j)) ||
                    flippedEdgeMatches(tile1.getSide(i), tile2.getSide(j))
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean edgeMatches(List<Character> edge1, List<Character> edge2) {
        for (int i = 0; i < edge1.size(); i++) {
            if (!(edge1.get(i).equals(edge2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    private static boolean flippedEdgeMatches(List<Character> edge1, List<Character> edge2) {
        for (int i = 0; i < edge1.size(); i++) {
            if (!(edge1.get(i).equals(edge2.get(edge2.size()-i-1)))) {
                return false;
            }
        }
        return true;
    }

    private static class Tile {
        private final int id;
        private int edgeMatches = 0;
        private List<List<Character>> tile = new ArrayList<>();

        Tile(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
        
        public int getEdgeMatches() {
            return edgeMatches;
        }

        public void addEdgeMatch() {
            edgeMatches++;
        }

        public void addLine(List<Character> line) {
            tile.add(line);
        }

        public List<Character> getSide(int num) {
            switch(num) {
                case 0:
                    return getTop();
                case 1:
                    return getRight();
                case 2:
                    return getBottom();
                case 3:
                    return getLeft();
                default:
                    return getTop();
            }
        }

        public List<Character> getTop() {
            return tile.get(0);
        }

        public List<Character> getRight() {
            List<Character> right = new ArrayList<>();
            for (List<Character> line : tile) {
                right.add(line.get(line.size() - 1));
            }
            return right;
        }

        public List<Character> getBottom() {
            return tile.get(tile.size() - 1);
        }

        public List<Character> getLeft() {
            List<Character> left = new ArrayList<>();
            for (List<Character> line : tile) {
                left.add(line.get(0));
            }
            return left;
        }
    }
}
