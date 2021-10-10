package cn.itcast.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.dao.RoleDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Role;
import cn.itcast.service.RoleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 19:40
 * @Description:
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        // 开启分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 根据条件查询
        Page<Role> page = roleDao.selectByCondition(queryPageBean.getQueryString());
        // 构建分页数据
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void addRole(Role role, Integer[] permissionIds, Integer[] menuIds) {
        // 1. 添加角色 返回角色id
        if (ObjectUtil.isEmpty(role)){
            throw new RuntimeException("角色为空");
        }
        roleDao.addRole(role);
        // 2. 添加角色绑定的菜单到 中间表
        if (ArrayUtil.isNotEmpty(menuIds)){
            roleDao.addRoleMenus(role.getId(), menuIds);
        }
        // 3. 添加角色绑定的权限到 中间表
        if (ArrayUtil.isNotEmpty(permissionIds[0])){
            roleDao.addRolePermissions(role.getId(), permissionIds);
        }
    }

    @Override
    public void updateRole(Role role, Integer[] permissionIds, Integer[] menuIds) {
        // 1. 修改角色
        if (ObjectUtil.isEmpty(role)){
            throw new RuntimeException("角色为空");
        }
        roleDao.updateRole(role);

        // 2. 删除角色绑定的菜单 添加新的角色菜单
        if (ArrayUtil.isEmpty(menuIds)){
            // 为空 取消角色菜单
            roleDao.deleteRoleMenus(role.getId());
        } else {
            // 不为空 修改角色菜单
            roleDao.deleteRoleMenus(role.getId());
            roleDao.addRoleMenus(role.getId(), menuIds);
        }
        // 3. 删除角色绑定的权限 添加新的角色权限
        if (ArrayUtil.isEmpty(permissionIds[0])){
            // 为空 取消角色权限
            roleDao.deleteRolePermissions(role.getId());
            return;
        }
        // 不为空 修改角色权限
        roleDao.deleteRolePermissions(role.getId());
        roleDao.addRolePermissions(role.getId(), permissionIds);

    }

    @Override
    public void deleteRole(Integer roleId) {
        // 删除角色 需要判断 角色是否有关联的用户
        // 查询用户数量 通过角色id
        Integer count = roleDao.findUserCountByRoleId(roleId);
        if (count > 0){
            // 有用户关联 无法删除
            throw new RuntimeException("该角色已被用户关联, 无法删除");
        }

        // 删除角色 需要判断 角色 是否关联了 菜单 权限
        List<Integer> menus = roleDao.findRoleMenusById(roleId);
        List<Integer> permissions = roleDao.findRolePermissionsById(roleId);
        // 有任何关联都无法删除
        if (CollUtil.isNotEmpty(menus) || CollUtil.isNotEmpty(permissions)){
            throw new RuntimeException("该角色已关联菜单或权限, 无法删除");
        }
        // 删除该角色
        roleDao.deleteRole(roleId);
        // 删除该角色所关联的菜单和权限
        roleDao.deleteRoleMenus(roleId);
        roleDao.deleteRolePermissions(roleId);
    }

    @Override
    public Role findRoleById(Integer roleId) {
        return roleDao.findRoleById(roleId);
    }

    @Override
    public List<Integer> findRoleMenusById(Integer roleId) {
        return roleDao.findRoleMenusById(roleId);
    }

    @Override
    public List<Integer> findRolePermissionsById(Integer roleId) {
        return roleDao.findRolePermissionsById(roleId);
    }

    @Override
    public String findKeywordById(Integer id) {
        return roleDao.findKeywordById(id);
    }
}
