package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "learning_sign")
public class LearnSign implements Serializable{

    private static final long serialVersionUID = 748346728L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "date")
    private Date date;
}
