package cn.itcast.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.pojo.Member;
import cn.itcast.util.ThreadLocalUtils;
import cn.itcast.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hanjunjie
 * @date 2021/8/22 23:15
 */
@Component
public class OrderSubmitInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        //从请求头中获取token
        String token = request.getHeader("Authorization");
        if (ObjectUtil.isNotEmpty(token)){
            Claims claims = AppJwtUtil.getClaimsBody(token);
            int count = AppJwtUtil.verifyToken(claims);
            if (count==-1||count==0){
                //token有效
                Member user = new Member();
                user.setId(Convert.toInt(claims.get("id")+""));
                //将User对象放入ThreadLocal中
                ThreadLocalUtils.setUser(user);
                return true;
            }else {
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
