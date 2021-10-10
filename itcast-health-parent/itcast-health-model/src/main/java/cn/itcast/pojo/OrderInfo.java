package cn.itcast.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfo {
    private String name;
    private String sex;
    private String phoneNumber;
    private String validateCode;//验证码
    private String idCard;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date orderDate;//体检日期
    private Integer addressId;
    private Integer setmealId;
}
