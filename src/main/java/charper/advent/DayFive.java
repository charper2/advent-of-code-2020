package charper.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DayFive {

    public void run() {
        List<Integer> seats = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String seat = scanner.nextLine().trim();
            int seatValue = getSeatValue(seat);
            seats.add(seatValue);
        }
        System.out.println(seats.stream().sorted().collect(Collectors.toList()));
        scanner.close();
    }

    public void run2() {
        List<Integer> seats = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String seat = scanner.nextLine().trim();
            int seatValue = getSeatValue(seat);
            seats.add(seatValue);
        }
        List<Integer> seatsOrdered = seats.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < seatsOrdered.size()-1; i++) {
            if (seatsOrdered.get(i) != (seatsOrdered.get(i+1) - 1)) {
                System.out.println(seatsOrdered.get(i) + 1);
                break;
            }
        }
        scanner.close();
    }
    
    private static int getSeatValue(String seat) {
        int answer = 0;
        for (int i = 0; i < 10; i++) {
            if (seat.charAt(9-i) == 'R' || seat.charAt(9-i) == 'B') {
                answer = answer + (int)Math.pow(2, i);
            }
        }
        return answer;
    }
}
