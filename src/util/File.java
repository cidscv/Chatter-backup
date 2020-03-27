package util;

import java.io.Serializable;

public class File implements Serializable {
    private byte[] bytes;
    private String filename;

    public File() {
    }

    public File(byte[] bytes, String filename) {
        this.bytes = bytes;
        this.filename = filename;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
