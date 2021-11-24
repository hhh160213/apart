package com.school.ex.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Car_inout {
    private int id;
    private Date inTime;
    private Date outTime;
    private String carNo;
    private int isAccess;


}
