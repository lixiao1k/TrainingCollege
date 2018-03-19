package com.xrom.ssh.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MInstitutionVO implements Serializable{
    private static final long serialVersionUID = 7998346728L;

    private String code;

    private String name;

    private String address;

    private String phone;

    private String description;

    private String password;

    private int coursesNum = 0;

    private int openedCoursesNum = 0;

}
