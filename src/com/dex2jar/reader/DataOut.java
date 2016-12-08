package com.dex2jar.reader;

import java.io.IOException;

public interface DataOut {
    void writeByte(int b) throws IOException;

    void writeBytes(byte[] bs) throws IOException;

    void writeInt(int i) throws IOException;

    void writeShort(int i) throws IOException;
}
