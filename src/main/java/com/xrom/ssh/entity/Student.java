package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String email;

    @Column(name = "level", nullable = false, columnDefinition = "INT default 0")
    private int level;

    @Column(name = "user_name")
    private String userName;
//用户状态：未验证——0，已验证——1，已取消——2
    @Column(name = "user_state", nullable = false, columnDefinition = "INT default 0")
    private int userState;

}
