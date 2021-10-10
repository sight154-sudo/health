package cn.itcast.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.itcast.dao.ReportDao;
import cn.itcast.service.ReportService;
import cn.itcast.vo.ReportData;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 17:59 2021/8/20
 * @description:
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public Map<String, List> findmemberCountStatistics() {
//        return reportDao.findMemberStatistics();
        //查询最近12个月的数据
//        String s = DateUtil.offset(new Date(), DateField.MONTH, -11).toDateStr();
        List<Map<String, Object>> list = reportDao.findMemberStatistics();
        Map<String, List> map = new HashMap<>();
        map.put("months", CollUtil.getFieldValues(list, "months"));
        map.put("memberCount", CollUtil.getFieldValues(list, "memberCount"));
        return map;
    }

    @Override
    public Map<String, Object> findSeamealStatistics() {
        List<Map<String, Object>> list = this.reportDao.findSetmealStatistics();
        //setmealCount setmealNames
        Map<String, Object> map = new HashMap<>();
        map.put("setmealNames", CollUtil.getFieldValues(list, "name"));
        map.put("setmealCount", list);
        return map;
    }

    /**
     * 统计最近12个月收入
     *
     * @return
     */
    @Override
    public Map<String, List> findIncomeStatistics() {
//        String s = DateUtil.offset(new Date(), DateField.MONTH, -11).toDateStr();
        List<Map<String, Object>> list = reportDao.findIncomeStatistics();
        Map<String, List> map = new HashMap<>();
        map.put("months", CollUtil.getFieldValues(list, "months"));
        map.put("moneySum", CollUtil.getFieldValues(list, "moneySum"));
        return map;
    }

    /**
     * 统计预约订单与支付订单
     *
     * @return
     */
    @Override
    public Map<String, List> findOrderStatistics() {
        List<Map<String, Object>> list = reportDao.findOrderCountStastics();
        Map<String, List> map = new HashMap<>();
        map.put("months", CollUtil.getFieldValues(list, "months"));
        map.put("orderCount", CollUtil.getFieldValues(list, "orderCount"));
        map.put("payCount", CollUtil.getFieldValues(list, "payCount"));
        return map;
    }

    @Override
    public ReportData findReportStatistics() {
        ReportData reportData = this.reportDao.findCountStastics();
        reportData.setReportDate(new Date());
        reportData.setHotSetmeal(this.reportDao.findHotSetmealStastics());
        return reportData;
    }

    /**
     * 导出excel文件数据
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            ReportData reportData = this.reportDao.findCountStastics();
            reportData.setReportDate(new Date());
            reportData.setHotSetmeal(this.reportDao.findHotSetmealStastics());
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/template.xlsx");
            //2.定义导出文件名称
            String fileName = "运营数据统计";

            //3.设置响应对象
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xlsx");
            //4.输出流和写入流
            ServletOutputStream out = response.getOutputStream();

            excelWriter = EasyExcel.write(out).withTemplate(in).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            //填充数据
            Map<String,Object> map = new HashMap<>();
            map.put("reportDate",reportData.getReportDate());
            map.put("todayNewMember",reportData.getTodayNewMember());
            map.put("totalMember",reportData.getTotalMember());
            map.put("thisWeekNewMember",reportData.getThisWeekNewMember());
            map.put("thisMonthNewMember",reportData.getThisMonthNewMember());
            map.put("todayOrderNumber",reportData.getTodayOrderNumber());
            map.put("todayVisitsNumber",reportData.getTodayVisitsNumber());
            map.put("thisWeekOrderNumber",reportData.getThisWeekOrderNumber());
            map.put("thisWeekVisitsNumber",reportData.getThisWeekVisitsNumber());
            map.put("thisMonthOrderNumber",reportData.getThisMonthOrderNumber());
            map.put("thisMonthVisitsNumber",reportData.getThisMonthVisitsNumber());
            excelWriter.fill(map,writeSheet);
            List<Map<String,Object>> list = reportData.getHotSetmeal();
            excelWriter.fill(list,writeSheet);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            // 别忘记关闭流
            excelWriter.finish();
        }
    }
}
