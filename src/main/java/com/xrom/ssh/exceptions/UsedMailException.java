package com.xrom.ssh.exceptions;

public class UsedMailException extends Exception {
    @Override
    public String toString() {
        return "邮箱已注册";
    }
}
