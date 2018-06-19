package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "LearnSign")
public class LearnSign implements Serializable{

    private static final long serialVersionUID = 748346728L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "class_id")
    private Long classId;

    //第几周的课签到，改为以周为单位，方便统计
    @Column(name = "week")
    private int week;
}
