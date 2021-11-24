package com.school.ex.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("owner")
public class Owner {
    private int id;
    private String name;
    private int age;
    private String Sex;
    private String idNumber;
    private String birthPlace;
    private String apartment;
    private int doorId;
    private String tel;
    private String checkIn;
    private String haveCar;
    private String pwd;
    @TableField(exist = false)
    private String logintype;
}
