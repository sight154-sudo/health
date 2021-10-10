package cn.itcast.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.itcast.dao.OrderDao;
import cn.itcast.entity.OrderQueryPageBean;
import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Order;
import cn.itcast.service.OrderService;
import cn.itcast.util.IdWorker;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @Author: liuyouqi
 * @Date: 2021/8/20 20:06
 * @Description: 订单管理service实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResult pageQuery(OrderQueryPageBean orderQueryPageBean) {
        PageHelper.startPage(orderQueryPageBean.getCurrentPage(), orderQueryPageBean.getPageSize());
        Page<Order> page = null;
        try {
            page = orderDao.findByCondition(orderQueryPageBean);
            if (ObjectUtil.isEmpty(page)){
                log.error("查不到预约订单！~  queryPageBean = "+orderQueryPageBean);
                return new PageResult(0L,null);
            }
            List<Order> orders = page.getResult();
            return new PageResult(page.getTotal(),orders);
        } catch (Exception e) {
            log.error("查不到预约订单,且出现异常！~  queryPageBean = "+orderQueryPageBean,e);
        }
        return new PageResult(0L,null);
    }

    @Override
    public Boolean updateArrival(String arrival,String id) {
        try {
            if (StrUtil.isAllNotBlank(arrival,id)){
                return orderDao.updateById(Integer.valueOf(arrival),id) >0;
            }
        } catch (Exception e) {
            log.error("更新到诊状态失败！Integer.valueOf(arrival),id  =" +Integer.valueOf(arrival)+","+id,e);
        }
        return false;
    }

    @Override
    public Order findById(String id) {
        try {
            Order order = orderDao.findById(id);
            if (ObjectUtil.isEmpty(order)){
                log.error("查不到预约订单！~  id = "+id);
                return null;
            }
            return order;
        } catch (Exception e) {
            log.error("查不到预约订单,且出现异常！~  id = "+id,e);
        }
        return null;
    }

    @Override
    public Boolean updateOrder(Order order) {
        try {
            if (ObjectUtil.isNotEmpty(order)){
                String sex = order.getSex();
                order.setSex("男".equals(sex)?"1":"2");
                Boolean result = orderDao.updateOrder(order);
                return true;
            }
        } catch (Exception e) {
            log.error("更新order失败！",e);
        }
        return false;
    }

    @Override
    public Boolean deleteOrder(Integer id) {
        try {
            return orderDao.deleteById(id);
        } catch (Exception e) {
            log.error("删除订单失败,且出现异常！~  id = "+id,e);
        }
        return false;
    }

    @Override
    public String saveOrder(Order order) {
        try {
            IdWorker idWorker = new IdWorker();
            long id = idWorker.nextId();
            order.setId(id+"");
            orderDao.saveOrder(order);
            return order.getId();
        } catch (Exception e) {
            log.error("保存order失败！order = "+order,e);
        }
        return null;
    }
}
