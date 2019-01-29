package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.service.PostVideoStatisticsService;
import com.tj720.utils.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * @author 杜昶
 */
@RestController
@RequestMapping("/postVideoStatistics")
public class PostVideoStatisticsController extends BaseController {

    @Autowired
    private PostVideoStatisticsService postVideoStatisticsService;

    /**
     * 汇总数据
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/videoSummaryStatistics")
    public JsonResult videoSummaryStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return postVideoStatisticsService.videoSummaryStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 新增资料统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/newVideoStatistics")
    public JsonResult newVideoStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return postVideoStatisticsService.newVideoStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 数据使用统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/videoUseStatistics")
    public JsonResult videoUseStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return postVideoStatisticsService.videoUseStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    @RequestMapping("/exportLiteratureReport6")
    public void exportLiteratureReport6(String startTime, String endTime)throws IOException{
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);

        String zipName = "新增资料统计.zip";
        JsonResult videoCjTable = postVideoStatisticsService.newVideoStatistics(condition);

        Map<String, Object> data = (Map<String, Object>) videoCjTable.getData();
        List<Object[]> tableData = new ArrayList<Object[]>();
        List<Map<String, Object>> table = (List<Map<String, Object>>) data.get("table");
        String[] tableRow = new String[]{"类别", "新增", "累计", "未公开", "公开仅查询", "公开可下载"};
        for (Map<String, Object> line : table){
            Object[] temp = new String[6];
            temp[0] = line.get("title");
            temp[1] = String.valueOf(line.get("new"));
            temp[2] = String.valueOf(line.get("count"));
            temp[3] = String.valueOf(line.get("undisclosed"));
            temp[4] = String.valueOf(line.get("query"));
            temp[5] = String.valueOf(line.get("download"));
            tableData.add(temp);
        }

        String[] pieRow = new String[]{"类型","数量"};
        List<Object[]> pieData = new ArrayList<Object[]>();
        List<Map<String, Object>> pie = (List<Map<String, Object>>) data.get("pie");
        for (Map<String, Object> line : pie){
            Object[] temp = new String[2];
            temp[0] = line.get("name");
            temp[1] = String.valueOf(line.get("value"));
            pieData.add(temp);
        }

        //折线图数据
        String[] lineRow = new String[]{"操作日期","新增资料"};
        List<Object[]> linedata = new ArrayList<Object[]>();
        Map<String, Object> line = (Map<String, Object>) data.get("line");
        List<String> xAxis = (List<String>) line.get("xAxis");
        List<Map<String, Object>> lineData = (List<Map<String, Object>>) line.get("data");
        List<Long> web = (List<Long>) lineData.get(0).get("data");
        for (int i = 0; i < xAxis.size(); i++) {
            Object[] temp = new String[2];
            temp[0] = xAxis.get(i);
            temp[1] = String.valueOf(web.get(i));
            linedata.add(temp);
        }

        String execelName1 = "新增资料交叉表统计";
        String execelName2 = "新增资料饼图统计";
        String execelName3 = "新增资料折线图统计";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil(execelName1,tableRow,tableData);
        HSSFWorkbook sheets = exportExcelUtil.exportMulti(response);
        ExportExcelUtil exportExcelUtil2 = new ExportExcelUtil(execelName2,pieRow,pieData);
        HSSFWorkbook sheets2 = exportExcelUtil2.exportMulti(response);
        ExportExcelUtil exportExcelUtil3 = new ExportExcelUtil(execelName3,lineRow,linedata);
        HSSFWorkbook sheets3 = exportExcelUtil3.exportMulti(response);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try
        {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
            ExportExcelUtil.doCompress(sheets,execelName1+".xls",out);
            ExportExcelUtil.doCompress(sheets2,execelName2+".xls",out);
            ExportExcelUtil.doCompress(sheets3,execelName3+".xls",out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {out.close();} catch (IOException e) {}
            }
        }
    }

    @RequestMapping("/exportLiteratureReport7")
    public void exportLiteratureReport4(String startTime, String endTime)throws IOException{
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);

        String zipName = "数据使用统计.zip";
        JsonResult videoCjTable = postVideoStatisticsService.videoUseStatistics(condition);

        Map<String, Object> data = (Map<String, Object>) videoCjTable.getData();
        List<Object[]> tableData = new ArrayList<Object[]>();
        Map<String, Object> histogram = (Map<String, Object>) data.get("histogram");
        String[] tableRow = new String[]{"类型", "申请", "下载"};
        String[] xAxis = (String[]) histogram.get("xAxis");
        List<Map<String, Object>> histogramData = (List<Map<String, Object>>) histogram.get("data");
        List<Object> apply = (List<Object>) histogramData.get(0).get("data");
        List<Object> down = (List<Object>) histogramData.get(1).get("data");
        for (int i = 0; i < xAxis.length; i++) {
            Object[] temp = new String[3];
            temp[0] = xAxis[i];
            temp[1] = String.valueOf(apply.get(i));
            temp[2] = String.valueOf(down.get(i));
            tableData.add(temp);
        }


        List<Object[]> pieData = new ArrayList<Object[]>();
        Map<String, Object> histogram2 = (Map<String, Object>) data.get("histogram2");
        String[] pieRow = new String[]{"文献名称", "申请", "下载"};
        String[] yAxis = (String[]) histogram2.get("yAxis");
        List<Map<String, Object>> histogram2Data = (List<Map<String, Object>>) histogram2.get("data");
        List<Object> apply2 = (List<Object>) histogram2Data.get(0).get("data");
        List<Object> down2 = (List<Object>) histogram2Data.get(1).get("data");
        for (int i = 0; i < yAxis.length; i++) {
            Object[] temp = new String[3];
            temp[0] = xAxis[i];
            temp[1] = String.valueOf(apply2.get(i));
            temp[2] = String.valueOf(down2.get(i));
            pieData.add(temp);
        }

        String execelName1 = "数据使用柱形图统计";
        String execelName2 = "数据使用TOP10统计";
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil(execelName1,tableRow,tableData);
        HSSFWorkbook sheets = exportExcelUtil.exportMulti(response);
        ExportExcelUtil exportExcelUtil2 = new ExportExcelUtil(execelName2,pieRow,pieData);
        HSSFWorkbook sheets2 = exportExcelUtil2.exportMulti(response);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try
        {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
            ExportExcelUtil.doCompress(sheets,execelName1+".xls",out);
            ExportExcelUtil.doCompress(sheets2,execelName2+".xls",out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {out.close();} catch (IOException e) {}
            }
        }
    }
}
