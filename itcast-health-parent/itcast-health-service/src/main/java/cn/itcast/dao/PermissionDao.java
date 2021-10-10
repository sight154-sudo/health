package cn.itcast.dao;

import cn.itcast.pojo.Permission;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/21 10:18
 * @Description:
 */
public interface PermissionDao {
    /**
     * 查询权限集合
     * @return
     */
    List<Permission> findAll();

    /**
     * 查询菜单id数组关联的权限id集合
     * @param menuIds
     * @return
     */
    List<Permission> findMenuAssociationPermission(@Param("menuIds") Integer[] menuIds);

    /**
     * 分页查询权限列表
     * @param queryString 查询字符串
     * @return
     */
    Page<Permission> findByPage(String queryString);

    /**
     * 查询权限详情
     * @param id 权限id
     * @return
     */
    Permission findById(Integer id);
    /**
     * 添加权限
     * @param permission 权限对象
     */
    void addPermission(Permission permission);

    /**
     * 编辑权限
     * @param permission 权限对象
     */
    void editPermission(Permission permission);

    /**
     * 删除权限
     * @param id 权限id
     */
    void deletePermission(Integer id);

    Integer findMenusByPermissionId(Integer id);

    Integer findRolesByPermissionId(Integer id);
}
