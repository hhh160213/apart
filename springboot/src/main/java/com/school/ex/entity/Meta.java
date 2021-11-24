package com.school.ex.entity;

import lombok.Data;

@Data
public class Meta {
    public Meta() {
        this.status=200;
        this.msg = "ok";
    }

    public Meta(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }

    private String msg;
    private int status;
}
