package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "class")
public class Classroom {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "students_num_plan")
    private int studentNumPlan;

    @Column(name = "students_num_now")
    private int studentNumNow;

}
