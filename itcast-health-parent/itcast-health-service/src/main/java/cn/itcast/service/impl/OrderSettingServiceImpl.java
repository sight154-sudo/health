package cn.itcast.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.dao.OrderSettingDao;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import cn.itcast.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author hanjunjie
 * @date 2021/8/20 18:01
 */
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * 修改预约的参数
     * orderSetting 设置的对象
     */
    @Override
    public void updateOrderSetting(OrderSetting orderSetting) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(orderSetting.getOrderDate());
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderSetting.setOrderDate(date);
        OrderSetting setting = orderSettingDao.findOrderSetting(date);
        if (ObjectUtil.isEmpty(setting)){
            //为空的时候新增
            orderSettingDao.updateByDate(orderSetting);
        }
        //不为空的时候修改
            orderSettingDao.update(orderSetting);
    }

    /**
     * 根据月份查询当月所有的数据
     * @param date
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrderSettingsByMonth(String date) {
        List<Map<String,Object> > list =null;
        try {
            Date date1 = DateUtils.parseString2Date(date, "yyyy-MM");
            //获取第一天
            String firstDayOfMonth = getFirstDayOfMonth(date1);
            //获取最后一天
            String lastDayOfMonth =  getLastDayOfMonth(date1);
            Date minDay = DateUtils.parseString2Date(firstDayOfMonth);
            Date maxDay = DateUtils.parseString2Date(lastDayOfMonth);
            list = orderSettingDao.findOrderSettingsByMonth(minDay,maxDay);
            } catch (Exception e) {
            e.printStackTrace();
        }
            return list;
    }

    /**
     * Excel导入数据
     * @param readExcel
     */
    @Override
    public void upload(List<String[]> readExcel) {
        if (CollUtil.isEmpty(readExcel)){
            return;
        }
        //不为空的情况下
        for (String[] strings : readExcel) {
            String date = strings[0];
            try {
                Date datetime = DateUtils.parseString2Date(date,"yyyy/MM/dd");
                OrderSetting orderSetting = orderSettingDao.findOrderSetting(datetime);
                if (ObjectUtil.isNotEmpty(orderSetting)){
                    //不为空的时候做修改
                    orderSettingDao.updateByTime(datetime, Convert.toInt(strings[1]));
                }else{
                    //为空的时候做新增
                    orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(datetime);
                    orderSetting.setNumber(Convert.toInt(strings[1]));
                    orderSettingDao.updateByDate(orderSetting);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *根据预约时间查询预约设置
     * @param orderDate
     * @return
     */
    @Override
    public OrderSetting findOrderSettingByOrderDate(Date orderDate) {
       return orderSettingDao.findOrderSettingByOrderDate(orderDate);
    }

    /**
     * 获取当前月第一天
     * @param
     * @return
     */
    public static String getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        calendar.set(Calendar.MONTH, date.getMonth() - 1);
        // 获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String firstDays = sdf.format(calendar.getTime())+" 00:00:00";
        String firstDays = sdf.format(date.getTime());
        return firstDays;
    }

    /**
     * 获取当月最后一天
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        int month = date.getMonth();
        int year = date.getYear()+1900;
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        // 获取某月最大天数
        int lastDay=0;
        //2月的平年瑞年天数
        if(month==2) {
            lastDay = calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
        }else {
            lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // 设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      //  String lastDays = sdf.format(calendar.getTime())+" 23:59:59";
        String lastDays = sdf.format(calendar.getTime());
        return lastDays;
    }

}
