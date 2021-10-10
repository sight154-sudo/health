package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.OrderQueryPageBean;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Order;
import cn.itcast.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: liuyouqi
 * @Date: 2021/8/20 19:57
 * @Description: 预约列表
 */
@Slf4j
@RestController
@RequestMapping(path = "/order")
@CrossOrigin
public class OrderController {

    @Reference
    private OrderService orderService;

    /**
     * 查询预约列表分页
     *
     * @param orderQueryPageBean
     * @return
     */
    @GetMapping(path = "/findPage")
    public Result findPage(OrderQueryPageBean orderQueryPageBean) {
        try {
            PageResult pageResult = orderService.pageQuery(orderQueryPageBean);
            if (null != pageResult) {
                return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, pageResult);
            }
        } catch (Exception e) {
            log.error("查询order分页失败！orderQueryPageBean = " + orderQueryPageBean, e);
        }
        return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
    }

    /**
     * 根据id查找order
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") String id) {
        try {
            Order order = orderService.findById(id);
            if (null != order) {
                return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, order);
            }
        } catch (Exception e) {
            log.error("根据id查询order失败！", e);
        }
        return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
    }

    /**
     * 更新到诊状态
     *
     * @param param
     * @return
     */
    @PostMapping(path = "updateArrival")
    public Result updateArrival(@RequestBody Map<String, Object> param) {
        try {
            String id = param.get("id").toString();
            String arrival = param.get("arrival").toString();
            Boolean result = orderService.updateArrival(arrival, id);
            if (result) {
                return new Result(true, null);
            }
        } catch (Exception e) {
            log.error("更新到诊状态失败！", e);
        }
        return new Result(false, null);
    }

    /**
     * 保存order
     *
     * @return
     */
    @PostMapping(path = "submit")
    public Result updateOrder(@RequestBody Order order) {
        try {
            Boolean result = orderService.updateOrder(order);
            if (result) {
                return new Result(true, MessageConstant.CONFIRM_ORDER_SUCCESS);
            }
        } catch (Exception e) {
            log.error("保存预约失败！", e);
        }
        return new Result(false, MessageConstant.CONFIRM_ORDER_FAIL);
    }

    /**
     * 添加order
     *
     * @return
     */
    @PostMapping(path = "save")
    public Result saveOrder(@RequestBody Order order) {
        try {
            orderService.saveOrder(order);
            return new Result(true, MessageConstant.CONFIRM_ORDER_SUCCESS);
        } catch (Exception e) {
            log.error("新增预约失败！", e);
        }
        return new Result(false, MessageConstant.CONFIRM_ORDER_FAIL);
    }

    /**
     * 删除order
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public Result deleteOrder(@PathVariable("id") Integer id) {
        try {
            Boolean result = orderService.deleteOrder(id);
            if (result) {
                return new Result(true, MessageConstant.DELETE_ORDER_SUCCESS);
            }
        } catch (Exception e) {
            log.error("删除order失败！", e);
        }
        return new Result(false, MessageConstant.DELETE_ORDER_FAIL);
    }

}
