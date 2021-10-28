package com.gao.rank.service;

import com.gao.rank.model.OrderedInfo;
import com.gao.rank.model.PhoneInfo;

import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/10/27 21:49
 */
public interface phoneService {
    int getPhoneRanking(Integer id);

    List<PhoneInfo> getPhoneInfo();

    List<OrderedInfo> getOrderd();

    void buyPhone(int phoneId);

    void clear();
}
