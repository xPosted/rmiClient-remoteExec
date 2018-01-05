package com.jubaka.remoting.model.dto;

import java.io.Serializable;

/**
 * Created by root on 05.01.18.
 */

public class ClassConteiner implements Serializable {
    private String login;
    private String pass;

    private String packageName;
    private String className;
    private byte[] bytecode;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

    public void setBytecode(byte[] bytecode) {
        this.bytecode = bytecode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }



}
