package cn.itcast.dao;

import cn.itcast.pojo.Menu;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author BlueStar
 * @date 2021/8/20 23:39
 * @Description:
 */
public interface MenuDao {
    /**
     * 查询子级菜单列表
     * @param parentMenuId 父级菜单id
     * @return
     */
    List<Menu> selectByParentMenuId(int parentMenuId);

    /**
     * 分页查询角色信息([查询参数 条件查询])
     * @param queryString 查询字符串
     */
    Page<Menu> selectByPage(String queryString);

    /**
     * 查询出所有菜单
     * @return
     */
    List<Menu> findAll();

    /**
     * 添加菜单
     * @param menu 菜单对象
     */
    void add(Menu menu);

    /**
     * 编辑菜单
     * @param menu 菜单对象
     */
    void update(Menu menu);

    /**
     * 根据菜单id查询菜单
     * @param id 菜单id
     * @return
     */
    Menu findById(Integer id);

    /**
     * 根据菜单id查询菜单的权限信息
     * @param id 菜单id
     * @return
     */
    List<Integer> findPermissionsById(Integer id);

    /**
     * 删除菜单所有权限
     * @param id 菜单id
     */
    void deleteMenuPermissions(Integer id);

    /**
     * 添加菜单的权限
     * @param permissionIds
     * @param menuId
     */
    void addMenuPermissions(@Param("menuId") Integer menuId, @Param("permissionIds") Integer[] permissionIds);

    /**
     * 查询菜单是否有角色关联
     * @return
     */
    Integer findRoleAssociateCount(@Param("menuId") Integer id);

    /**
     * 删除菜单
     * @param id 菜单id
     */
    void deleteMenu(Integer id);

    /**
     * 查询用户所具有的菜单列表
     * @param id 用户id
     * @return
     */
    List<Menu> findUserMenus(Integer id);
}
