import java.util.Scanner;
import java.util.HashSet;

public class Main {

    private static boolean check(char[][] table, char k) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (table[i][j] == k) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("********** Playfair Cipher ************\n\n");

        // Input the key length and key
        System.out.print("Enter the length of the Key: ");
        int key_len = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the Key: ");
        String keyInput = scanner.nextLine();
        char[] key = keyInput.replaceAll("j", "i").toCharArray();

        // Initialize the table
        char[][] table = new char[5][5];
        HashSet<Character> usedChars = new HashSet<>();

        int count = 0;
        outerloop:
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                while (count < key_len && usedChars.contains(key[count])) {
                    count++;
                }
                if (count < key_len && check(table, key[count])) {
                    table[i][j] = key[count];
                    usedChars.add(key[count]);
                    count++;
                } else {
                    break outerloop;
                }
            }
        }

        // Insert remaining characters
        char currentChar = 'a';
        outerloop:
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (table[i][j] == '\0') {
                    while (usedChars.contains(currentChar) || currentChar == 'j') {
                        currentChar++;
                    }
                    table[i][j] = currentChar;
                    usedChars.add(currentChar);
                    currentChar++;
                }
            }
        }

        // Print the table
        System.out.println("The table is as follows:");
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }

        // Input plaintext
        System.out.print("\nEnter the length of plain text (without spaces): ");
        int l = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the Plain text: ");
        String plainText = scanner.nextLine().replaceAll("j", "i");
        char[] p = plainText.toCharArray();

        // Replace 'j' with 'i' and print replaced text
        System.out.println("\nThe replaced text (j with i):");
        for (char c : p) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Insert bogus characters
        StringBuilder p1 = new StringBuilder();
        for (int i = 0; i < p.length; i++) {
            p1.append(p[i]);
            if (i + 1 < p.length && p[i] == p[i + 1]) {
                p1.append(p[i] == 'x' ? 'z' : 'x');
            }
        }
        if (p1.length() % 2 != 0) {
            p1.append(p1.charAt(p1.length() - 1) == 'x' ? 'z' : 'x');
        }

        // Print final text
        System.out.println("The final text is:");
        for (int i = 0; i < p1.length(); i++) {
            System.out.print(p1.charAt(i) + " ");
        }
        System.out.println();

        // Create cipher text
        char[] cipher_text = new char[p1.length()];
        for (int k = 0; k < p1.length(); k += 2) {
            char first = p1.charAt(k);
            char second = p1.charAt(k + 1);
            int[] firstPos = findPosition(table, first);
            int[] secondPos = findPosition(table, second);

            if (firstPos[0] == secondPos[0]) {
                cipher_text[k] = table[firstPos[0]][(firstPos[1] + 1) % 5];
                cipher_text[k + 1] = table[secondPos[0]][(secondPos[1] + 1) % 5];
            } else if (firstPos[1] == secondPos[1]) {
                cipher_text[k] = table[(firstPos[0] + 1) % 5][firstPos[1]];
                cipher_text[k + 1] = table[(secondPos[0] + 1) % 5][secondPos[1]];
            } else {
                cipher_text[k] = table[firstPos[0]][secondPos[1]];
                cipher_text[k + 1] = table[secondPos[0]][firstPos[1]];
            }
        }

        // Print the cipher text
        System.out.println("\n\nThe Cipher text is:");
        for (char c : cipher_text) {
            System.out.print(c + " ");
        }
    }

    private static int[] findPosition(char[][] table, char c) {
        int[] position = new int[2];
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (table[i][j] == c) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }
}
