package com.school.ex.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
//@Alias("owner")
public class Repairer {
    private int id;
    private String name;
    private String idNumber;
    private String apartment;
    private int doorId;
    private String tel;
    private String categorya;
    private String description;
    private String complete;

}
