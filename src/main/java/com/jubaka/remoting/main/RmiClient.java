package com.jubaka.remoting.main;

import com.jubaka.remoting.client.impl.CustomThread;
import com.jubaka.remoting.model.RemoteClassLoader;
import com.jubaka.remoting.model.dto.ClassConteiner;
import com.jubaka.remoting.model.impl.FutureRemote;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 04.01.18.
 */
public class RmiClient implements Serializable {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        ExecutorService remote = (ExecutorService)context.getBean("remoteExecutor");
        RemoteClassLoader classLoader = (RemoteClassLoader) context.getBean("remoteClassLoader");

        init(classLoader, "com.jubaka.remoting.client.impl");
        //remote.execute(new CustomThread());
        Future<String> future = remote.submit(new CustomThread());
        System.out.println(future.get(1, TimeUnit.MINUTES));
    }

    public static void init(RemoteClassLoader classLoader, String packegeRoot) throws IOException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false))
                .setUrls(ClasspathHelper.forPackage(packegeRoot))
        );
        // bug - root package does not work properly

        System.out.println(reflections.getAllTypes());

        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        System.out.println(allClasses.size());
        allClasses.forEach(cl -> {
            try {
                if ( ! cl.getPackage().getName().startsWith(packegeRoot)) return;
                ClassConteiner cc = new ClassConteiner();
                cc.setPackageName(cl.getPackage().getName());
                cc.setClassName(cl.getName());
                cc.setBytecode(Files.readAllBytes(Paths.get(cl.getProtectionDomain().getCodeSource().getLocation().getPath()+cl.getName().replace(".","/")+".class")));
                classLoader.loadClass(cc);
            } catch (Exception io) {
               // io.printStackTrace();
            }


        });
    }



}

