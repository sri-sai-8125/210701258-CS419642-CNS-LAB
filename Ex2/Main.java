import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i, j, k = 0, l = 0, m = 0;
        char[] s = new char[20];
        char[] a = new char[10];
        char[] b = new char[10];

        System.out.print("Enter a string: ");
        String input = scanner.next();
        s = input.toCharArray();
        
        for (i = 0; i < input.length(); i++) {
            if (i % 2 == 0) { // even position
                a[k] = s[i];
                k++;
            } else { // odd position
                b[l] = s[i];
                l++;
            }
        }

        System.out.println("Characters at even positions:");
        for (i = 0; i < k; i++) {
            System.out.print(a[i] + " ");
            s[m] = a[i];
            m++;
        }
        System.out.println();

        System.out.println("Characters at odd positions:");
        for (i = 0; i < l; i++) {
            System.out.print(" " + b[i]);
            s[m] = b[i];
            m++;
        }
        System.out.println();
        
        System.out.println("\nCipher text is " + new String(s, 0, m));
    }
}
