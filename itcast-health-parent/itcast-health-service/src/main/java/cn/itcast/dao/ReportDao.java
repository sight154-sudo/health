package cn.itcast.dao;


import cn.itcast.vo.ReportData;

import java.util.List;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 16:59 2021/8/20
 * @description:
 */
public interface ReportDao {

    /**
     * 会员数量统计
     * @return
     */
    List<Map<String,Object>> findMemberStatistics();

    /**
     * 套餐预约统计
     * @return
     */
    List<Map<String,Object>> findSetmealStatistics();

    /**
     * 最近12个月的收入统计
     * @return
     */
    List<Map<String,Object>> findIncomeStatistics();

    /**
     * 预约订单支付订单统计
     * @return
     */
    List<Map<String,Object>> findOrderCountStastics();

    /**
     * 统计当天，本周，本月数据
     * @return
     */
    ReportData findCountStastics();

    List<Map<String,Object>> findHotSetmealStastics();

}
