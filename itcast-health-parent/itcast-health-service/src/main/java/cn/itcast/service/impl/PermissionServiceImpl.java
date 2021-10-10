package cn.itcast.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.dao.PermissionDao;
import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Permission;
import cn.itcast.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/21 10:21
 * @Description:
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public List<Permission> findMenuAssociationPermission(Integer[] menuIds) {

        return permissionDao.findMenuAssociationPermission(menuIds);
    }

    @Override
    public PageResult findByPage(Integer currentPage, Integer pageSize, String queryString) {
        // 开启分页
        PageHelper.startPage(currentPage, pageSize);
        // 条件查询
        Page<Permission> page = permissionDao.findByPage(queryString);

        // 返回分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Permission findById(Integer id) {

        return permissionDao.findById(id);
    }

    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDao.editPermission(permission);
    }

    @Override
    public void deletePermission(Integer id) {
        // 查询该权限是否被菜单所拥有
        Integer menusCount = permissionDao.findMenusByPermissionId(id);
        // 查询该权限是否被角色所拥有
        Integer rolesCount = permissionDao.findRolesByPermissionId(id);
        if (menusCount > 0 || rolesCount > 0){
            throw new RuntimeException("权限已经被菜单/角色所拥有");
        }
        permissionDao.deletePermission(id);
    }
}
