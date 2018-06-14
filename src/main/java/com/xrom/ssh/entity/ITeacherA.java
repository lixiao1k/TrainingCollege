package com.xrom.ssh.entity;

//@管理信息系统

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ITeacherA")
public class ITeacherA implements Serializable{
    private static final long serialVersionUID = 1738L;

    //教师ID
    @Id
    private Long id;


    //所有成交订单数
    @Column(name = "payedOrder", columnDefinition = "INT default 0")
    private int payedOrder;

    //总课程预计招生数
    @Column(name = "studentNumPlan", columnDefinition = "INT default 0")
    private int studentNumPlan;
}
