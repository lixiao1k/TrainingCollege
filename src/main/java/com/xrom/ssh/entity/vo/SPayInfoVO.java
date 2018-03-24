package com.xrom.ssh.entity.vo;

import lombok.Data;

@Data
public class SPayInfoVO {
    private Long orderId;

    private int orderRawMoney;

    private String cardNumber;

    private int cardBalance;

    private int bpBalance = 0;

    private int moneyFromBP = 0;

    private int moneyNeedPay;
}
