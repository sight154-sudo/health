package cn.itcast.dao;

import cn.itcast.pojo.PayLog;

/**
 * @author: tang
 * @date: Create in 21:35 2021/8/22
 * @description:
 */
public interface PayLogDao {

    void insertPaylog(PayLog payLog);


    void updatePaylog(PayLog payLog);

    PayLog findPayLogByOid(String orderId);

}
