package com.gao.rank.model;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/10/27 21:29
 */
public class PhoneRank {
    private Integer id;
    private String name;
    private String ranking;

    public PhoneRank() {
    }

    public PhoneRank(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
