package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Address;
import cn.itcast.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/address")
@CrossOrigin
public class AddressController {

    @Reference
    private AddressService addressService;

    /**
     * 保存分院信息
     * @param address
     * @return
     */
    @PostMapping(path = "/submit")
    public Result add(@RequestBody Address address) {
        try {
            addressService.add(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ADDRESS_FAIL);
        }
    }

/*    *//**
     * 分页查询分院信息
     *
     * @param queryPageBean
     * @return
     *//*
    @GetMapping(path = "/findPage")
    public Result findPage(QueryPageBean queryPageBean) {
        //分页查询检查组信息
        try {
            PageResult pageResult = addressService.QueryByPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_ADDRESS_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ADDRESS_FAIL);
        }
    }*/

    /**
     * 根据id查询分院详情
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Address address = addressService.findById(id);
            return new Result(true, MessageConstant.QUERY_ADDRESS_SUCCESS, address);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ADDRESS_FAIL);
        }
    }

    /**
     * 更新分院信息
     *
     * @param address
     * @return
     */
    @PutMapping(path = "/edit")
    public Result edit(@RequestBody Address address) {
        try {
            addressService.edit(address);
            return new Result(true, MessageConstant.EDIT_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_ADDRESS_FAIL);
        }
    }

    /**
     * 删除分院信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        try {
            addressService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ADDRESS_FAIL);
        }
    }

    /**
     * 查询所有分院信息
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    public Result findAll() {
        try {
            List<Address> addressList = addressService.findAll();
            return new Result(true, MessageConstant.QUERY_ADDRESS_SUCCESS, addressList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ADDRESS_FAIL);
        }
    }

    /**
     * 查询所有分院信息
     *
     * @return
     */
    @GetMapping(path = "/editByEnable/{id}/{enable}")
    public Result editByEnable(@PathVariable("id") Integer id ,@PathVariable("enable") Integer enable) {
        try {
            Boolean falg = addressService.editByEnable(id,enable);
            return new Result(true, MessageConstant.ADD_START_SUCCESS,falg);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_START_FAIL);
        }
    }
}
