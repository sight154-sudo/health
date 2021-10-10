package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * @author hanjunjie
 * @date 2021/8/22 11:33
 */
@Slf4j
@RestController
@RequestMapping("sms")
@CrossOrigin
public class LoginController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public static final String key = "VALIDATE_CODE";

    @Autowired
    private LoginService loginService;


    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @PostMapping("sendLoginCode")
    public Result sendLoginCode(@RequestParam("phone") String phone){
        try {
            //调用方法发送短信
            String code = "123456";
          /*  try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,phone,code);
            } catch (ClientException e) {
                e.printStackTrace();
            }*/
            //短信发送完后将数据保存到redis中,设置超时时间为5分钟
            String redisKey = key+phone;
            redisTemplate.opsForValue().set(redisKey,code, Duration.ofMinutes(5));
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 发送短信验证码接口
     *
     * @param phoneNumber
     * @return
     */
    @PostMapping(path = "/sendCode")
    public Result sendValidateCode(@RequestParam("phone") String phoneNumber) {
        try {
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,phoneNumber,"123456");
            loginService.sendValidateCode(phoneNumber);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
