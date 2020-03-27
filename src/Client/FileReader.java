package Client;

import java.io.*;
import util.Input;

public class FileReader {
    public static Input writeFileToBytes(String filename) throws FileNotFoundException {
        Input input = null;
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
        byte[] bytes = bos.toByteArray();
        input.setType(Input.inputType.FILE);
        input.setFile(bytes, file.getName());
        return input;
    }

    public static void readFileFromBytes(Input input, File selectedFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(selectedFile);
        fos.write(input.getByteArray());
        fos.flush();
        fos.close();
    }


}
