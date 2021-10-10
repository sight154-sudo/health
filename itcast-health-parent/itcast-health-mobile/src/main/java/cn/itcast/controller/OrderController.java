package cn.itcast.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Order;
import cn.itcast.service.OrderService;
import cn.itcast.util.ThreadLocalUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.OrderInfo;
import cn.itcast.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 10:51 2021/8/22
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("{id}")
    public ResponseEntity<Result> getOrderInfo(@PathVariable(value = "id") String oId) {

        try {
            Order order = orderService.findById(oId);
            if(ObjectUtil.isNotEmpty(order)){
                return ResponseEntity.ok(new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,order));
            }
        } catch (Exception e) {
            log.error("查询订单详情信息",e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false,MessageConstant.QUERY_ORDER_FAIL));
    }





    //todo
    @PostMapping(path = "/submit")
    public ResponseEntity<Result> submitOrder(@RequestBody OrderInfo orderinfo){
        try {
            if(null==ThreadLocalUtils.getUser()){
                throw new RuntimeException("Error: Request failed with status code 401");
//                return new Result(false,MessageConstant.USER_LOGIN_STATUS);
            }
            if (!orderInfoService.findOrderSettingByOrderDate(orderinfo.getOrderDate())){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false,MessageConstant.ORDER_FULL));
            }
            String id = orderInfoService.saveOrder(orderinfo);
            if (id=="1") {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false,MessageConstant.VALIDATECODE_ERROR));
            }
            return  ResponseEntity.ok(new Result(true,MessageConstant.ORDER_SUCCESS,id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result(false, MessageConstant.ORDER_FAIL,"Error: Request failed with status code 401"));
    }

}
