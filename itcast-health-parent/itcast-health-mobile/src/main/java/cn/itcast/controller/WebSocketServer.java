package cn.itcast.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.service.CheckPayLogService;
import cn.itcast.service.PayService;
import cn.itcast.service.impl.CheckPayLogServiceImpl;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.zookeeper.Op;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 22:33 2021/8/22
 * @description:
 */
@Controller
@ServerEndpoint("/pay/status/{orderId}")
public class WebSocketServer {

    /*@Reference
    private static PayService payService;

    @Autowired
    public static void setPayService(PayService payService) {
        WebSocketServer.payService = payService;
    }*/
    private static CheckPayLogService checkPayLogService;
    @Autowired
    public static void setCheckPayLogService(CheckPayLogService checkPayLogService) {
        WebSocketServer.checkPayLogService = checkPayLogService;
    }
    /*private CheckPayLogService checkPayLogService;

    @PostConstruct
    public void init(){
        if(ObjectUtil.isEmpty(checkPayLogService)){
            checkPayLogService = new CheckPayLogServiceImpl();
        }
    }*/
    @OnOpen
    public void queryStatus(Session session, @PathParam("orderId")String orderId) {
        System.out.println(orderId);
        try {
//            Thread.sleep(3000);
            Result result = null;
            /*if (true) {
                result = new Result(true, MessageConstant.PAY_SUCCESS, "SUCCESS");
                try {
                    session.getBasicRemote().sendText("SUCCESS");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            int x = 0;//计数器
            while (true) {
//                Map map = u.queryStatus(orderId);
                Map map = checkPayLogService.queryStatus(orderId);
                if (map == null) {//支付出错了
                    session.getBasicRemote().sendText("PAYFAIL");
                    break;
                }
                if (map.get("trade_state").equals("SUCCESS")) {
                    session.getBasicRemote().sendText("SUCCESS");
                    break;
                }
                Thread.sleep(1000);//等待3秒
                x++;
                if (x >= 20) {
                    session.getBasicRemote().sendText("PAYTIMEOUT");
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
