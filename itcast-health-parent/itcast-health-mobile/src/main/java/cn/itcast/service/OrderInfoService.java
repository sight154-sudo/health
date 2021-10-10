package cn.itcast.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.system.UserInfo;
import cn.itcast.pojo.Member;
import cn.itcast.pojo.Order;
import cn.itcast.pojo.OrderInfo;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.util.ThreadLocalUtils;
import cn.itcast.utils.SMSUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderInfoService {

    @Reference
    private OrderSettingService orderSettingService;

    @Reference
    private OrderService orderService;

    @Reference
    private AddressService addressService;

    @Autowired
    private RedisTemplate redisTemplate;

    public String saveOrder(OrderInfo orderinfo) {

        String code = orderinfo.getValidateCode();
        String redisKey = SMSUtils.VALIDATE_CODE + "_" + orderinfo.getPhoneNumber();
        if (!ObjectUtil.equal(code, redisTemplate.opsForValue().get(redisKey))) {
            return "1";//验证码错误
        }
        Order order = new Order();
        Member member = ThreadLocalUtils.getUser();
        order.setMemberId(member.getId());
        order.setMember(member);
        order.setOrderType("微信预约");
        order.setName(orderinfo.getName());
        order.setPayStatus(0);
        order.setOrderStatus(0);
        order.setSetmealId(orderinfo.getSetmealId());
        order.setOrderDate(orderinfo.getOrderDate());
        order.setIdCard(orderinfo.getIdCard());
        order.setAddressId(orderinfo.getAddressId());
        order.setSex(orderinfo.getSex());
        String id = orderService.saveOrder(order);
        return id;//添加成功
    }

    /**
     * 查询预约是否满
     *
     * @param orderDate
     */
    public boolean findOrderSettingByOrderDate(Date orderDate) {
        OrderSetting orderSetting = orderSettingService.findOrderSettingByOrderDate(orderDate);
        if (orderSetting.getNumber() > orderSetting.getReservations()) {
            return true;
        }
        return false;
    }
}
