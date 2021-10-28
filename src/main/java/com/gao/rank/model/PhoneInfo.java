package com.gao.rank.model;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/10/27 21:47
 */
public class PhoneInfo {
    private Integer id;
    private String name;
    private Integer sales;

    public PhoneInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }
}
