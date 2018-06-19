package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
//@管理信息系统
//分析机构所有课程的订单情况

@Data
@Entity
@Table(name = "ICourseA")
public class ICourseA implements Serializable{
    private static final long serialVersionUID = 773118L;

    @Id
    @Column(name = "courseId")
    private Long courseId;

    @Column(name = "institutionCode", length = 7)
    private String institutionCode;

    //课程成交订单总数
    @Column(name = "payedOrder", columnDefinition = "INT default 0")
    private int payedOrder;

    //课程预计招生总人数，每创建一个班级都要更新一次
    @Column(name = "studentNumPlan", columnDefinition = "INT default 0")
    private int studentNumPlan;

}
