import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter prime number p and enter q prime divisor of (p-1): ");
        int p = scanner.nextInt();
        int q = scanner.nextInt();

        System.out.print("Enter h such that it is greater than 1 and less than (p-1): ");
        int h = scanner.nextInt();

        // Compute g
        int t = (p - 1) / q;
        int g = power(h, t, p);

        System.out.print("Enter user's private key such that it is greater than 0 and less than q: ");
        int x = scanner.nextInt();

        // Compute user's public key
        int y = power(g, x, p);

        System.out.print("Enter user's per-message secret key k such that it is greater than 0 and less than q: ");
        int k = scanner.nextInt();

        System.out.print("Enter the hash(M) value: ");
        int hash = scanner.nextInt();

        // Signing. Compute r and s pair
        int z = power(g, k, p);
        int r = z % q;
        int inv = multiplicativeInverse(k, q, p);
        int s = (inv * (hash + x * r)) % q;

        // Display
        System.out.println("\n*********Computed Values*********");
        System.out.println("g = " + g);
        System.out.println("y = " + y);
        System.out.println("Generated Signature Sender = (" + r + ", " + s + ")");
    }

    private static int power(int x, int y, int p) {
        int res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }

    private static int multiplicativeInverse(int a, int b, int n) {
        int sum, x, y;
        for (y = 0; y < n; y++) {
            for (x = 0; x < n; x++) {
                sum = a * x + b * (-y);
                if (sum == 1)
                    return x;
            }
        }
        return -1; // Indicates multiplicative inverse not found
    }
}
