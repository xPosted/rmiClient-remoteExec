package com.jubaka.remoting.model.impl;

import com.jubaka.remoting.model.RemoteFutureController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureRemote<V> implements Future, Serializable {

    static final long serialVersionUID = 0;

    private static ApplicationContext context;

    private long id;


    public FutureRemote(long id) {
        this.id = id;
    }

    public long getID() {
        return id;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (context == null)
            context = initContext();
        RemoteFutureController remoteFuture = (RemoteFutureController) context.getBean("remoteFutureController");
        return remoteFuture.cancel(id,mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        if (context == null)
            context = initContext();
        RemoteFutureController remoteFuture = (RemoteFutureController) context.getBean("remoteFutureController");
        return remoteFuture.isCancelled(id);
    }

    @Override
    public boolean isDone() {
        if (context == null)
            context = initContext();
        RemoteFutureController remoteFuture = (RemoteFutureController) context.getBean("remoteFutureController");
        return remoteFuture.isDone(id);
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        V res = null;
        if (context == null)
            context = initContext();
        RemoteFutureController remoteFuture = (RemoteFutureController) context.getBean("remoteFutureController");
        res = (V) remoteFuture.get(id);
        return res;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        V res = null;
        if (context == null)
            context = initContext();
        RemoteFutureController remoteFuture = (RemoteFutureController) context.getBean("remoteFutureController");
        res = (V) remoteFuture.get(id,timeout,unit);
        return res;
    }

    private ApplicationContext initContext() {
        return new ClassPathXmlApplicationContext("future-context.xml");
    }
}
