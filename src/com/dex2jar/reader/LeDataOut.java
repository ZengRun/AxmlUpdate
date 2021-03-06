package com.dex2jar.reader;

import java.io.IOException;
import java.io.OutputStream;

public class LeDataOut implements DataOut {

    private OutputStream os;

    public LeDataOut(OutputStream os) {
        super();
        this.os = os;
    }

    public void writeByte(int v) throws IOException {
        os.write(v);
    }

    public void writeBytes(byte[] bs) throws IOException {
        os.write(bs);
    }

    public void writeInt(int v) throws IOException {
        os.write(v);
        os.write(v >> 8);
        os.write(v >> 16);
        os.write(v >>> 24);
    }

    public void writeShort(int v) throws IOException {
        os.write(v);
        os.write(v >> 8);
    }

}
