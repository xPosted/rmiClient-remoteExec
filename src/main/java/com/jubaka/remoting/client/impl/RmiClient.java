package com.jubaka.remoting.client.impl;

import com.jubaka.remoting.model.RemoteClassLoader;
import com.jubaka.remoting.model.dto.ClassConteiner;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by root on 04.01.18.
 */
public class RmiClient implements Serializable {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        ExecutorService remote = (ExecutorService)context.getBean("remoteExecutor");
        RemoteClassLoader classLoader = (RemoteClassLoader) context.getBean("remoteClassLoader");

        init(classLoader, "com.jubaka.remoting");
        remote.execute(new CustomThread());
    }

    public static void init(RemoteClassLoader classLoader, String packegeRoot) throws IOException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false))
                .setUrls(ClasspathHelper.forPackage(packegeRoot))
        );
        System.out.println(reflections.getAllTypes());

        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        System.out.println(allClasses.size());
        allClasses.forEach(cl -> {
            try {
                ClassConteiner cc = new ClassConteiner();
                cc.setPackageName(cl.getPackage().getName());
                cc.setClassName(cl.getName());
                cc.setBytecode(Files.readAllBytes(Paths.get(cl.getProtectionDomain().getCodeSource().getLocation().getPath()+cl.getName().replace(".","/")+".class")));
                classLoader.loadClass(cc);
            } catch (IOException io) {
                io.printStackTrace();
            }


        });
    }



}

