package cn.itcast.service;

import cn.itcast.pojo.User;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询数据库,获得用户信息
        User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //获取roleId
        Integer[] ids = userService.findAssociationRoleIds(user.getId());
        //封装keyword
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Integer id : ids) {
            String keyword = roleService.findKeywordById(id);
            authorities.add(new SimpleGrantedAuthority(keyword));
        }
        // 直接获取数据库中的密码 数据库中的密码用 同样的方式加密
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        // 加密 密码为明文时
        //return new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPassword()) , authorities);

    }

}
