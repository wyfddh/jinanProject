package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.service.EsaleStatisticsService;
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
 * 统计图相关controller
 * @author 杜昶
 */
@RestController
@RequestMapping("/esaleStatistics")
public class EsaleStatisticsController extends BaseController {

    @Autowired
    private EsaleStatisticsService esaleStatisticsService;

    /**
     * 藏品统计
     * @return
     */
    @RequestMapping("/colletStatistics")
    public JsonResult colletStatistics() {
        try {
            return esaleStatisticsService.colletStatistics();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 藏品浏览统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/collectViewStatistics")
    public JsonResult collectViewStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return esaleStatisticsService.collectViewStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 藏品收藏统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/colectionCollectStatistics")
    public JsonResult colectionCollectStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return esaleStatisticsService.colectionCollectStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 用户统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/userStatistics")
    public JsonResult userStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return esaleStatisticsService.userStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 社教活动汇总数据
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/socialEducationSummaryStatistics")
    public JsonResult socialEducationSummaryStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return esaleStatisticsService.socialEducationSummaryStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 活动分类统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/socialEducationTypeStatistics")
    public JsonResult socialEducationTypeStatistics(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return esaleStatisticsService.socialEducationTypeStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 用户统计导出
     * @param startTime
     * @param endTime
     * @throws IOException
     */
    @RequestMapping("/exportLiteratureReport1")
    public void exportLiteratureReport1(String startTime, String endTime)throws IOException {
        String zipName = "用户统计.zip";

        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);

        //折线图数据
        String[] lineRow = new String[]{"操作日期","PC浏览次数(PV)","独立访客(UV)","注册用户数"};
        JsonResult videoCjLine = esaleStatisticsService.userStatistics(condition);
        List<Object[]> linedata = new ArrayList<Object[]>();
        Map<String,Object> map = (Map<String,Object>) videoCjLine.getData();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("data");
        List<String> xAxis = (List<String>) map.get("xAxis");
        for (int i = 0; i < xAxis.size(); i++) {
            Object[] temp = new String[4];
            temp[0] = xAxis.get(i);
            int k = 1;
            for (int j = 0; j < mapList.size(); j++) {
                temp[k++] = Long.toString(((List<Long>) mapList.get(j).get("data")).get(i));
            }
            linedata.add(temp);
        }

        String execelName3 = "用户统计折线图统计";
        ExportExcelUtil exportExcelUtil3 = new ExportExcelUtil(execelName3,lineRow,linedata);
        HSSFWorkbook sheets3 = exportExcelUtil3.exportMulti(response);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try
        {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(zipName, "UTF-8"));
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

    /**
     * 公开藏品分类统计导出
     * @throws IOException
     */
    @RequestMapping("/exportLiteratureReport2")
    public void exportLiteratureReport2()throws IOException{
        String zipName = "公开藏品分类统计.zip";
        JsonResult videoCjTable = esaleStatisticsService.colletStatistics();
        //交叉表数据
        Map<String, Object> table = (Map<String, Object>) ((Map<String, Object>) videoCjTable.getData()).get("table");
        Map<String, Object> tableHead = (Map<String, Object>) table.get("head");
        List<Map<String, Object>> talbeColumn = (List<Map<String, Object>>) table.get("column");
        String[] tableRow = new String[tableHead.size() + 1];
        Object[] temp1 = new String[tableHead.size() + 1];
        Object[] temp2 = new String[tableHead.size() + 1];
        int index = 0;
        tableRow[index] = "类型";
        temp1[index] = "数量";
        temp2[index] = "占比";
        index ++;
        List<Object[]> tableData = new ArrayList<Object[]>();
        Map<String, Object> count = talbeColumn.get(0);
        Map<String, Object> percen = talbeColumn.get(1);
        List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)((Map<String, Object>) videoCjTable.getData()).get("column");
        for (Map.Entry<String, Object> entry : tableHead.entrySet()) {
            tableRow[index] = (String) tableHead.get(entry.getKey());
            temp1[index] = Integer.toString((Integer) count.get(entry.getKey()));
            temp2[index] = percen.get(entry.getKey());
            index ++;
        }
        tableData.add(temp1);
        tableData.add(temp2);

        String[] pieRow = new String[]{"类型","数量"};
        List<Object[]> pieData = new ArrayList<Object[]>();
        List<Map<String, Object>> pie = (List<Map<String, Object>>) ((Map<String, Object>) videoCjTable.getData()).get("pie");
        for (Map<String, Object> line : pie){
            Object[] temp = new String[2];
            temp[0] = line.get("name");
            temp[1] = Long.toString((Long) line.get("value"));
            pieData.add(temp);
        }

        String execelName1 = "公开藏品分类交叉表统计";
        String execelName2 = "公开藏品分类饼图统计";
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

    @RequestMapping("/exportLiteratureReport3")
    public void exportLiteratureReport3(String startTime, String endTime)throws IOException{
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);

        String zipName = "藏品浏览统计.zip";
        JsonResult videoCjTable = esaleStatisticsService.collectViewStatistics(condition);

        Map<String, Object> data = (Map<String, Object>) videoCjTable.getData();
        List<Object[]> tableData = new ArrayList<Object[]>();
        List<Map<String, Object>> histogram = (List<Map<String, Object>>) data.get("histogram");
        String[] tableRow = new String[]{"藏品名称", "数量"};
        for (Map<String, Object> line : histogram) {
            Object[] temp = new String[2];
            temp[0] = line.get("name");
            temp[1] = Long.toString((Long) line.get("value"));
            tableData.add(temp);
        }


        String[] pieRow = new String[]{"类型","数量"};
        List<Object[]> pieData = new ArrayList<Object[]>();
        List<Map<String, Object>> pie = (List<Map<String, Object>>) data.get("pie");
        for (Map<String, Object> line : pie){
            Object[] temp = new String[2];
            temp[0] = line.get("name");
            temp[1] = Long.toString((Long) line.get("value"));
            pieData.add(temp);
        }

        String execelName1 = "藏品浏览TOP10统计";
        String execelName2 = "藏品浏览饼图统计";
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

    @RequestMapping("/exportLiteratureReport4")
    public void exportLiteratureReport4(String startTime, String endTime)throws IOException{
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);

        String zipName = "藏品收藏统计.zip";
        JsonResult videoCjTable = esaleStatisticsService.colectionCollectStatistics(condition);

        Map<String, Object> data = (Map<String, Object>) videoCjTable.getData();
        List<Object[]> tableData = new ArrayList<Object[]>();
        List<Map<String, Object>> histogram = (List<Map<String, Object>>) data.get("histogram");
        String[] tableRow = new String[]{"藏品名称", "数量"};
        for (Map<String, Object> line : histogram) {
            Object[] temp = new String[2];
            temp[0] = line.get("name");
            temp[1] = String.valueOf(line.get("value"));
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

        String execelName1 = "藏品收藏TOP10统计";
        String execelName2 = "藏品收藏饼图统计";
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

    @RequestMapping("/exportLiteratureReport5")
    public void exportLiteratureReport5(String startTime, String endTime)throws IOException{
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);

        String zipName = "活动分类统计.zip";
        JsonResult videoCjTable = esaleStatisticsService.socialEducationTypeStatistics(condition);

        Map<String, Object> data = (Map<String, Object>) videoCjTable.getData();
        List<Object[]> tableData = new ArrayList<Object[]>();
        Map<String, Object> table = (Map<String, Object>) data.get("table");
        List<Map<String, Object>> tableHead = (List<Map<String, Object>>) table.get("head");
        tableHead.remove(0);
        List<Map<String, Object>> tableColumn = (List<Map<String, Object>>) table.get("column");
        Map<String, Object> times = tableColumn.get(0);
        Map<String, Object> people = tableColumn.get(1);
        Object[] temp1 = new String[tableHead.size() + 1];
        Object[] temp2 = new String[tableHead.size() + 1];
        String[] tableRow = new String[tableHead.size() + 1];
        tableRow[0] = "类别";
        temp1[0] = "活动次数";
        temp2[0] = "报名人数";
        for (int i = 0; i < tableHead.size(); i++) {
            Map<String, Object> head = tableHead.get(i);
            tableRow[i + 1] = (String) head.get("title");
            String key = (String) head.get("field");
            temp1[i + 1] = String.valueOf(times.get(key));
            temp2[i + 1] = String.valueOf(people.get(key));
        }
        tableData.add(temp1);
        tableData.add(temp2);

        String[] pieRow = new String[]{"类型","数量"};
        List<Object[]> pieData = new ArrayList<Object[]>();
        List<Map<String, Object>> pie = (List<Map<String, Object>>) data.get("pie");
        for (Map<String, Object> line : pie){
            Object[] temp = new String[2];
            temp[0] = line.get("name");
            temp[1] = Long.toString((Long) line.get("value"));
            pieData.add(temp);
        }

        //折线图数据
        String[] lineRow = new String[]{"操作日期","PC端报名","移动端报名"};
        List<Object[]> linedata = new ArrayList<Object[]>();
        Map<String, Object> line = (Map<String, Object>) data.get("line");
        List<String> xAxis = (List<String>) line.get("xAxis");
        List<Map<String, Object>> lineData = (List<Map<String, Object>>) line.get("data");
        List<Long> web = (List<Long>) lineData.get(0).get("data");
        List<Long> app = (List<Long>) lineData.get(1).get("data");
        for (int i = 0; i < xAxis.size(); i++) {
            Object[] temp = new String[3];
            temp[0] = xAxis.get(i);
            temp[1] = Long.toString(web.get(i));
            temp[2] = Long.toString(app.get(i));
            linedata.add(temp);
        }

        String execelName1 = "活动分类交叉表统计";
        String execelName2 = "活动分类饼图统计";
        String execelName3 = "活动分类折线图统计";
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

    /**
     * 日报统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/diaryStatistics")
    public JsonResult diaryStatistics(String startTime, String endTime) {
        try {
            return esaleStatisticsService.diaryStatistics(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 根据日期统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/diaryStatisticsGroupByDate")
    public JsonResult diaryStatisticsGroupByDate(String startTime, String endTime) {
        try {
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            return esaleStatisticsService.diaryStatisticsGroupByDate(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 根据用户统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/diaryStatisticsGroupByPerson")
    public JsonResult diaryStatisticsGroupByPerson(String startTime, String endTime) {
        try {
            return esaleStatisticsService.diaryStatisticsGroupByPerson(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    /**
     * 根据部门统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/diaryStatisticsGroupByDept")
    public JsonResult diaryStatisticsGroupByDept(String startTime, String endTime) {
        try {
            return esaleStatisticsService.diaryStatisticsGroupByDept(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }

    @RequestMapping("/personLoopStatistics")
    public JsonResult personLoopStatistics(String startTime, String endTime) {
        try {
            return esaleStatisticsService.personLoopStatistics(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }
}
