public class DayOne {
    
    public DayOne() {
        List<Integer> integers = getIntegers();
        for (int i = 0; i < integers.size() - 2; i++) {
            for (int j = 0; j < integers.size() - 1; j++) {
                for (int k = 0; k < integers.size(); k++) {
                    if (integers.get(i) + integers.get(j) + integers.get(k) == 2020) {
                        System.out.println(integers.get(i) * integers.get(j) * integers.get(k));
                    }
                }
            }
        }
    }

}