package cn.itcast.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Menu;
import cn.itcast.service.MenuService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author BlueStar
 * @date 2021/8/20 23:27
 * @Description:
 */
@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {
    @Reference
    private MenuService menuService;

    /**
     * 分页查询菜单数据
     *
     * @param params
     * @return
     */
    @GetMapping(path = "/page")
    public Result findPage(@RequestParam Map<String, Object> params) {
        try {
            String currentPage = (String) params.get("currentPage");
            String pageSize = (String) params.get("pageSize");
            Object queryString = params.get("queryString");
            PageResult pageResult;
            if (ObjectUtil.isNotEmpty(queryString)) {
                pageResult = menuService.pageQuery(Integer.parseInt(currentPage), Integer.parseInt(pageSize), (String) queryString);
            } else {
                pageResult = menuService.pageQuery(Integer.parseInt(currentPage), Integer.parseInt(pageSize), null);
            }
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    /**
     * 查询出所有的菜单
     * @return
     */
    @GetMapping
    public Result findAll() {
        try {
            List<Menu> menuList = menuService.findAll();
            if (CollUtil.isNotEmpty(menuList)){
                return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);

    }
    /**
     * 查询单个菜单
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Menu menu = menuService.findById(id);
            if (ObjectUtil.isNotEmpty(menu)){
                return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);

    }

    /**
     * 查询菜单管理权限信息
     * @param id 菜单id
     * @return
     */
    @GetMapping(path = "/{id}/permissions")
    public Result findPermissionsById(@PathVariable("id") Integer id) {
        try {
            List<Integer> permissionIds = menuService.findPermissionsById(id);
            if (CollUtil.isNotEmpty(permissionIds)){
                return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissionIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);

    }


    /**
     * 添加菜单
     * @param permissionIds 权限id数组
     * @param menu 菜单对象
     * @return
     */
    @PostMapping
    public Result addMenu(@RequestParam Integer[] permissionIds, @RequestBody Menu menu) {
        try {
            menuService.addMenu(menu, permissionIds);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_MENU_FAIL);

    }

    /**
     * 编辑菜单
     * @param permissionIds 权限id数组
     * @param menu 菜单对象
     * @return
     */
    @PutMapping
    public Result updateMenu(@RequestParam Integer[] permissionIds, @RequestBody Menu menu) {
        try {
            menuService.updateMenu(menu, permissionIds);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_MENU_FAIL);

    }

    /**
     * 删除菜单
     * @param id 菜单id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public Result deleteMenu(@PathVariable("id") Integer id){
        try {
            menuService.deleteMenu(id);
            return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_MENU_FAIL);

    }
}
