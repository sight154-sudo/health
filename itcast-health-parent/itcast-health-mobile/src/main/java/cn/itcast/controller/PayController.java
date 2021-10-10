package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.service.PayService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: tang
 * @date: Create in 16:22 2021/8/22
 * @description:
 */
@RestController
@RequestMapping("pay")
@Slf4j
public class PayController {


    @Reference
    private PayService payService;


    /**
     * 根据订单号生成二维码
     *
     * @param id
     * @return
     */
    @GetMapping("wxPay")
    public ResponseEntity<Result> queryPayUrl(@RequestParam("orderId") String id,
                                              @RequestParam("payTotal") Integer payTotal,
                                              @RequestParam("name") String name) {
        //id 为订单号
        try {
            String url = payService.queryPayUrl(id, payTotal, name);
            return ResponseEntity.ok(new Result(true, MessageConstant.GET_PAYURL_SUCCESS, url));
        } catch (Exception e) {
            log.error("二维码生成失败!", e);
        }
        return ResponseEntity.ok(new Result(false, MessageConstant.GET_PAYURL_FAIL));

    }


//    @seren("status/{orderId}")
    public ResponseEntity<Result> queryStatus(@PathVariable("orderId") String orderId) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Result result = null;
        if (true) {
            result = new Result(true, MessageConstant.PAY_SUCCESS, "SUCCESS");
            return ResponseEntity.ok(result);
        }

        int x = 0;//计数器

        while (true) {
            Map map = payService.queryStatus(orderId);
            if (map == null) {//支付出错了
                result = new Result(false, MessageConstant.PAY_FAIL);
                break;
            }
            if (map.get("trade_state").equals("SUCCESS")) {
                result = new Result(true, MessageConstant.PAY_SUCCESS, "SUCCESS");
                break;
            }
            try {
                Thread.sleep(3000);//等待3秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if (x >= 20) {
                result = new Result(false, MessageConstant.CODE_TIMEOUT);
                break;
            }
        }
        return ResponseEntity.ok(result);
    }
}
