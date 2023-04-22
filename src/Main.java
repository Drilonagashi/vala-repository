import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output.txt";
    private static Set<Integer> primeFactorsSet = new HashSet<>();

    // this enables user to input a number
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Give me the number: ");
            int n = sc.nextInt();

            // check if prime factors for this number are already in set
            if (primeFactorsSet.contains(n)) {
                System.out.printf("Prime factors of %d are already in the set\n", n);
                saveFactorsToFile(n, primeFactors(n));
            } else {
                // calculate prime factors
                long start = System.nanoTime();
                List<Integer> factors = primeFactors(n);
                long end = System.nanoTime();

                // add prime factors to set
                primeFactorsSet.addAll(factors);

                saveFactorsToFile(n, factors);
                System.out.printf("It took %.2f seconds to find those\n", calculateTimeTaken(start, end));
            }
        }
    }

    /**
     * Finds all prime factors of n without duplicates.
     */
    private static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        int d = 2;
        while (n > 1) {
            while (n % d == 0) {
                if (!factors.contains(d)) { // avoid duplicates
                    factors.add(d);
                }
                n /= d;
            }
            d++;
            if (d * d > n && n > 1) {
                factors.add(n); // n is prime
                break;
            }
        }
        return factors;
    }

    /**
     * Saves the prime factors to file and prints the time taken to find them.
     */
    private static void saveFactorsToFile(int n, List<Integer> factors) {
        System.out.printf("Prime factors of %d are: %s\n", n, factors);
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            pw.printf("Prime Factors of number %d are:\n", n);
            for (int factor : factors) {
                pw.printf("%d,", factor);
            }
            pw.printf("\nIt took %.2f seconds to find those\n", calculateTimeTaken(0, 0));
        } catch (IOException e) {
            System.err.println("Error writing to output file");
        }
    }

    /**
     * Calculates the time taken to perform a task given the start and end times.
     */
    private static double calculateTimeTaken(long start, long end) {
        return (end - start) / 1e9;
    }
}
