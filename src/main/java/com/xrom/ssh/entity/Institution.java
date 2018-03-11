package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "institution")
public class Institution implements Serializable{
    private static final long serialVersionUID = 748346728L;

    @Id
    @Column(name = "code", length = 7, nullable = false, unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "password")
    private String password;
}
