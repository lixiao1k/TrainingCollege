package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {
    private static final long serialVersionUID = 744339228L;

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "total_consumption", columnDefinition = "INT default 0")
    private int totalConsumption;

    //积分余额
    @Column(name = "bp_balance", columnDefinition = "INT default 0")
    private int bpBalance;
}
