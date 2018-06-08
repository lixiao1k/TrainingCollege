package com.xrom.ssh.entity.vo;

import com.xrom.ssh.entity.Order;
import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//统计学员的订单总数、总额、平均订单单价（年、季度、月）
@Data
public class OrderStatistics implements Serializable {
    private static final long serialVersionUID = 7839228L;

    private Long sid;
//    Long 是year，Integer是这个年份的订单数；
    private HashMap<Integer,Integer> map_amount_year = new HashMap<>();
    // Long 是year，Integer是这个年份的订单总价
    private HashMap<Integer,Integer> map_totalPrice_year = new HashMap<>();

    private HashMap<Integer,Integer> map_amount_season = new HashMap<>();

    private HashMap<Integer,Integer> map_totalPrice_season = new HashMap<>();

    private HashMap<Integer,Integer> map_amount_month = new HashMap<>();

    private HashMap<Integer,Integer> map_totalPrice_month = new HashMap<>();

    //增加一个订单记录
    public void addOrder(OrderVO order){
        Date date = order.getPayedTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        //确保month唯一
        int month = calendar.get(Calendar.MONTH) + (year-1)*12;
        //同理
        int season = (int)Math.floor(month/3) + 4*(year-1);

        int price = order.getPrice();

        if(map_amount_month.containsKey(month)){
            map_amount_year.put(year, map_amount_year.get(year) + 1);
            map_totalPrice_year.put(year, map_totalPrice_year.get(year) + price);
            map_amount_season.put(season, map_amount_season.get(season) + 1);
            map_totalPrice_season.put(season, map_totalPrice_season.get(season) + price);
            map_amount_month.put(month, map_amount_month.get(month) + 1);
            map_totalPrice_month.put(month, map_totalPrice_month.get(month) + price);
        }else {
            map_amount_year.put(year,0);
            map_totalPrice_year.put(year,0);
            map_amount_season.put(season,0);
            map_totalPrice_season.put(season,0);
            map_amount_month.put(month,0);
            map_totalPrice_month.put(month,0);
        }
    }
}
