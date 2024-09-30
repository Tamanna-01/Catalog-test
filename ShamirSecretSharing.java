import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShamirSecretSharing {
    
    // Decodes a value based on its base
    public static int decodeYValue(String value, int base) {
        return Integer.parseInt(value, base);  // Converts string value to decimal
    }

    // Lagrange interpolation to find f(0), which is the secret
    public static int lagrangeInterpolation(int[] x, int[] y) {
        int n = x.length;
        int secret = 0;

        // Loop over each x_i and calculate Lagrange basis polynomial
        for (int i = 0; i < n; i++) {
            int li = y[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    li = li * (0 - x[j]) / (x[i] - x[j]);  // Lagrange basis evaluated at x=0
                }
            }
            secret += li;  // Add the contribution of this term
        }

        return secret;
    }

    public static void main(String[] args) {
        // Example JSON input handling (You can replace this with actual JSON parsing logic)
        Scanner sc = new Scanner(System.in);
        Map<Integer, Map<String, String>> data = new HashMap<>();

        // Sample input (change to actual JSON parsing)
        // Format: x -> {base: ..., value: ...}
        data.put(1, Map.of("base", "10", "value", "4"));
        data.put(2, Map.of("base", "2", "value", "111"));
        data.put(3, Map.of("base", "10", "value", "12"));
        
        int n = 3;  // Number of roots
        int[] x = new int[n];
        int[] y = new int[n];
        
        int index = 0;
        for (Map.Entry<Integer, Map<String, String>> entry : data.entrySet()) {
            int xi = entry.getKey();  // x value
            String yiStr = entry.getValue().get("value");  // y value as string
            int base = Integer.parseInt(entry.getValue().get("base"));  // base
            int yi = decodeYValue(yiStr, base);  // Convert y to decimal

            x[index] = xi;
            y[index] = yi;
            index++;
        }

        // Perform Lagrange Interpolation to find the secret
        int secret = lagrangeInterpolation(x, y);

        // Output the result
        System.out.println("The secret is: " + secret);
    }
}