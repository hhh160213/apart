package com.school.ex.pojo;

import lombok.Data;

@Data
public class Pay {
    private int id;
    private String payName;
    private String startTime;
    private String endTime;
    private String payMoney;
    private String status;
    private String payType;
    private String apartment;
    private int doorId;
    private String payPeople;
    private String payPeopleTel;
    private String deadLine;
}
