package com.xrom.ssh.exceptions;

public class SignInFailedException extends Exception {
    @Override
    public String toString() {
        return "邮箱或者密码错误";
    }
}
