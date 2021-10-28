package com.gao.rank.service;

import com.gao.rank.model.OrderedInfo;
import com.gao.rank.model.PhoneInfo;
import com.gao.rank.model.PhoneRank;
import com.gao.rank.utils.StringUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/10/27 21:49
 */
@Service
public class phoneServiceImpl implements phoneService{
    private static final String BUY_DYNAMIC="phone:buy:dynamic";
    private static final String SALE_LIST="phone:sales:list";
    private static final String SEPARATOR="#";
    List<PhoneRank> list= Arrays.asList(
            new PhoneRank(1, "苹果"),
            new PhoneRank(2, "小米"),
            new PhoneRank(3, "华为"),
            new PhoneRank(4, "oppo"),
            new PhoneRank(5, "vivo"));

    Jedis jedis=null;
    @PostConstruct
    public void initRedisClient () {
        // 注：保持代码简介，未使用 JedisPool 生产环境 应使用连接池
        jedis = new Jedis("127.0.0.1", 6379);

    }

    @Override
    public int getPhoneRanking(Integer id) {
        // 如果是排名第一， 返回的是0 ，因此如果为null 即不在排行榜上则返回-1
        Long zrank = jedis.zrevrank(SALE_LIST, String.valueOf(id));
        if (id==5){
            System.out.println("vivo"+ zrank);
        }
        return zrank == null ? -1 : zrank.intValue();
    }

    @Override
    public List<PhoneInfo> getPhoneInfo() {
        Set<Tuple> tuples = jedis.zrevrangeWithScores(SALE_LIST, 0, 4);
        List<PhoneInfo> phoneInfoList=new ArrayList<>();
        for (Tuple tuple:tuples){
            PhoneInfo phoneInfo = new PhoneInfo();
            int i = Integer.parseInt(tuple.getElement());
//            System.out.println(i);
//            System.out.println(list.size());
            phoneInfo.setName(list.get(i-1).getName());
            phoneInfo.setSales((int)tuple.getScore());
            phoneInfoList.add(phoneInfo);
        }
        return phoneInfoList;
    }

    @Override
    public List<OrderedInfo> getOrderd() {
        List<OrderedInfo> orderedInfoList=new ArrayList<>();
        for (int i=0;i<3;i++){
            String lindex = jedis.lindex(BUY_DYNAMIC, i);
            if (StringUtils.isEmpty(lindex))
                break;
            String[] split = lindex.split(SEPARATOR);

            long time=Long.valueOf(split[0]);
            String name=split[1];
            System.out.println(time+" "+name);
            OrderedInfo orderedInfo=new OrderedInfo();
            orderedInfo.setName(name);
            orderedInfo.setTime(StringUtil.showTime(new Date(time)));
            orderedInfoList.add(orderedInfo);
        }
        jedis.ltrim(BUY_DYNAMIC,0,19);
        return orderedInfoList;
    }

    @Override
    public void buyPhone(int phoneId) {
        jedis.zincrby(SALE_LIST, 1, String.valueOf(phoneId));
        //为有序集 key 的成员 member 的 score 值加上增量 increment
        long l = System.currentTimeMillis();
        String msg=l+SEPARATOR+list.get(phoneId-1).getName();
        jedis.lpush(BUY_DYNAMIC,msg);
    }

    @Override
    public void clear() {
        jedis.del(SALE_LIST);
        jedis.del(BUY_DYNAMIC);
    }
}
