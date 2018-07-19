package com.jubaka.remoting.main;

import com.jubaka.remoting.model.RemoteClassLoader;
import com.jubaka.remoting.model.dto.ClassConteiner;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class RemoteExecutors {
    private static ExecutorService remote;

    private RemoteExecutors() {

    }

    public static ExecutorService getRemoteCachedThreadPool(Collection<URL> urls) throws IOException {
        if (remote != null) return remote;
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        remote = (ExecutorService)context.getBean("remoteExecutor");
        RemoteClassLoader classLoader = (RemoteClassLoader) context.getBean("remoteClassLoader");

        init(classLoader, urls);
        return remote;

    }

    public static ExecutorService getRemoteCachedThreadPool(String rootPackage) throws IOException {
        if (remote != null) return remote;
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        remote = (ExecutorService)context.getBean("remoteExecutor");
        RemoteClassLoader classLoader = (RemoteClassLoader) context.getBean("remoteClassLoader");

        init(classLoader, ClasspathHelper.forPackage(rootPackage));
        return remote;

    }

    private static void init(RemoteClassLoader classLoader, Collection<URL> urls) throws IOException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false))
                .setUrls(urls)
        );

        // bug - root package does not work properly

        System.out.println(reflections.getAllTypes());

        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        System.out.println(allClasses.size());
        allClasses.forEach(cl -> {
            try {
                ClassConteiner cc = new ClassConteiner();
                cc.setPackageName(cl.getPackage().getName());
                cc.setClassName(cl.getName());
                cc.setBytecode(Files.readAllBytes(Paths.get(cl.getProtectionDomain().getCodeSource().getLocation().getPath() + cl.getName().replace(".", "/") + ".class")));
                classLoader.loadClass(cc);
            } catch (IOException io) {
                // io.printStackTrace();
            }


        });
    }


}
