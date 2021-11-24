package com.school.ex.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Property {
    private int id;
    private String name;
    private String permission;
    private String account;
    private String password;
    @TableField(exist = false)
    private String logintype;
}
