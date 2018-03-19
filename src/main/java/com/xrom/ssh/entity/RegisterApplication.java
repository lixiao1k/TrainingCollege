package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "application_register")
public class RegisterApplication {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "create_time")
    private Date created_time;

    @Column(name = "is_rejected", columnDefinition = "INT default 0")
    private int is_rejected;

    @Column(name = "is_agreed", columnDefinition = "INT default 0")
    private int is_agreed;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "password")
    private String password;
}
