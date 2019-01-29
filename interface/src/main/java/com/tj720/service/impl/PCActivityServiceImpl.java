package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PCActivityMapper;
import com.tj720.dto.PCEsaleActivityDto;
import com.tj720.service.PCActivityService;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PCActivityServiceImpl implements PCActivityService {
    @Autowired
    private PCActivityMapper activityMapper;
    @Autowired
    private Config config;
    @Override
    public JsonResult getActivityListByUid(String userId,String key,Integer currentPage, Integer size) {
        try {
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("uid",userId);
            //符合条件总数
            Integer count = activityMapper.countActivity(map);
            page.setAllRow(count);
            System.out.println("count : "+count);
            if (count==0) {
                List<PCEsaleActivityDto> alllist = new ArrayList<>();

                return new JsonResult(1,alllist, page);
            }

            map.put("start",page.getStart());
            map.put("end",page.getSize());


            //查询分页数据
            List<PCEsaleActivityDto> alllist = activityMapper.getActivityListByUid(map);
            for(PCEsaleActivityDto info:alllist){
                if(info.getPictureUrl() != null){
                    info.setPictureUrl(config.getRootUrl()+info.getPictureUrl());
                }
            }
            System.out.println("key ========= "+key);

            /*if(key!=null&&key.equals("")){*/
                /**
                 * 全部活动
                 */
                if(key.equals("0")){

                    isFlog(alllist);

                    System.out.println("page.getAllRow();   "+page.getAllRow());
                    System.out.println("page.getTotalPage();   "+ page.getTotalPage());

                    return new JsonResult(1,alllist,page);
                }

                //未开始
                if(key.equals("1")){
                    System.out.println("key.equals()    ====   "+key.equals("1"));
                    List<PCEsaleActivityDto> list = new ArrayList<>();
                    for (PCEsaleActivityDto a:alllist) {
                        String s = a.getActivityTime();
                        long timeInMillis = DateFormartUtil.getCurrentTimeMillis(s);
                        int i = differentDaysByMillisecond(new Date(), timeInMillis);;
                        if (i<0){
                            list.add(a);
                        }
                    }

                    return new JsonResult(1,list,page);
                }
                //已经结束
                if(key.equals("3")){
                    List<PCEsaleActivityDto> list = new ArrayList<>();
                    for (PCEsaleActivityDto a:alllist
                    ) {
                        String s = a.getActivityTime();
                        long timeInMillis = DateFormartUtil.getCurrentTimeMillis(s);
                        int i = differentDaysByMillisecond(new Date(), timeInMillis);
                        System.out.println("i =========" +i);
                        if (i>0){
                            list.add(a);
                        }
                    }

                    return new JsonResult(1,list,page);
                }
                //正在进行
                if (key.equals("2")){
                    List<PCEsaleActivityDto> list = new ArrayList<>();
                    for (PCEsaleActivityDto a:alllist
                    ) {
                        String s = a.getActivityTime();
                        long timeInMillis = DateFormartUtil.getCurrentTimeMillis(s);
                        int i = differentDaysByMillisecond(new Date(), timeInMillis);
                        if (i==0){
                            list.add(a);
                        }
                    }

                    return new JsonResult(1,list,page);
                }
        /*    }*/

            isFlog(alllist);

            return new JsonResult(1,alllist,page);

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }
    }




    /**
     * 判断两个时间相差的天数
     * @param date1 当天时间
     * @param date2  活动报名时间
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,long date2)
    {
        int days = (int) ((date1.getTime() - date2 ) / (1000*3600*24));
        return days;
    }

    public void isFlog(List<PCEsaleActivityDto> alllist){
        for (PCEsaleActivityDto a:alllist) {
            String s = a.getActivityTime();
            long timeInMillis = DateFormartUtil.getCurrentTimeMillis(s);
            int i = differentDaysByMillisecond(new Date(), timeInMillis);
            if (i==0){
                a.setActivityStatus("2");
            }

            if (i>0){
                a.setActivityStatus("3");
            }
            if (i<0){
                a.setActivityStatus("1");
            }
        }
    }
}
