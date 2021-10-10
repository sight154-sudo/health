package cn.itcast.dao;

import cn.itcast.pojo.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 19:37
 * @Description:
 */
public interface RoleDao {
    /**
     * 查询所有角色数据
     * @return
     */
    List<Role> findAll();

    /**
     * 分页查询角色信息([查询参数 条件查询])
     * @param queryString
     */
    Page<Role> selectByCondition(String queryString);

    /**
     * 添加角色
     * @param role 角色对象
     */
    void addRole(Role role);

    /**
     * 编辑角色
     * @param role 角色对象
     */
    void updateRole(Role role);

    /**
     * 添加角色绑定的菜单
     * @param id 角色id
     * @param menuIds 菜单id的数组
     */
    void addRoleMenus(@Param("roleId") Integer id, @Param("menuIds") Integer[] menuIds);

    /**
     * 添加角色绑定的权限
     * @param id 角色id
     * @param permissionIds 权限id的数组
     */
    void addRolePermissions(@Param("roleId") Integer id, @Param("permissionIds") Integer[] permissionIds);

    /**
     * 通过角色id查询角色
     * @param roleId 角色id
     * @return
     */
    Role findRoleById(Integer roleId);

    /**
     * 通过角色id查询角色菜单id集合
     * @param roleId 角色id
     * @return
     */
    List<Integer> findRoleMenusById(Integer roleId);

    /**
     * 通过角色id查询角色权限id集合
     * @param roleId 角色id
     * @return
     */
    List<Integer> findRolePermissionsById(Integer roleId);

    /**
     * 删除角色菜单
     * @param id 角色id
     */
    void deleteRoleMenus(@Param("roleId") Integer id);

    /**
     * 删除角色权限
     * @param id 角色id
     */
    void deleteRolePermissions(@Param("roleId") Integer id);

    /**
     * 删除角色
     * @param roleId 角色id
     */
    void deleteRole(Integer roleId);

    /**
     * 查询该角色关联的用户数量
     * @param roleId 角色id
     * @return
     */
    Integer findUserCountByRoleId(Integer roleId);

    /**
     * 查询角色关键词
     * @param id 角色id
     * @return
     */
    String findKeywordById(Integer id);
}
