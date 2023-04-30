import java.io.*;
import java.util.*;

public class Main {

    private static final String PRIME_FACTORS_FILE = "prime_factors.txt";
    private static final String OUTPUT_FILE = "output.txt";
    private static Set<Integer> primeFactorsSet = new HashSet<>();

    // This enables user to input a number
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Give me the number: ");
            int n = sc.nextInt();

            // Check if prime factors for this number are already in set
            List<Integer> factors;
            if (primeFactorsSet.contains(n)) {
                System.out.printf("Prime factors of %d are already in the set\n", n);
                factors = readFactorsFromFile(n);
            } else {
                // Calculate prime factors
                long start = System.nanoTime();
                factors = primeFactors(n);
                long end = System.nanoTime();

                // Add prime factors to set
                primeFactorsSet.addAll(factors);

                saveFactorsToFile(n, factors);
                System.out.printf("It took %.2f seconds to find those\n", calculateTimeTaken(start, end));
            }

            // Print prime factors
            System.out.printf("Prime factors of %d are: %s\n", n, factors);
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
                if (!factors.contains(d)) { // Avoid duplicates
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
     * Reads the prime factors of n from the file.
     */
    private static List<Integer> readFactorsFromFile(int n) {
        List<Integer> factors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRIME_FACTORS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(Integer.toString(n))) {
                    String[] factorsStr = parts[1].split(",");
                    for (String factor : factorsStr) {
                        factors.add(Integer.parseInt(factor));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading prime factors from file");
        }
        return factors;
    }

    /**
     * Saves the prime factors to file and prints the time taken to find them.
     */
    private static void saveFactorsToFile(int n, List<Integer> factors) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRIME_FACTORS_FILE, true))) {
            pw.printf("%d:", n);
            for (int factor : factors) {
                pw.printf("%d,", factor);
            }
            pw.println();
        } catch (IOException e) {
            System.err.println("Error writing prime factors to file");
        }
    }

    /**
     * Calculates the time taken to perform a task given the start and end times.
     */
    private static double calculateTimeTaken(long start, long end) {
        return (end - start) / 1e9;
    }

}
