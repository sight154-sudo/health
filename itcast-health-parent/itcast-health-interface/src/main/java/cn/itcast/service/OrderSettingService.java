package cn.itcast.service;

import cn.itcast.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author hanjunjie
 * @date 2021/8/20 18:03
 */
public interface OrderSettingService {
    /**
     * 修改预约的参数
     * orderSetting 对象
     */
    void updateOrderSetting(OrderSetting orderSetting);

    /**
     * 根据月份查询数据
     * @param date
     * @return
     */
    List<Map<String,Object>>   getOrderSettingsByMonth(String date);

    void upload(List<String[]> readExcel);

    /**
     * 查询预约是否满
     * @param orderDate
     */
    OrderSetting findOrderSettingByOrderDate(Date orderDate);
}
