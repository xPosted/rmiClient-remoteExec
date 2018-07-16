package com.jubaka.remoting.client.impl;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by root on 04.01.18.
 */
public class CustomThread implements Callable<String>, Serializable {
    static String ver = "10";
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
   // @Override
    public void run() {
        System.out.println("RemoteProcessV225");
    }

    @Override
    public String call() throws Exception {

        return "niga niga niga nigasdfgsdfgsdfg";
    }
}
