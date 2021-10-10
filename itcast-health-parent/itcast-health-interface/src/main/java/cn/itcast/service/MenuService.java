package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.pojo.Menu;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 23:32
 * @Description:
 */
public interface MenuService {

    /**
     * 分页查询菜单
     * @param currentPage 当前页
     * @param pageSize 单页最大条数
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 查询出所有的菜单
     * @return
     */
    List<Menu> findAll();

    /**
     * 添加菜单
     * @param menu 菜单对象
     * @param permissionIds 权限id数组
     */
    void addMenu(Menu menu, Integer[] permissionIds);

    /**
     * 根据id查询出菜单
     * @param id 菜单id
     * @return
     */
    Menu findById(Integer id);

    /**
     * 根据菜单id查询菜单的权限信息
     * @param id
     * @return
     */
    List<Integer> findPermissionsById(Integer id);

    /**
     * 编辑菜单
     * @param menu 菜单对象
     * @param permissionIds 权限id数组
     */
    void updateMenu(Menu menu, Integer[] permissionIds);

    /**
     * 删除菜单
     * @param id 菜单id
     */
    void deleteMenu(Integer id);
}
