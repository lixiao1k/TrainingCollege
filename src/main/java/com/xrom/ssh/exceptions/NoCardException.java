package com.xrom.ssh.exceptions;

public class NoCardException extends Exception {
    @Override
    public String toString() {
        return "此用户未绑定卡";
    }
}
