package cn.itcast.config;


import cn.itcast.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity // 开启WebSecurity模式
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 定制请求的授权规则
        // 首页所有人可以访问
        http.authorizeRequests()    // 开启权限控制,通过ANT规范，自定义逻辑
                // 跨域预检请求
                .antMatchers("/css/**", "/js/**", "/img/**", "/plugins/**", "/favicon.ico","/login.html").permitAll()
                // 登录页面
                .antMatchers("/member/**").hasAuthority("ROLE_USER")
                //.antMatchers("/")
                //.antMatchers("/user/**", "/pages/main.html").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                //.antMatchers("/**").hasAnyRole("ROLE_ADMIN")
                .anyRequest().authenticated()//任意请求 必须经过认证 只要登陆就能访问
                .and()
                // 自定义登录页面,登录成功页面,登录失败页面
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/pages/main.html")
                .failureForwardUrl("/login.html")
                // frame同源配置
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                // 关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
                .and()
                .csrf()
                .disable()
                // 注销
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/login.html");
    }


    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    // 密码不加密
    //@Bean
    //public PasswordEncoder bCryptPasswordEncoder(){
    //    return NoOpPasswordEncoder.getInstance();
    //}
    // 密码加密
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
