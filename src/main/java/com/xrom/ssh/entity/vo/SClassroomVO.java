package com.xrom.ssh.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SClassroomVO implements Serializable{
    private static final Long serialVersionUID = 748355578L;

    private Long id;

    private Long courseId;

    private Long teacherId;

    private int studentNumPlan;

    private int studentNumNow;

    private int priceTotal = Integer.MAX_VALUE;
}
