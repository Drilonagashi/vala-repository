import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimeFactorsStorage {

    private final Map<Integer, List<Integer>> storage = new HashMap<>();

    public List<Integer> getPrimeFactors(int n) {
        return storage.get(n);
    }

    public void addPrimeFactors(int n, List<Integer> factors) {
        storage.put(n, factors);
    }

    public boolean hasPrimeFactors(int n) {
        return storage.containsKey(n);
    }

    public void printAllPrimeFactors() {
        for (int n : storage.keySet()) {
            List<Integer> factors = storage.get(n);
            System.out.printf("Prime factors of %d are: %s\n", n, factors);
        }
    }
}
