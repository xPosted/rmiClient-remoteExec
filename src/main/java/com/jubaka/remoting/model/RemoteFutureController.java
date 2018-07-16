package com.jubaka.remoting.model;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface RemoteFutureController {
    public Object get(long id) throws InterruptedException, ExecutionException;

    public Object get(long id, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

    public boolean isDone(long id);

    public boolean isCancelled(long id);

    public boolean cancel(long id,boolean mayInterruptIfRunning);
}
