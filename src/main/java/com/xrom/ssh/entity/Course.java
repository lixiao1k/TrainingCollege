package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "begin_date", nullable = false)
    private Date beginDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "type")
    private String type;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "hour_per_week", columnDefinition = "INT default 0")
    private int hourPerWeek;

    @Column(name = "weeks", columnDefinition = "INT default 0")
    private int weeks;

    @Column(name = "institution_code", length = 7, nullable = false)
    private String institutionCode;

    @Column(name = "price", columnDefinition = "INT default 0")
    private int price;
}
