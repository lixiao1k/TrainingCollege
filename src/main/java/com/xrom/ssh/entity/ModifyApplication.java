package com.xrom.ssh.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "application_modify")
public class ModifyApplication {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "institution_code", length = 7, nullable = false)
    private String institutionCode;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "is_rejected", columnDefinition = "INT default 0")
    private int isRejected;

    @Column(name = "is_agreed", columnDefinition = "INT default 0")
    private int isAgreed;

    @Column(name = "description", length = 1000)
    private String description;
}
