package com.xrom.ssh.exceptions;

public class CreateSameCardException extends Exception {
    @Override
    public String toString() {
        return "此卡已经被绑定";
    }
}
