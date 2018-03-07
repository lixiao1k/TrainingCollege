package com.xrom.ssh.exceptions;

public class GradeExistException extends Exception {
    @Override
    public String toString() {
        return "成绩已经存在";
    }
}
