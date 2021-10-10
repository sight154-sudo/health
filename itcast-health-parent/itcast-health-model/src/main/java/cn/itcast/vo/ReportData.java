package cn.itcast.vo;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 12:45 2021/8/21
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportData implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat("yyyy年MM月dd日")
    private Date reportDate;
    private Integer todayNewMember;
    private Integer totalMember;
    private Integer thisWeekNewMember;
    private Integer thisMonthNewMember;
    private Integer todayOrderNumber;
    private Integer todayVisitsNumber;
    private Integer thisWeekOrderNumber;
    private Integer thisWeekVisitsNumber;
    private Integer thisMonthOrderNumber;
    private Integer thisMonthVisitsNumber;
    private List<Map<String,Object>> hotSetmeal;

}
