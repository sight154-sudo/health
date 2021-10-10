package cn.itcast.service;

import java.util.Map;

/**
 * @author: tang
 * @date: Create in 19:08 2021/8/22
 * @description:
 */
public interface PayService {
    String queryPayUrl(String id,Integer payTotal,String name);

    Map queryStatus(String orderId);
}
