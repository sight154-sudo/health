package cn.itcast.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.dao.MenuDao;
import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Menu;
import cn.itcast.service.MenuService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 23:34
 * @Description:
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        // 开启分页查询
        PageHelper.startPage(currentPage, pageSize);
        // 条件查询
        Page<Menu> page = menuDao.selectByPage(queryString);
        // 返回分页查询结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public List<Integer> findPermissionsById(Integer id) {
        return menuDao.findPermissionsById(id);
    }

    @Override
    public void addMenu(Menu menu, Integer[] permissionIds) {
        // 添加菜单 注意返回id
        menuDao.add(menu);
        // 权限的添加(菜单_权限表)
        menuDao.addMenuPermissions(menu.getId(), permissionIds);
    }


    @Override
    public void updateMenu(Menu menu, Integer[] permissionIds) {
        // 编辑菜单信息
        if (ObjectUtil.isEmpty(menu)){
            throw new RuntimeException("编辑的菜单为空");
        }
        menuDao.update(menu);
        // 修改菜单权限信息 先删再加
        if (ArrayUtil.isEmpty(permissionIds)){
            // 为空 删除菜单所拥有的权限
            menuDao.deleteMenuPermissions(menu.getId());
            return;
        }
        // 不为空 修改菜单所拥有权限
        menuDao.addMenuPermissions(menu.getId(), permissionIds);
        menuDao.deleteMenuPermissions(menu.getId());

    }

    @Override
    public void deleteMenu(Integer id) {

        // 1. 删除菜单需要查询菜单是否被角色关联, 被关联无法删除
        Integer count = menuDao.findRoleAssociateCount(id);
        if (count > 0){
            // 被关联 无法删除
            throw new RuntimeException("有角色关联此菜单, 无法删除");
        }
        // 2. 查询是否有子菜单
        List<Menu> menus = menuDao.selectByParentMenuId(id);
        if (CollUtil.isNotEmpty(menus)){
            // 3. 先删除子菜单
            menus.forEach(menu -> {
                menuDao.deleteMenu(menu.getId());
            });
        }
        // 4. 再删除菜单
        menuDao.deleteMenu(id);
    }
}
