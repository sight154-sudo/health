package cn.itcast.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import cn.itcast.utils.AppJwtUtil;
import cn.itcast.utils.CookieUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author hanjunjie
 * @date 2021/8/22 11:53
 */
@RestController
@CrossOrigin
@RequestMapping("/member")
public class MemberController {
    public static final String key = "VALIDATE_CODE";
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Reference(version = "1.0.0")
    private MemberService memberService;

    @CrossOrigin
    @RequestMapping("/login")
    public Result login(@RequestBody Map<String,String> params,HttpServletResponse response,HttpServletRequest request) {
        //获取请求的参数
        String phone = params.get("phone");
        String validateCode = params.get("validateCode");
        String redisKey = key+phone;
        String code = redisTemplate.opsForValue().get(redisKey);
        //验证手机号码
        if (!ObjectUtil.equal(code,validateCode)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        redisTemplate.delete(redisKey);
        //判断是否是新用户,是新用户就进行注册
       Member member = memberService.findByPhoneNumber(phone);
        if (ObjectUtil.isEmpty(member)){
            member = new Member();
            member.setPhoneNumber(phone);
            memberService.add(member);
        }
//        CookieUtils.setCookie(request,response,"username","lisi",300);
        //生成token
        String token = AppJwtUtil.getToken(Convert.toLong(member.getId()));
        //设置token1
        return  new Result(true,MessageConstant.LOGIN_SUCCESS,token);
    }
}
