package cn.itcast.dao;

import cn.itcast.pojo.Order;
import cn.itcast.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hanjunjie
 * @date 2021/8/20 18:03
 */
public interface OrderSettingDao {

    /**
     * 修改设置的对象
     * @param orderSetting 对象
     */
    void updateByDate(OrderSetting orderSetting);

    /**
     * 根绝月份查数据
     * @param minDay
     * @param maxDay
     * @return
     */
    List<Map<String,Object>> findOrderSettingsByMonth(@Param("minDay") Date minDay, @Param("maxDay") Date maxDay);

    /**
     * 根据时间查询对象
     * @param date
     * @return
     */
    OrderSetting findOrderSetting(Date date);

    /**
     * 修改数据
     * @param orderSetting
     */
    void  update(OrderSetting orderSetting);

    /**
     * 根据时间进行修改
     * @param date
     */
    void updateByTime(@Param("date") Date date,@Param("number") int number);

    /**
     *根据预约时间查询预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findOrderSettingByOrderDate(Date orderDate);
}
