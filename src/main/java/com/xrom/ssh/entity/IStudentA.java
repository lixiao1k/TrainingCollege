package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//机构的学员信息统计
@Data
@Entity
@Table(name = "IStudentA")
public class IStudentA implements Serializable{
    private static final long serialVersionUID = 8731238L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "code", length = 7)
    private String code;

    @Column(name = "sid")
    private Long sid;
}
