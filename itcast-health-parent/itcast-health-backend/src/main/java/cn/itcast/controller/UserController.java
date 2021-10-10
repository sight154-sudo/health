package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @author BlueStar
 * @date 2021/8/20 18:59
 * @Description:
 */
@RestController
@RequestMapping(path = "/user")
@CrossOrigin
public class UserController {
    @Reference
    private UserService userService;

    /**
     * 分页查询用户数据
     *
     * @param queryPageBean
     * @return
     */
    @GetMapping(path = "/page")
    public Result findPage(QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = userService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    /**
     * 添加用户
     *
     * @param roleIds 用户角色角色id集合
     * @param user    用户对象
     * @return
     */
    @PostMapping
    public Result addUser(@RequestParam("roleIds") int[] roleIds, @RequestBody User user) {
        try {
            // 添加用户(给用户添加角色)
            userService.add(user, roleIds);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id 用户id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findUserById(@PathVariable String id) {
        try {
            User user = userService.findById(Integer.parseInt(id));
            if (null != user){
                return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }

    /**
     * 查询用户角色id数组
     * @param id 用户id
     * @return
     */
    @GetMapping(path = "/{id}/roleIds")
    public Result findUserRoles(@PathVariable String id){
        try {
            int[] roleIds = userService.findRoleIdsByUserId(Integer.parseInt(id));
            if (null != roleIds){
                return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, roleIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
    }


    /**
     * 编辑用户
     * @param roleIds 角色id数组
     * @param user 用户对象
     * @return
     * @Description 编辑用户信息, 编辑用户角色
     */
    @PutMapping()
    public Result findUserRoles(@RequestParam int[] roleIds, @RequestBody User user) {
        try {

            userService.update(user, roleIds);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_USER_FAIL);
    }

    /**
     * 通过id删除用户端
     * @param id 用户id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteUserById(@PathVariable String id) {
        try {

            userService.deleteById(Integer.parseInt(id));
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_USER_FAIL);
    }
    @GetMapping("getUsername")
    public Result getUsername(Authentication authentication) {
        try {
            String name = authentication.getName();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }

    @GetMapping("getMenus")
    public Result getMenus(Authentication authentication) {
        try {
            String username = authentication.getName();
            List<Map<String, Object>> menuList = userService.findMenus(username);
            return new Result(true, MessageConstant.GET_MENU_SUCCESS, menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_MENU_FAIL);
    }
}
