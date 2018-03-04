package com.xrom.ssh.exceptions;

public class RepeatInsertException extends Exception {
    @Override
    public String toString() {
        return "重复插入值";
    }
}
