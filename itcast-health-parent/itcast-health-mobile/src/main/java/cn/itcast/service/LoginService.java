package cn.itcast.service;

import cn.itcast.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void sendValidateCode(String phoneNumber) {
        try {
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,phoneNumber,"123456");
            String redisKey = SMSUtils.VALIDATE_CODE+"_"+phoneNumber;
            String code="123456";
            redisTemplate.opsForValue().set(redisKey,code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
