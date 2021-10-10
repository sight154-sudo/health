package cn.itcast.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.service.ReportService;
import cn.itcast.vo.ReportData;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 16:55 2021/8/20
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("report")
public class ReportController {

    @Reference
    private ReportService reportService;

    /**
     * 会员数量统计
     *
     * @return
     */
    @GetMapping("member/count")
    public ResponseEntity<Result> memberCountStatistics() {

        Map<String, List> map = null;
        try {
            map = this.reportService.findmemberCountStatistics();
            if (MapUtil.isNotEmpty(map)) {
                return ResponseEntity.ok(new Result(true, MessageConstant.QUERY_MEMBER_SUCCESS, map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false, MessageConstant.QUERY_MEMBER_FAIL));
    }


    /**
     * 预约套餐统计
     *
     * @return
     */
    @GetMapping("setmeals")
    public ResponseEntity<Result> seamealStatistics() {

        Map<String, Object> map = null;
        try {
            map = this.reportService.findSeamealStatistics();
            if (MapUtil.isNotEmpty(map)) {
                return ResponseEntity.ok(new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL));
    }


    /**
     * 收入统计
     *
     * @return
     */
    @GetMapping("income")
    public ResponseEntity<Result> incomeStatistics() {

        Map<String, List> map = null;
        try {
            map = this.reportService.findIncomeStatistics();
            if (MapUtil.isNotEmpty(map)) {
                return ResponseEntity.ok(new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL));
    }


    /**
     * 收入统计
     *
     * @return
     */
    @GetMapping("order")
    public ResponseEntity<Result> orderStatistics() {

        Map<String, List> map = null;
        try {
            map = this.reportService.findOrderStatistics();
            if (MapUtil.isNotEmpty(map)) {
                return ResponseEntity.ok(new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL));
    }


    /**
     * 运营统计
     *
     * @return
     */
    @GetMapping("business")
    public ResponseEntity<Result> findReportStatistics() {

        try {
            ReportData data = this.reportService.findReportStatistics();
            if (ObjectUtil.isNotEmpty(data)) {
                return ResponseEntity.ok(new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL));
    }

    @GetMapping("export/business")
    public ResponseEntity<Void> exportData(HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            ReportData reportData = this.reportService.findReportStatistics();

            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/template.xlsx");
            //2.定义导出文件名称
            String fileName = "运营数据统计";

            //3.设置响应对象
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xlsx");
            //4.输出流和写入流
            ServletOutputStream out = response.getOutputStream();
            excelWriter = EasyExcel.write(out,ReportData.class).withTemplate(in).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            //填充数据
            excelWriter.fill(reportData, writeSheet);
            excelWriter.fill(reportData.getHotSetmeal(), writeSheet);
        } catch (Exception e) {
            log.error("运营数据导出失败", e);
        } finally {
            // 别忘记关闭流
            excelWriter.finish();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
