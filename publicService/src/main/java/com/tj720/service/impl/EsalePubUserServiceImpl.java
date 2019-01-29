package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.BlackListExcelTitles;
import com.tj720.controller.UserExcelTitles;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleAssessActivityMapper;
import com.tj720.dao.EsaleCollectionInfoMapper;
import com.tj720.dao.EsalePubUserMapper;
import com.tj720.dao.EsaleUserActivityMapper;
import com.tj720.model.EsalePubUser;
import com.tj720.model.EsalePubUserVo;
import com.tj720.model.EsaleUserActivity;
import com.tj720.service.EsalePubUserService;
import com.tj720.utils.*;
import com.tj720.utils.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EsalePubUserServiceImpl implements EsalePubUserService {
    @Autowired
    private EsalePubUserMapper esaleUser;

    @Autowired
    private EsaleUserActivityMapper esaleUserActivityMapper;

    @Autowired
    private EsaleCollectionInfoMapper esaleCollectionInfoMapper;

    @Autowired
    private EsaleAssessActivityMapper esaleAssessActivityMapper;


    /**
     * 加载公众号用户列表
     * @param key
     * @param phone
     * @param isYoung
     * @param orderBy
     * @param size
     * @param currentPage
     * @return
     */
    @Override
    public JSONObject getEsalePubUserList(String key, String phone, String isYoung, String orderBy, Integer size, Integer currentPage) {
       System.out.println("isYoung =============="+isYoung);
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.isBlank(Tools.getUserId())) {
                throw new Exception("登录异常");
            }

            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();
            if (StringUtils.isNotBlank(key)) {
                map.put("key",key);
            }
            if (StringUtils.isNotBlank(phone)) {
                //1：首页推荐
                map.put("phone",phone);
            }

            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy",orderBy);
            } else {
                //默认1
                map.put("orderBy",1);
            }
            if (StringUtils.isNotBlank(isYoung)) {
                map.put("isYoung",isYoung);
            }

            //符合条件总数
            Integer count = esaleUser.countEsaleUserList(map);
            System.out.println("count : "+count);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());

            //查询分页数据
            List<EsalePubUserVo> list = esaleUser.getEsalePubUserList(map);

            /**
             * 判断是否是青年用户
             */
            for (EsalePubUserVo vo:list) {
             if(vo.getIsYoung()!=null&&vo.getIsYoung().equals("1")){
                  vo.setIsYoung("是");
              }else {
                  vo.setIsYoung("否");
              }

                List<EsaleUserActivity> activitiesList = esaleUserActivityMapper.countActivityByUid(vo.getId());
                System.out.println("activitiesList ========="+activitiesList.size());
                /**
                 * 设置缺勤的次数
                 */
             Integer joinStateNum = 0;
             Integer joinNum = 0;
                for (EsaleUserActivity eua:activitiesList) {
                    if(eua.getJoinState()!=null&&eua.getJoinState().equals("0")){
                        joinStateNum++;
                    }

                    /**
                     * 设置活动报名的次数
                     */
                    if (eua.getDataState()!=null&&eua.getDataState().equals("1")){
                        joinNum++;
                    }

                }
                System.out.println("joinStateNum ========="+joinStateNum);
               vo.setNotJoinStateNum(joinStateNum);
                System.out.println("joinNum ========="+joinNum);

               vo.setActivityNum(joinNum);


                /**
                 * 设置收藏品的数量
                 */
            Integer c = esaleCollectionInfoMapper.countCollectionByUid(vo.getId());
                System.out.println("c   ========="+c);
            if (c!=null){
                vo.setCollectionNum(c);
            }else {
                vo.setCollectionNum(0);
            }


            }

            String jsonString = JSON.toJSONString(list);
            System.out.println("jsonString  :   "+jsonString);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    /**
     * 查询指定用户的个人信息
     */
    @Override
    public EsalePubUser queryUserById(String id) {
        EsalePubUser esalePubUser = esaleUser.selectByPrimaryKey(id);
        if (esalePubUser!=null&&esalePubUser.getBirthday()!=null){
            String dateToString = DateUtil.DateToString(esalePubUser.getBirthday(), "yyyy-MM-dd");
            esalePubUser.setMyBirthday(dateToString);
        }
        return esalePubUser;
    }

    /**
     * 统计信息
     * @param id 用户id
     * @return
     */
    @Override
    public JSONObject countInfomation(String id) {

        JSONObject jsonObject = new JSONObject();
        EsalePubUserVo vo = new EsalePubUserVo();
        try {
            if (StringUtils.isBlank(Tools.getUserId())) {
                throw new Exception("登录异常");
            }

            /**
             * 设置收藏品的数量
             */
            Integer c = esaleCollectionInfoMapper.countCollectionByUid(id);
            if (c!=null){
                vo.setCollectionNum(c);
            }else {
                vo.setCollectionNum(0);
            }


            List<EsaleUserActivity> list = esaleUserActivityMapper.countActivityByUid(id);
            Integer concenNum = 0;
            Integer dataStateNum = 0;
            Integer joinStateNum = 0;
            if (list!=null&&list.size()!=0) {
                for (EsaleUserActivity eua : list) {
                    /**
                     * 取消次数
                     */
                    if (eua.getDataState() != null && eua.getDataState().equals("0")) {
                        concenNum++;
                    } else {
                        vo.setConcenNum(0);
                    }
                    vo.setConcenNum(concenNum);

                    /**
                     * 报名次数
                     */
                    if (eua.getDataState() != null && eua.getDataState().equals("1")) {
                        dataStateNum++;
                    } else {
                        vo.setActivityNum(0);
                    }
                    vo.setActivityNum(dataStateNum);

                    /**
                     * 缺席次数
                     */
                    if (eua.getJoinState() != null && eua.getJoinState().equals("0")) {
                        joinStateNum++;
                    } else {
                        vo.setNotJoinStateNum(0);
                    }
                    vo.setNotJoinStateNum(joinStateNum);
                }
            }else {

                vo.setConcenNum(0);
                vo.setActivityNum(0);
                vo.setNotJoinStateNum(0);
            }
            /**
             * 活动评论
             */
            Integer cont = esaleAssessActivityMapper.countComment(id);
            vo.setActivityComment(cont);
            List<EsalePubUserVo> pubUserVoList = new ArrayList<>();
            if (vo!=null){
                pubUserVoList.add(vo);
            }
            String jsonString = JSON.toJSONString(pubUserVoList);
            System.out.println("jsonString  :   "+jsonString);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", 1);
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getActivityCountById(Integer size, Integer currentPage, String uid) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.isBlank(Tools.getUserId())) {
                throw new Exception("登录异常");
            }

            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();

            //符合条件总数
            Integer count = esaleUser.countEsaleUserList(map);
            System.out.println("count : "+count);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());

            //查询分页数据
            List<EsalePubUserVo> list = esaleUser.getEsalePubUserList(map);


            String jsonString = JSON.toJSONString(list);
            System.out.println("jsonString  :   "+jsonString);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    /**
     * 编辑用户个人信息
     * @param esalePubUser
     */
    @Override
    public JsonResult updateUser(EsalePubUser esalePubUser) {
        JsonResult jsonResult = null;
        try {
            if(esalePubUser.getMyBirthday()!=null&&!esalePubUser.getMyBirthday().equals("")){
                System.out.println("esalePubUser.getMyBirthday() ===  "+esalePubUser.getMyBirthday());
                boolean flag = flag(esalePubUser.getMyBirthday());
                if(flag){
                    //是青少年用户
                    esalePubUser.setIsYoung("1");
                }else {
                    esalePubUser.setIsYoung("0");
                }

                Date stringToDate = DateUtil.StringToDate(esalePubUser.getMyBirthday(), "yyyy-MM-dd");
                esalePubUser.setBirthday(stringToDate);
            }
            esaleUser.updateByPrimaryKeySelective(esalePubUser);
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("111116");
        }
        return  jsonResult;
    }

    /**
     * 冻结用户
     * @param uid
     * @return
     */
    @Override
    public JsonResult freezeUser(String uid) {
        JsonResult jsonResult = null;
        try {
            EsalePubUser esalePubUser = esaleUser.selectByPrimaryKey(uid);
            esalePubUser.setUpdateBy(Tools.getUserId());
            esalePubUser.setUpdateDate(new Date());
            esalePubUser.setDataState("2");
            esalePubUser.setFreezTime(new Date());
            esaleUser.updateByPrimaryKey(esalePubUser);

            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("111116");
        }
        return  jsonResult;

    }

    /**
     * 批量冻结
     * @param uids
     * @return
     */
    @Override
    public JsonResult freezeUsers(String[] uids) {
        JsonResult jsonResult = null;
        for (String id:uids
             ) {
            jsonResult = freezeUser(id);
        }
        return jsonResult;
    }


    /**
     *黑名单列表查询
     */
    @Override
    public JSONObject getEsaleUserBlacklist(String key, String phone, String isYoung, String orderBy, Integer size, Integer currentPage) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.isBlank(Tools.getUserId())) {
                throw new Exception("登录异常");
            }

            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();
            if (StringUtils.isNotBlank(key)) {
                map.put("key",key);
            }
            if (StringUtils.isNotBlank(phone)) {
                //1：首页推荐
                map.put("phone",phone);
            }

            if (StringUtils.isNotBlank(orderBy)) {
                map.put("orderBy",orderBy);
            } else {
                //默认1
                map.put("orderBy",1);
            }
            if (StringUtils.isNotBlank(isYoung)) {
                map.put("isYoung",isYoung);
            }
            //符合条件总数
            List<Integer> count = esaleUser.countEsaleUserBlacklist(map);
            if (count !=null&&count.size()!=0){
                page.setAllRow(count.size());
            }
            System.out.println("count : "+count);

            map.put("start",page.getStart());
            map.put("end",page.getSize());

            //查询分页数据
            List<EsalePubUserVo> list = esaleUser.getEsaleUserBlacklist(map);

            /**
             * 判断是否是青年用户
             */
            for (EsalePubUserVo vo:list) {
                if(vo.getIsYoung()!=null&&vo.getIsYoung().equals("1")){
                    vo.setIsYoung("是");
                }else {
                    vo.setIsYoung("否");
                }
                List<EsaleUserActivity> activitiesList = esaleUserActivityMapper.countActivityByUid(vo.getId());
                /**
                 * 设置缺勤的次数
                 */
                Integer joinStateNum = 0;
                Integer joinNum = 0;
                for (EsaleUserActivity eua:activitiesList) {
                    if(eua.getJoinState()!=null&&eua.getJoinState().equals("0")){
                        joinStateNum++;
                    }
                    /**
                     * 设置活动报名的次数
                     */
                    if (eua.getDataState()!=null&&eua.getDataState().equals("1")){
                        joinNum++;
                    }
                }
                vo.setNotJoinStateNum(joinStateNum);

                vo.setActivityNum(joinNum);
            }

            String jsonString = JSON.toJSONString(list);
            System.out.println("jsonString  :   "+jsonString);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    /**
     * 移出黑名单
     * @param id 用戶id
     * @return
     */
    @Override
    public JsonResult moveOutUser(String id) {
        JsonResult jsonResult = null;
        try {
            EsalePubUser esalePubUser = esaleUser.selectByPrimaryKey(id);
            esalePubUser.setUpdateBy(Tools.getUserId());
            esalePubUser.setUpdateDate(new Date());
            esalePubUser.setDataState("1");
            System.out.println("esalePubUser     ======    "  +esalePubUser);
            esaleUser.updateByPrimaryKey(esalePubUser);

            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("111116");
        }
        return  jsonResult;
    }
    /**
     * 批量移出黑名單
     * @param uids
     * @return
     */
    @Override
    public JsonResult moveOutUsers(String[] uids) {
        JsonResult jsonResult = null;
        for (String i:uids) {
            jsonResult = moveOutUser(i);
        }
        return jsonResult;
    }


    @Override
    public List<EsalePubUser> getListByName(String userName) {

        if(StringUtils.isBlank(userName)) {
            return new ArrayList<>();
        }else {
            List<EsalePubUser> user = esaleUser.selectUserByName(userName);
            return user;
        }
    }

    @Override
    public void login(EsalePubUser model, EsalePubUser user, HttpServletRequest request, HttpServletResponse response) {
        String token = Aes.encrypt(user.getId());
        MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
        MyCookie.addCookie(Const.COOKIE_USERID, user.getId(), response);
        model.setId(user.getId());
        if (!MyString.isEmpty(model.getPhone())) {
            MyCookie.addCookie(Const.COOKIE_PHONE, model.getPhone(), response);
        }
        MyCookie.addCookie("sessionAdminName", user.getUserName()==null?"":user.getUserName(), response);

        MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
        model.setSessionAdminName(user.getRealName());
        model.setAvatarUrl(user.getAvatarUrl());
    }

    @Override
    public Workbook exportUserData(EsalePubUserVo vo) {
        /**
         * 导出数据
         */
        XSSFWorkbook wb = (XSSFWorkbook) generateUserTemplate();

        List<EsalePubUserVo> esalePubUserVoList = new ArrayList<>();
        if (null != vo.getIds()) {
            String[] idList = vo.getIds();
            for (String id : idList
            ) {
                EsalePubUserVo esalePubUserVo= getDateUser(id);
                //EsalePubUser esalePubUser = this.esaleUser.selectByPrimaryKey(vo.getId());
                System.out.println("esalePubUserVo====== "+esalePubUserVo);
                esalePubUserVoList.add(esalePubUserVo);
            }
        }
        Sheet sheet = wb.getSheetAt(0);
        Row row;// 创建行对象
        Cell cell;// 创建格子对象

        EsalePubUserVo esalePubUserVo = new EsalePubUserVo();
        // 设置样式
        CellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        font.setFontName("宋体");// 字体
        font.setFontHeight(25);
        cellStyle.setFont(font);// 使用字体
        cellStyle.setWrapText(true); // 换行显示
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
        row = sheet.getRow(0);
        cell = row.createCell(0);
        cell.setCellValue("公众用户数据列表");
        cell.setCellStyle(cellStyle);
        // 遍历集合
        for (int i = 0; i < esalePubUserVoList.size(); i++) {
            // 从第三行开始
            row = sheet.createRow(i + 2);
            esalePubUserVo = esalePubUserVoList.get(i);
            for (int j = 0; j < UserExcelTitles.values().length; j++) {
                // 创建单元格
                cell = row.createCell(j);
                if (j == 0) {
                    // 设置值
                    cell.setCellValue(esalePubUserVo.getId());
                } else if (j == 1) {
                    cell.setCellValue(esalePubUserVo.getUserName());
                } else if (j == 2) {
                    cell.setCellValue(esalePubUserVo.getPhone());
                } else if (j == 3) {
                    cell.setCellValue(esalePubUserVo.getCollectionNum());
                }else if (j == 4) {
                    cell.setCellValue(esalePubUserVo.getActivityNum());
                }else if (j == 5) {
                    cell.setCellValue(esalePubUserVo.getNotJoinStateNum());
                }else if (j == 6) {
                    cell.setCellValue(esalePubUserVo.getIsYoung());
                }
            }
        }
        return wb;
    }

    /**
     * 公众用户下载模板
     * @return
     */
    @Override
    public Workbook generateUserTemplate() {
        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = wb.createSheet("公众用户数据列表");
        // sheet.setDefaultColumnWidth(18);
        Row row;// 创建行对象
        Cell cell;// 创建格子对象
        // 设置样式
        CellStyle cellStyle = wb.createCellStyle();
        CellStyle cellStyle2 = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        font.setFontName("宋体");// 字体
        font.setFontHeight(25);
        cellStyle.setFont(font);// 使用字体
        cellStyle.setWrapText(true); // 换行显示
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式

        XSSFFont font2 = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        font.setFontName("宋体");// 字体
        font.setFontHeight(14);
        cellStyle2.setFont(font2);// 使用字体
        cellStyle2.setWrapText(true); // 换行显示
        cellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
        cellStyle2.setLocked(true);// 只读

        // 创建第一行
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("用户模板导入");
        cell.setCellStyle(cellStyle);
        row.setHeight((short) 600);

        // 创建第二行
        row = sheet.createRow(1);

        UserExcelTitles[] userExcelTitles = UserExcelTitles.values();
        List<String> title = new ArrayList<>();
        for (UserExcelTitles m : userExcelTitles) {
            title.add(m.getTitle());
        }
        // 写入标题列
        for (int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(cellStyle2);
        }
        int total = row.getLastCellNum();
        // 这个就是合并单元格
        // 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
        // 比如我要合并 第二行到第四行的 第六列到第八列 sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, total - 1));
        return wb;
    }



    public  EsalePubUserVo getDateUser(String id){
        EsalePubUserVo vo = esaleUser.getEsalePubUsers(id);
        /**
         * 判断是否是青年用户
         */
            if(vo.getIsYoung()!=null&&vo.getIsYoung().equals("1")){
                vo.setIsYoung("是");
            }else {
                vo.setIsYoung("否");
            }
            List<EsaleUserActivity> activitiesList = esaleUserActivityMapper.countActivityByUid(vo.getId());
            System.out.println("activitiesList ========="+activitiesList.size());
            /**
             * 设置缺勤的次数
             */
            Integer joinStateNum = 0;
            Integer joinNum = 0;
            for (EsaleUserActivity eua:activitiesList) {
                if(eua.getJoinState()!=null&&eua.getJoinState().equals("0")){
                    joinStateNum++;
                }

                /**
                 * 设置活动报名的次数
                 */
                if (eua.getDataState()!=null&&eua.getDataState().equals("1")){
                    joinNum++;
                }

            }
            vo.setNotJoinStateNum(joinStateNum);
            vo.setActivityNum(joinNum);
            /**
             * 设置收藏品的数量
             */
            Integer c = esaleCollectionInfoMapper.countCollectionByUid(vo.getId());
            if(c!=null){
                vo.setCollectionNum(c);
            }else {
                vo.setCollectionNum(0);
            }

        return vo;
    }

    /**
     * 黑名单数据导出
     * @param vo
     * @return
     */
    @Override
    public Workbook exportBlacklistData(EsalePubUserVo vo) {
        /**
         * 导出数据
         */
        XSSFWorkbook wb = (XSSFWorkbook) generateBlicklistTemplate();

        List<EsalePubUserVo> esalePubUserVoList = new ArrayList<>();
        if (null != vo.getIds()) {
            String[] idList = vo.getIds();
            for (String id : idList
            ) {
                EsalePubUserVo esalePubUserVo= getDateBlacklist(id);
                //EsalePubUser esalePubUser = this.esaleUser.selectByPrimaryKey(vo.getId());
                esalePubUserVoList.add(esalePubUserVo);
            }
        }
        Sheet sheet = wb.getSheetAt(0);
        Row row;// 创建行对象
        Cell cell;// 创建格子对象

        EsalePubUserVo esalePubUserVo = new EsalePubUserVo();
        // 设置样式
        CellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        font.setFontName("宋体");// 字体
        font.setFontHeight(25);
        cellStyle.setFont(font);// 使用字体
        cellStyle.setWrapText(true); // 换行显示
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
        row = sheet.getRow(0);
        cell = row.createCell(0);
        cell.setCellValue("黑名单数据导出模板");
        cell.setCellStyle(cellStyle);
        // 遍历集合
        for (int i = 0; i < esalePubUserVoList.size(); i++) {
            // 从第三行开始
            row = sheet.createRow(i + 2);
            esalePubUserVo = esalePubUserVoList.get(i);
            for (int j = 0; j < BlackListExcelTitles.values().length; j++) {
                // 创建单元格
                cell = row.createCell(j);
                if (j == 0) {
                    // 设置值
                    cell.setCellValue(esalePubUserVo.getId());
                } else if (j == 1) {
                    cell.setCellValue(esalePubUserVo.getUserName());
                } else if (j == 2) {
                    cell.setCellValue(esalePubUserVo.getPhone());
                }else if (j == 3) {
                    cell.setCellValue(esalePubUserVo.getActivityNum());
                }else if (j == 4) {
                    cell.setCellValue(esalePubUserVo.getNotJoinStateNum());
                }else if (j == 5) {
                    if (esalePubUserVo.getFreezTime()!=null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = formatter.format(esalePubUserVo.getFreezTime());
                        cell.setCellValue(date);
                    }
                }
            }
        }
        return wb;
    }

    private EsalePubUserVo getDateBlacklist(String id) {
        EsalePubUserVo vo = esaleUser.getEsaleUserBlacks(id);
        if(vo.getIsYoung()!=null&&vo.getIsYoung().equals("1")){
            vo.setIsYoung("是");
        }else {
            vo.setIsYoung("否");
        }
        List<EsaleUserActivity> activitiesList = esaleUserActivityMapper.countActivityByUid(vo.getId());
        /**
         * 设置缺勤的次数
         */
        Integer joinStateNum = 0;
        Integer joinNum = 0;
        for (EsaleUserActivity eua:activitiesList) {
            if(eua.getJoinState()!=null&&eua.getJoinState().equals("0")){
                joinStateNum++;
            }
            /**
             * 设置活动报名的次数
             */
            if (eua.getDataState()!=null&&eua.getDataState().equals("1")){
                joinNum++;
            }
        }
        vo.setNotJoinStateNum(joinStateNum);

        vo.setActivityNum(joinNum);
        return vo;


    }

    /**
     * 黑名单导出下载模板
     * @return
     */
    @Override
    public Workbook generateBlicklistTemplate() {
        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = wb.createSheet("movie");
        // sheet.setDefaultColumnWidth(18);
        Row row;// 创建行对象
        Cell cell;// 创建格子对象
        // 设置样式
        CellStyle cellStyle = wb.createCellStyle();
        CellStyle cellStyle2 = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        font.setFontName("宋体");// 字体
        font.setFontHeight(25);
        cellStyle.setFont(font);// 使用字体
        cellStyle.setWrapText(true); // 换行显示
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式

        XSSFFont font2 = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        font.setFontName("宋体");// 字体
        font.setFontHeight(14);
        cellStyle2.setFont(font2);// 使用字体
        cellStyle2.setWrapText(true); // 换行显示
        cellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中格式
        cellStyle2.setLocked(true);// 只读

        // 创建第一行
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("用户模板导入");
        cell.setCellStyle(cellStyle);
        row.setHeight((short) 600);

        // 创建第二行
        row = sheet.createRow(1);

        BlackListExcelTitles[] userExcelTitles = BlackListExcelTitles.values();
        List<String> title = new ArrayList<>();
        for (BlackListExcelTitles m : userExcelTitles) {
            title.add(m.getTitle());
        }
        // 写入标题列
        for (int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(cellStyle2);
        }
        int total = row.getLastCellNum();
        // 这个就是合并单元格
        // 参数说明：1：开始行 2：结束行 3：开始列 4：结束列
        // 比如我要合并 第二行到第四行的 第六列到第八列 sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, total - 1));
        return wb;
    }

    /**
     * 判断是否为青少年
     */
    public static boolean flag(String bir){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        Date birthday = null;
        try {
            birthday = sdf.parse(bir);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date nowDate = new Date();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(nowDate);
        c2.setTime(birthday);
        int age =  c1.get(Calendar.YEAR)-  c2.get(Calendar.YEAR);
        System.out.println(age);
        if(age<=14){
            return true;
        }else{
            return false;
        }
    }

}
