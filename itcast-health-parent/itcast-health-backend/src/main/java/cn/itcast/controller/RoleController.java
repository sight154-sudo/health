package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckItem;
import cn.itcast.pojo.Role;
import cn.itcast.service.RoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author BlueStar
 * @date 2021/8/20 19:42
 * @Description:
 */
@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {
    @Reference
    private RoleService roleService;

    @GetMapping("/all")
    public Result findAll() {
        try {
            List<Role> items = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, items);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    /**
     * 分页查询角色数据
     *
     * @param queryPageBean 分页参数封装对
     * @return
     */
    @GetMapping(path = "/page")
    public Result findPage(QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = roleService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    /**
     * 添加角色
     * @param permissionIds 权限id数组
     * @param menuIds 菜单id数组
     * @param role 角色对象
     * @return
     */
    @PostMapping
    public Result addRole(@RequestParam("permissionIds")Integer[] permissionIds
            , @RequestParam("menuIds")Integer[] menuIds,
                          @RequestBody Role role) {
        try {
            roleService.addRole(role, permissionIds, menuIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    /**
     * 通过id查询角色
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/{id}")
    public Result findRoleById(@PathVariable("id") Integer roleId) {
        try {
            Role role = roleService.findRoleById(roleId);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    /**
     * 查找角色菜单
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/{id}/menu")
    public Result findRoleMenusById(@PathVariable("id") Integer roleId) {
        try {
            List<Integer> menuIds = roleService.findRoleMenusById(roleId);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    /**
     * 查找角色权限
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/{id}/permission")
    public Result findRolePermissionsById(@PathVariable("id") Integer roleId) {
        try {
            List<Integer> permissionIds = roleService.findRolePermissionsById(roleId);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    /**
     * 编辑角色
     * @param permissionIds 权限id数组
     * @param menuIds 菜单id数组
     * @param role 角色对象
     * @return
     */
    @PutMapping
    public Result updateRole(@RequestParam("permissionIds") Integer[] permissionIds, @RequestParam("menuIds")Integer[] menuIds, @RequestBody Role role) {
        try {
            roleService.updateRole(role, permissionIds, menuIds);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result updateRole(@PathVariable("id") Integer roleId) {
        try {
            roleService.deleteRole(roleId);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }
}
