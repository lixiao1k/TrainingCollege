package com.xrom.ssh.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "card")
public class Card implements Serializable{
    private static final long serialVersionUID = 74839228L;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "balance", columnDefinition = "INT default 0")
    private int balance;

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

}
