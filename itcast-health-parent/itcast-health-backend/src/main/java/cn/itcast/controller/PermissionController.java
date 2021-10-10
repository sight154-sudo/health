package cn.itcast.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Permission;
import cn.itcast.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author BlueStar
 * @date 2021/8/21 10:22
 * @Description:
 */
@RestController
@RequestMapping("permission")
@CrossOrigin
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    /**
     * 查找
     * @return
     */
    @GetMapping("all")
    public Result findAllPermission(){
        try {
            List<Permission> permissionList = permissionService.findAll();
            if (CollUtil.isNotEmpty(permissionList)){
                return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissionList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }

    /**
     * 查询菜单关联的权限集合
     * @param menuIds 菜单id数组
     * @return
     */
    @GetMapping(path = "/menu/association")
    public Result findMenuAssociationPermission(@RequestParam("menuIds") Integer[] menuIds){
        try {
            // 查找菜单关联的权限
            List<Permission> permissionList = permissionService.findMenuAssociationPermission(menuIds);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }

    /**
     * 分页查询权限列表
     * @param params 参数map
     * @return
     */
    @GetMapping
    public Result findByPage(@RequestParam Map<String, String> params){
        try {
            String currentPage = params.get("currentPage");
            String pageSize = params.get("pageSize");
            String queryString = params.get("queryString");
            // 查找菜单关联的权限
            PageResult pageResult;
            if (ObjectUtil.isNotEmpty(queryString)) {
                pageResult = permissionService.findByPage(Integer.parseInt(currentPage), Integer.parseInt(pageSize), queryString);
            }else {
                pageResult = permissionService.findByPage(Integer.parseInt(currentPage), Integer.parseInt(pageSize), null);
            }
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }

    /**
     * 查询单个权限详情
     * @param id 权限id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") Integer id){
        try {
            Permission permission = permissionService.findById(id);
            if (ObjectUtil.isNotEmpty(permission)){
                return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }

    /**
     * 添加权限
     * @param permission 权限对象
     * @return
     */
    @PostMapping
    public Result addPermission(@RequestBody Permission permission){
        try {
            permissionService.addPermission(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
    }

    /**
     * 编辑权限
     * @param permission 权限对象
     * @return
     */
    @PutMapping
    public Result editPermission(@RequestBody Permission permission){
        try {
            permissionService.updatePermission(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
    }

    /**
     * 删除权限
     * @param id 权限id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deletePermission(@PathVariable("id") Integer id){
        try {
            permissionService.deletePermission(id);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
    }

}
