package cn.itcast.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.itcast.config.NativePayConfig;
import cn.itcast.dao.PayLogDao;
import cn.itcast.pojo.PayLog;
import cn.itcast.service.PayService;
import cn.itcast.util.HttpClient;
import cn.itcast.util.IdWorker;
import cn.itcast.utils.CommonUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: tang
 * @date: Create in 19:09 2021/8/22
 * @description:
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private PayLogDao payLogDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String ORDER_SUFFIX_KEY = "ORDER_SUFFIX";

    @Override
    public String queryPayUrl(String id, Integer payTotal, String name) {
        try {
            //TODO 随机生成订单id
//            id = String.valueOf(new IdWorker().nextId());
            //防止同一个订单号重复  生成二维码失败
//            String randomId = id+ CommonUtils.getRandByNum(6);
            //1.封装数据
            Map map=new HashMap<>();
            map.put("appid", NativePayConfig.WX_PAY_APPID);//应用id
            map.put("mch_id",NativePayConfig.WX_PAY_MACHID);//商户id
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            map.put("body",name);//商品描述
            map.put("out_trade_no",id);//商户订单号
            map.put("total_fee",payTotal+"");//金额
            map.put("spbill_create_ip","127.0.0.1");//终端ip;
            map.put("notify_url",NativePayConfig.WX_PAY_PAYNOTIFY_URL);//回调地址，随便写
            map.put("trade_type","NATIVE");//支付方式

            //1.2把Map类型的数据转化成xml格式
            String xmlParam = WXPayUtil.generateSignedXml(map, NativePayConfig.WX_PAY_KEY);
            System.out.println("发送请求参数："+xmlParam);
            //2.发送请求
            String url="https://api.mch.weixin.qq.com/pay/unifiedorder";
            HttpClient httpClient=new HttpClient(url);
            httpClient.setHttps(true);//支持https协议
            httpClient.setXmlParam(xmlParam);//传参xml格式的参数
            httpClient.post();//post提交方式
            //3.获取结果
            String xmlResult = httpClient.getContent();
            System.out.println("获取结果:"+xmlResult);
            //4.把xml格式转化为map类型
            Map<String, String> xmlMap = WXPayUtil.xmlToMap(xmlResult);
            //5.把code_url取出
            String code_url = xmlMap.get("code_url");

            //保存支付日志到数据库中
            PayLog payLog = new PayLog();
            payLog.setOrderId(id);
            payLog.setPayStatus(0);
            payLog.setMoney(payTotal.doubleValue());
            payLog.setCreateTime(new Date());
            payLog.setPayTime(null);
            payLog.setTransactionId(null);

            payLogDao.insertPaylog(payLog);
            //6.进行参数封装
            /*Map resultMap=new HashMap();
            resultMap.put("out_trade_no",out_trade_no);
            resultMap.put("name",name);
            resultMap.put("total_fee",total_fee);
            resultMap.put("code_url",code_url);*/

            //将订单号和生成随机数保存到redis中
//            String redisKey = ORDER_SUFFIX_KEY;
//            redisTemplate.opsForValue().set(redisKey,randomId,1, TimeUnit.HOURS);
            return code_url;
        } catch (Exception e) {
            log.error("二维码生成失败!",e);
        }
        return null;
    }

    @Override
    public Map queryStatus(String orderId) {
        try {
            System.out.println("查询订单状态!!");
//            String randomId = this.redisTemplate.opsForValue().get(ORDER_SUFFIX_KEY);
            //1.封装数据
            Map map=new HashMap();
            map.put("appid", NativePayConfig.WX_PAY_APPID);//应用id
            map.put("mch_id",NativePayConfig.WX_PAY_MACHID);//商户id
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            map.put("out_trade_no",orderId);//商户订单号
            //转化格式
            String xmlParam = WXPayUtil.generateSignedXml(map, NativePayConfig.WX_PAY_KEY);
            //2.发送请求
            String url="https://api.mch.weixin.qq.com/pay/orderquery";
            HttpClient httpClient=new HttpClient(url);
            httpClient.setHttps(true);//支持https协议
            httpClient.setXmlParam(xmlParam);//传参xml格式的参数
            httpClient.post();//post提交方式

            //3.获取结果
            String resultXml = httpClient.getContent();
            Map<String, String> xmlMap = WXPayUtil.xmlToMap(resultXml);
            System.out.println(xmlMap);

            //获取交易状态
            String trade_state = xmlMap.get("trade_state");
            String total_fee = xmlMap.get("total_fee");

            //获取微信支付订单号
            String transactionId = xmlMap.get("transaction_id");
            if(StrUtil.isNotEmpty(transactionId)){
                //保存到数据库中
                PayLog payLog = payLogDao.findPayLogByOid(orderId);
                payLog.setPayStatus(1);
                payLog.setTransactionId(transactionId);
                payLog.setPayTime(new Date());
                payLogDao.updatePaylog(payLog);
            }


            Map resultMap=new HashMap();
            resultMap.put("total_fee",total_fee);
            resultMap.put("trade_state",trade_state);
            System.out.println(resultMap);

            //清除redis
//            this.redisTemplate.delete(ORDER_SUFFIX_KEY);
            return  resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap();
    }
}
