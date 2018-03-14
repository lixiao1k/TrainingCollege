package com.xrom.ssh.entity.vo;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

@lombok.Data
public class SCourseVO implements Serializable{
    private static final Long serialVersionUID = 748378L;

    private Long courseId;

    private String description;

    private String type;

    private Date beginDate;

    private Date endDate;

    private int price;

    private int hourPerWeek;

    private int weeks;

    private int grade = 0;
}
