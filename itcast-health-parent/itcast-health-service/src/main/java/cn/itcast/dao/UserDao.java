package cn.itcast.dao;

import cn.itcast.pojo.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 17:03
 * @Description: 用户数据访问层
 */
public interface UserDao {
    /**
     * 添加用户
     * @param user 用户对象
     */
    void add(User user);

    /**
     * 查找全部用户
     * @return
     */
    List<User> findAll();

    /**
     * 分页查询用户 [按条件(用户名)]
     * @param queryString 查询字符串
     * @return
     */
    Page<User> selectByCondition(String queryString);

    /**
     * 添加用户所属角色
     * @param id 用户id
     * @param roleId 角色id
     */
    void addUserRoles(@Param("userId")Integer id, @Param("roleId") int roleId);

    /**
     * 根据用户id查询用户数据
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 通过用户id查询用户角色id数组
     * @param userId 用户id
     * @return
     */
    int[] findRoleIdsByUserId(@Param("userId") int userId);

    /**
     * 删除用户所有角色
     * @param id 用户id
     */
    void deleteUserRoles(@Param("userId") Integer id);

    /**
     * 修改用户
     * @param user 用户对象
     */
    void update(User user);

    /**
     * 通过id删除用户
     * @param id 用户id
     */
    void deleteById(Integer id);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return
     */
    User findUserByUsername(String username);
}
