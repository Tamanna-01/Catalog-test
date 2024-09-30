


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShamirSecretSharing {

    // Decodes a value based on its base
    public static long decodeYValue(String value, int base) {
        return Long.parseLong(value, base);  // Converts string value to decimal
    }

    // Lagrange interpolation to find f(0), which is the secret
    public static long lagrangeInterpolation(long[] x, long[] y) {
        int n = x.length;
        long secret = 0;

        // Loop over each x_i and calculate Lagrange basis polynomial
        for (int i = 0; i < n; i++) {
            long li = y[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    li = li * (0 - x[j]) / (x[i] - x[j]);  // Lagrange basis evaluated at x=0
                }
            }
            secret += li;  // Add the contribution of this term
        }

        return secret;
    }

    // Function to check if the point is valid based on the polynomial equation
    public static boolean isPointValid(long x, long y, long secret) {
        // For a polynomial of the form f(x) = c, we check if y == c
        return y == secret;
    }

    public static void runTestCase(Map<Integer, Map<String, String>> data, int n, int k) {
        long[] x = new long[k];
        long[] y = new long[k];

        int index = 0;
        for (Map.Entry<Integer, Map<String, String>> entry : data.entrySet()) {
            int xi = entry.getKey();  // x value
            String yiStr = entry.getValue().get("value");  // y value as string
            int base = Integer.parseInt(entry.getValue().get("base"));  // base
            long yi = decodeYValue(yiStr, base);  // Convert y to decimal

            x[index] = xi;
            y[index] = yi;
            index++;
            if (index == k) {
                break;  // We only need 'k' points for interpolation
            }
        }

        // Perform Lagrange Interpolation to find the secret
        long secret = lagrangeInterpolation(x, y);

        // Output the result
        System.out.println("The secret is: " + secret);
        
        // Check for wrong points if running test case 2
        if (n == 9 && k == 6) {
            List<String> wrongPoints = new ArrayList<>();
            for (Map.Entry<Integer, Map<String, String>> entry : data.entrySet()) {
                int xi = entry.getKey();  // x value
                String yiStr = entry.getValue().get("value");  // y value as string
                int base = Integer.parseInt(entry.getValue().get("base"));  // base
                long yi = decodeYValue(yiStr, base);  // Convert y to decimal
                
                // Check if the point is valid
                if (!isPointValid(xi, yi, secret)) {
                    wrongPoints.add(String.format("(%d, %d)", xi, yi));
                }
            }
            // Print all wrong points in a list format
            System.out.println("Wrong points in Test Case 2: " + wrongPoints);
        }
    }

    public static void main(String[] args) {

        // Test Case 1
        System.out.println("Test Case 1:");
        Map<Integer, Map<String, String>> testCase1 = new HashMap<>();
        testCase1.put(1, Map.of("base", "10", "value", "4"));
        testCase1.put(2, Map.of("base", "2", "value", "111"));
        testCase1.put(3, Map.of("base", "10", "value", "12"));
        testCase1.put(6, Map.of("base", "4", "value", "213"));
        
        runTestCase(testCase1, 4, 3);  // n = 4, k = 3 (k = m + 1)

        // Test Case 2
        System.out.println("\nTest Case 2:");
        Map<Integer, Map<String, String>> testCase2 = new HashMap<>();
        testCase2.put(1, Map.of("base", "10", "value", "28735619723837"));
        testCase2.put(2, Map.of("base", "16", "value", "1A228867F0CA"));
        testCase2.put(3, Map.of("base", "12", "value", "32811A4AA0B7B"));
        testCase2.put(4, Map.of("base", "11", "value", "917978721331A"));
        testCase2.put(5, Map.of("base", "16", "value", "1A22886782E1"));
        testCase2.put(6, Map.of("base", "10", "value", "28735619654702"));
        testCase2.put(7, Map.of("base", "14", "value", "71AB5070CC4B"));
        testCase2.put(8, Map.of("base", "9", "value", "122662581541670"));
        testCase2.put(9, Map.of("base", "8", "value", "642121030037605"));

        runTestCase(testCase2, 9, 6);  // n = 9, k = 6 (k = m + 1)
    }
}
