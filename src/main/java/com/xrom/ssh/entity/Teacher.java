package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {
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
