package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//@管理信息系统
//到课率，以周为单位，课程有多少周，每周有多少签到，总共有多少学生

@Data
@Entity
@Table(name = "ICourseSignA")
public class ICourseSignA implements Serializable{
    private static final long serialVersionUID = 1731438L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "courseId")
    private Long courseId;

    @Column(name = "code", length = 7)
    private String code;

    @Column(name = "week", columnDefinition = "INT default 0")
    private int week;

    @Column(name = "signAmount", columnDefinition = "INT default 0")
    private int signAmount;
}
