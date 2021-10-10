package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Permission;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/21 10:20
 * @Description:
 */
public interface PermissionService {
    /**
     * 查询所有权限列表
     * @return
     */
    List<Permission> findAll();

    /**
     * 查询菜单id数组关联的权限id集合
     * @param menuIds 菜单id数组
     * @return
     */
    List<Permission> findMenuAssociationPermission(Integer[] menuIds);

    /**
     * 分页查询权限列表
     * @param currentPage 当前页
     * @param pageSize 单页最大条数
     * @param queryString 查询字符串
     * @return
     */
    PageResult findByPage(Integer currentPage, Integer pageSize, String queryString);

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
    void updatePermission(Permission permission);

    /**
     * 删除权限
     * @param id 权限id
     */
    void deletePermission(Integer id);
}
