package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Role;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 19:40
 * @Description:
 */
public interface RoleService {

    /**
     * 查询所有角色信息
     *
     * @return
     */
    List<Role> findAll();

    /**
     * 分页查询角色信息
     * @param queryPageBean 分页参数对象
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 添加角色
     * @param permissionIds 权限id数组
     * @param menuIds 菜单id数组
     * @param role 角色对象
     */
    void addRole(Role role, Integer[] permissionIds, Integer[] menuIds);

    /**
     * 通过id查询角色
     * @param roleId 角色id
     * @return
     */
    Role findRoleById(Integer roleId);

    /**
     * 通过id查询角色菜单
     * @param roleId 角色id
     * @return
     */
    List<Integer> findRoleMenusById(Integer roleId);

    /**
     * 通过id查询角色权限
     * @param roleId
     * @return
     */
    List<Integer> findRolePermissionsById(Integer roleId);

    /**
     * 编辑角色
     * @param role 角色对象
     * @param permissionIds 权限id数组
     * @param menuIds 菜单id数组
     */
    void updateRole(Role role, Integer[] permissionIds, Integer[] menuIds);

    /**
     * 删除角色
     * @param roleId 角色id
     */
    void deleteRole(Integer roleId);

    /**
     * 通过角色id查询角色关键字
     * @param id
     * @return
     */
    String findKeywordById(Integer id);
}
