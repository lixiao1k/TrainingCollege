package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "grade")
public class Grade implements Serializable{
    private static final long serialVersionUID = 748393678L;

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
