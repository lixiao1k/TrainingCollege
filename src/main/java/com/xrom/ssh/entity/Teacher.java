package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable{
    private static final long serialVersionUID = 74449228L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "institution_code", length = 7, nullable = false)
    private String institutionCode;

    @Column(name = "teaching_type")
    private String teachingType;

}
