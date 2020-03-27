package Client;

import java.io.*;

public class FileReader {
    public static byte[] readBytesFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[16384];
        int n;

        try {
            while ((n = fis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, n);
                System.out.println("sent " + n + " bytes to output");
            }
        } catch (IOException ex) {
        }
        return bos.toByteArray();
    }

    public static void writeFileFromBytes(byte[] bytes, File selectedFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(selectedFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }


}
