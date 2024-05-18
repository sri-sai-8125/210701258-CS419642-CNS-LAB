import java.util.Scanner;

public class Main
 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter two prime numbers p and q that are not equal: ");
        int p = scanner.nextInt();
        int q = scanner.nextInt();

        int n = p * q;
        int phi = (p - 1) * (q - 1);

        System.out.println("Phi(" + n + ") = " + phi);

        System.out.print("Enter the integer e: ");
        int e = scanner.nextInt();

        if (e < 1 || e >= phi || gcd(phi, e) != 1) {
            System.out.println("Choose proper value for e!");
            return;
        }

        // Key Generation
        int d = multiplicativeInverse(e, phi);
        if (d == -1) {
            System.out.println("Multiplicative inverse not found!");
            return;
        }

        System.out.println("Public Key PU = {" + e + "," + n + "}");
        System.out.println("Private Key PR = {" + d + "," + n + "}");

        // Encryption
        System.out.print("Message M = ");
        int M = scanner.nextInt();

        int C = power(M, e, n);
        System.out.println("Ciphertext C = " + C);

        // Decryption
        M = power(C, d, n);
        System.out.println("Decrypted Message M = " + M);
    }

    private static int power(int x, int y, int p) {
        int res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) == 1) {
                res = (res * x) % p;
            }
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static int multiplicativeInverse(int a, int b) {
        int b0 = b, t, q;
        int x0 = 0, x1 = 1;
        if (b == 1) return 1;
        while (a > 1) {
            q = a / b;
            t = b;
            b = a % b;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) x1 += b0;
        return x1;
    }
}
