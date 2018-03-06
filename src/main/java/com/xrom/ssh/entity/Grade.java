package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "grade")
    private int grade;
}
