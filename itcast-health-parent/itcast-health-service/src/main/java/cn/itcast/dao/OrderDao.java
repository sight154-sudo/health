package cn.itcast.dao;

import cn.itcast.entity.OrderQueryPageBean;
import cn.itcast.pojo.Order;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface OrderDao {

    /**
     * 查询预约分页数据
     *
     * @param orderQueryPageBean
     * @return
     */
    Page<Order> findByCondition(
            @Param("orderQueryPageBean") OrderQueryPageBean orderQueryPageBean);

    /**
     * 根据id更新到诊状态
     * @param id
     * @param arrival
     * @return
     */
    int updateById(@Param("arrival") Integer arrival, @Param("id") String id);

    /**
     * 根据id查询order
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * 更新order
     * @param order
     * @return
     */
    Boolean updateOrder(@Param("order") Order order);

    /**
     * 根据id删除order
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);

    /**
     * 添加order
     * @param order
     * @return
     */
    Boolean saveOrder(@Param("order")Order order);
}
