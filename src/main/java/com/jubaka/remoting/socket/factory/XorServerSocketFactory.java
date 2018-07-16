package com.jubaka.remoting.socket.factory;

import com.jubaka.remoting.socket.XorServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

public class XorServerSocketFactory
        implements RMIServerSocketFactory {

    private static final byte patternDefault = (byte) 0xAF;
    private byte pattern;

    public XorServerSocketFactory(byte pattern) {
        this.pattern = pattern;
    }

    public XorServerSocketFactory() {
        this.pattern = patternDefault;
    }

    public ServerSocket createServerSocket(int port)
            throws IOException
    {
        return new XorServerSocket(port, pattern);
    }

    public int hashCode() {
        return (int) pattern;
    }

    public boolean equals(Object obj) {
        return (getClass() == obj.getClass() &&
                pattern == ((XorServerSocketFactory) obj).pattern);
    }

}