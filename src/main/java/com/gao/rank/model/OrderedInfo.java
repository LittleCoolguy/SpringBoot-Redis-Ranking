package com.gao.rank.model;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/10/27 21:48
 */
public class OrderedInfo {
    private String name;
    private String time;

    public OrderedInfo(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public OrderedInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
