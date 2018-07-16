package com.jubaka.remoting.socket;

import java.io.*;

public class XorOutputStream extends FilterOutputStream {

    /*
     * The byte used to "encrypt" each byte of data.
     */
    private final byte pattern;

    /*
     * Constructs an output stream that uses the specified pattern
     * to "encrypt" each byte of data.
     */
    public XorOutputStream(OutputStream out, byte pattern) {
        super(out);
        this.pattern = pattern;
    }

    /*
     * XOR's the byte being written with the pattern
     * and writes the result.
     */
    public void write(int b) throws IOException {
        out.write((b ^ pattern) & 0xFF);
    }

    public void write(byte b[]) throws IOException {
        byte[] encBuf = new byte[b.length];
        for (int i =0; i< b.length; i++)
            encBuf[i] = (byte)((b[i] ^ pattern) & 0xFF);
        out.write(encBuf, 0, b.length);
    }
}