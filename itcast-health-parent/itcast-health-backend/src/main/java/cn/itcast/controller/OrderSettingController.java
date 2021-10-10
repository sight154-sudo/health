package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import cn.itcast.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author hanjunjie
 * @date 2021/8/20 17:54
 * 预约设置模块
 */
@RestController
@RequestMapping("ordersetting")
@CrossOrigin
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 设置预约的参数
     * orderSetting 设置参数的对象
     */
    @PutMapping("updateOrderSettingNumber")
    public Result updateOrderSetting(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.updateOrderSetting(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @GetMapping("/getOrderSettingsByMonth")
    public Result getOrderSettingsByMonth(@RequestParam("date") String date){
        try {
            List<Map<String,Object>>  list= orderSettingService.getOrderSettingsByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @PostMapping("upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            List<String[]> readExcel = POIUtils.readExcel(excelFile);
            orderSettingService.upload(readExcel);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
