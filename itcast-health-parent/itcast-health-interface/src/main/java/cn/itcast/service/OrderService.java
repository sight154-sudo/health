package cn.itcast.service;

import cn.itcast.entity.OrderQueryPageBean;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Order;

public interface OrderService {

    /**
     * 查询预约列表分页
     * @param orderQueryPageBean 预约信息分页条件
     * @return
     */
    PageResult pageQuery(OrderQueryPageBean orderQueryPageBean);

    /**
     * 更新到诊状态
     * @param arrival
     * @param id
     * @return
     */
    Boolean updateArrival(String arrival,String id);

    /**
     * 根据id查询order
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * 保存order
     * @param order
     * @return
     */
    Boolean updateOrder(Order order);

    /**
     * 删除order
     * @param id
     * @return
     */
    Boolean deleteOrder(Integer id);

    /**
     * 新增order
     * @param order
     * @return
     */
    String saveOrder(Order order);
}
