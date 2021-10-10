package cn.itcast.service;

import java.util.Map;

/**
 * @author: tang
 * @date: Create in 23:36 2021/8/22
 * @description:
 */
public interface CheckPayLogService {
    Map queryStatus(String orderId);
}
