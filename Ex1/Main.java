import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] plaintext = new char[100];
        char[] ciphertext = new char[100];
        
        System.out.print("Plaintext: ");
        String input = scanner.nextLine();
        
        int i = 0;
        int x = 0;
        for (char c : input.toCharArray()) {
            plaintext[i++] = c;
            ciphertext[x++] = (char)(c + 3);
        }

        System.out.print("Cipher text: ");
        System.out.println(new String(ciphertext, 0, x));
    }
}
