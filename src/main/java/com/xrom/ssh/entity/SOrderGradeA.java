package com.xrom.ssh.entity;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//@管理信息系统
//存储学生所有已结束课程的比例分析
@Data
@Entity
@Table(name = "SOrderGradeA")
public class SOrderGradeA implements Serializable{
    private static final long serialVersionUID = 7972238L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sid")
    private Long sid;

    //优秀课程数
    @Column(name = "excellent", columnDefinition = "INT default 0")
    private int excellent;

    //良好课程数
    @Column(name = "good", columnDefinition = "INT default 0")
    private int good;


    //合格课程数
    @Column(name = "pass", columnDefinition = "INT default 0")
    private int pass;

    //不合格课程数
    @Column(name = "fail", columnDefinition = "INT default 0")
    private int fail;

}
