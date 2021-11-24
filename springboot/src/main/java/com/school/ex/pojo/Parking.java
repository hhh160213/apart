package com.school.ex.pojo;

import lombok.Data;

@Data
public class Parking {
    private int id;
    private String state;
    private String ownerId;
    private String owner;
    private String address;
}
