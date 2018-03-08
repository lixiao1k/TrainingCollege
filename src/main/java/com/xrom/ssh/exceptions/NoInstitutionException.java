package com.xrom.ssh.exceptions;

public class NoInstitutionException extends Exception{
    @Override
    public String toString() {
        return "没有找到这个机构！";
    }
}
