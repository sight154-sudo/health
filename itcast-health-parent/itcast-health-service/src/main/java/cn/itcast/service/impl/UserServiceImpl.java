package cn.itcast.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.dao.MenuDao;
import cn.itcast.dao.UserDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Menu;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import cn.itcast.utils.MD5Utils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author BlueStar
 * @date 2021/8/20 18:42
 * @Description:
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public void add(User user, int[] roleIds) {
        // 添加用户
        if (ObjectUtil.isEmpty(user)){
            return;
        }
        // 设置了密码 则给密码加密
        if (null != user.getPassword()){
            // BCryptPassword给密码加密
            String bcryptPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            // md5给密码加密
            //String md5Password = MD5Utils.md5();
            user.setPassword(bcryptPassword);
        }

        userDao.add(user);
        // 给用户添加角色
        if (null == roleIds){
            return;
        }
        this.addUserRoles(user.getId(), roleIds);
    }


    @Override
    public void update(User user, int[] roleIds) {
        if (null == user){
            return;
        }
        // 查询该密码是否是原密码 如果是原密码不作修改
        User dbUser = userDao.findById(user.getId());
        if (null != user.getPassword() && ObjectUtil.notEqual(dbUser.getPassword(), user.getPassword())){
            // md5给密码加密
            //String md5Password = MD5Utils.md5(user.getPassword());
            // BCryptPassword给密码加密
            String bcryptPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(bcryptPassword);
        }
        userDao.update(user);
        if (null == roleIds){
            return;
        }
        this.updateUserRoles(user.getId(), roleIds);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //开启分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //根据条件分页查询检查项信息
        Page<User> page = userDao.selectByCondition(queryPageBean.getQueryString());
        //构建分页数据返回
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }



    @Override
    public int[] findRoleIdsByUserId(int userId) {
        return userDao.findRoleIdsByUserId(userId);
    }



    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public Integer[] findAssociationRoleIds(Integer id) {
        // int数组 -> Integer数组
        int[] roleIds = userDao.findRoleIdsByUserId(id);
        // 数组转stream 装箱 转数组
        return Arrays.stream(roleIds).boxed().toArray(Integer[]::new);

    }

    @Override
    public List<Map<String, Object>> findMenus(String username) {
        // 根据用户名查询用户信息
        User user = userDao.findUserByUsername(username);
        // 查询用户所具有的权限列表
        List<Menu> menuList = menuDao.findUserMenus(user.getId());
        if (CollUtil.isEmpty(menuList)){
            return null;
        }
        return menuList.stream().map(menu -> {
            Map<String, Object> rootData = new HashMap<>();
            rootData.put("title", menu.getName());
            rootData.put("icon", menu.getIcon());
            if (CollUtil.isEmpty(menu.getChildren())){
                rootData.put("children", null);
                return rootData;
            }
            List<Map<String, Object>> childDataList = menu.getChildren().stream().map(childMenu -> {
                Map<String, Object> childData = new HashMap<>();
                childData.put("title", childMenu.getName());
                childData.put("linkUrl", childMenu.getLinkUrl());
                childData.put("children", null);
                return childData;
            }).collect(Collectors.toList());
            rootData.put("children", childDataList);
            return rootData;
        }).collect(Collectors.toList());

        //return null;
    }

    /**
     * 添加用户的角色
     * @param id 用户id
     * @param roleIds 角色id数组
     */
    private void addUserRoles(Integer id, int[] roleIds) {
        for (int roleId : roleIds) {
            userDao.addUserRoles(id, roleId);
        }
    }

    /**
     * 修改用户所拥有的角色
     * @param id 用户id
     * @param roleIds 角色id数组
     * @Description 先删除用户所拥有的角色 再添加用户需要新增的角色
     */
    private void updateUserRoles(Integer id, int[] roleIds){
        // 删除用户所有角色
        userDao.deleteUserRoles(id);
        // 添加用户角色
        this.addUserRoles(id, roleIds);
    }
}
