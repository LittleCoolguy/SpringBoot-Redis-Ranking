package com.gao.rank.controller;

import com.gao.rank.model.OrderedInfo;
import com.gao.rank.model.PhoneInfo;
import com.gao.rank.model.PhoneRank;
import com.gao.rank.service.phoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/10/27 21:49
 */
@Controller
public class phoneController {
    List<PhoneRank> phones = Arrays.asList(
            new PhoneRank(1, "苹果"),
            new PhoneRank(2, "小米"),
            new PhoneRank(3, "华为"),
            new PhoneRank(4, "oppo"),
            new PhoneRank(5, "vivo"));

    @Autowired
    phoneService phoneService;
    /**
     * 这块是什么意思？？
     */

    @RequestMapping("/")
    public ModelAndView home(){
        for (PhoneRank phone:phones){
            int ranking=phoneService.getPhoneRanking(phone.getId())+1;
            //一个问题，为什么要加+1，直接返回0不可以吗
            phone.setRanking(ranking==0 ? "辣鸡手机，没人买" : "销量排名： "+ranking);
        }
        //1.获取手机销量信息,生成商品列表
        ModelAndView view=new ModelAndView("index");
        view.addObject("phones",phones);
        List<PhoneInfo> phoneInfoList=phoneService.getPhoneInfo();
        List<OrderedInfo> orderedInfoList=phoneService.getOrderd();
        view.addObject("phbList",phoneInfoList);
        view.addObject("dynamicList",orderedInfoList);
        return view;
    }
    @RequestMapping("/buy/{phoneId}")
    public String buyPhone(@PathVariable int phoneId){
        phoneService.buyPhone(phoneId);
        return "redirect:/";
    }
    /**
     * 清空缓存
     * @return
     */
    @RequestMapping("/clear")
    public String clear() {
        phoneService.clear();
        return "redirect:/";
    }
}
