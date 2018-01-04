package com.jubaka.remoting.client.impl;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by root on 04.01.18.
 */
public class RmiClient implements Serializable {

    public static void main(String[] args) {
        init("com.jubaka.remoting");
        /*
        System.setProperty("java.rmi.server.useCodebaseOnly","false");
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        ExecutorService remote = (ExecutorService)context.getBean("remoteExecutor");
        System.out.println(remote.submit(new CustomThread()));
*/
    }

    public static void init(String packegeRoot) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false))
                .setUrls(ClasspathHelper.forPackage(packegeRoot))
        );
        System.out.println(reflections.getAllTypes());



        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        System.out.println(allClasses.size());
        allClasses.forEach(cl -> {
            System.out.println(cl.getName());
            System.out.println(new File(cl.getProtectionDomain().getCodeSource().getLocation().getPath()+cl.getName().replace(".","/")+".class"));
        });
    }



}

