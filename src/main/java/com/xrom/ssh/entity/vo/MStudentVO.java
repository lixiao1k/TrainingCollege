package com.xrom.ssh.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MStudentVO implements Serializable{
    private static final long serialVersionUID = 7483956248L;

    private Long id;

    private String email;

    private int level = 0;

    private String userName;

    private String password;

    //用户状态：未验证——0，已验证——1，已取消——2
    private int userState;

    private String cardNumber = "无";

    private int totalConsumption = 0;

    private int bpBalance = 0;
}
