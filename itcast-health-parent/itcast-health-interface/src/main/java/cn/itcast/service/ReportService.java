package cn.itcast.service;

import cn.itcast.vo.ReportData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 17:57 2021/8/20
 * @description:
 */
public interface ReportService {
    Map<String,List> findmemberCountStatistics();

    Map<String,Object> findSeamealStatistics();

    Map<String,List> findIncomeStatistics();

    Map<String,List> findOrderStatistics();

    ReportData findReportStatistics();

    void exportData(HttpServletResponse response);


}
