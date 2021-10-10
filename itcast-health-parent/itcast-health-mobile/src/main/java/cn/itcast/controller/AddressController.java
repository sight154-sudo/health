package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Address;
import cn.itcast.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.xml.bind.v2.TODO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @GetMapping(path = "/findAll")
    public Result findAllAddress() {
        try {
            List<Address> addressList = addressService.findAll();
            return new Result(true,MessageConstant.QUERY_ADDRESS_SUCCESS,addressList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ADDRESS_FAIL);
        }

    }
}
