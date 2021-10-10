package cn.itcast.service.impl;

import cn.itcast.service.CheckPayLogService;
import cn.itcast.service.PayService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: tang
 * @date: Create in 23:36 2021/8/22
 * @description:
 */
@Service
public class CheckPayLogServiceImpl implements CheckPayLogService {

    @Reference
    private PayService payService;

    @Override
    public Map queryStatus(String orderId) {
        return payService.queryStatus(orderId);
    }
}
