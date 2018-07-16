package com.jubaka.remoting.socket.factory;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import com.jubaka.remoting.socket.XorSocket;

public class XorClientSocketFactory
        implements RMIClientSocketFactory, Serializable {

    private static final byte patternDefault = (byte) 0xAF;
    private byte pattern;

    public XorClientSocketFactory() {
        this.pattern = patternDefault;
    }

    public XorClientSocketFactory(byte pattern) {
        this.pattern = pattern;
    }

    public Socket createSocket(String host, int port)
            throws IOException
    {
        return new XorSocket(host, port, pattern);
    }

    public int hashCode() {
        return (int) pattern;
    }

    public boolean equals(Object obj) {
        return (getClass() == obj.getClass() &&
                pattern == ((XorClientSocketFactory) obj).pattern);
    }
}
