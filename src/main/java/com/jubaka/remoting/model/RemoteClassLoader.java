package com.jubaka.remoting.model;


import com.jubaka.remoting.model.dto.ClassConteiner;

/**
 * Created by root on 05.01.18.
 */

public interface RemoteClassLoader {

    public boolean loadClass(ClassConteiner cContainer);
}
