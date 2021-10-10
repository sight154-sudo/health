package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author BlueStar
 * @date 2021/8/20 18:42
 * @Description:
 */
public interface UserService {
    /**
     * 添加用户
     *
     * @param user 用户对象
     */
    void add(User user, int[] roleIds);

    /**
     * 分页查询用户信息
     *
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据用户id删除用户
     *
     * @param id 用户id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return
     */
    User findById(Integer id);



    /**
     * 查询用户角色id数组
     * @param userId 用户id
     * @return
     */
    int[] findRoleIdsByUserId(int userId);

    /**
     * 修改用户信息(修改用户角色)
     * @param user 用户id
     * @param roleIds 角色id数组
     */
    void update(User user, int[] roleIds);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 查询关联的角色id数组
     * @param id
     * @return
     */
    Integer[] findAssociationRoleIds(Integer id);

    /**
     * 查询动态菜单
     * @param username 用户名
     * @return
     */
    List<Map<String, Object>> findMenus(String username);
}
