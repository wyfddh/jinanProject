package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.EsaleSysMenuMapper;
import com.tj720.model.EsaleSysMenu;
import com.tj720.model.EsaleSysMenuVo;
import com.tj720.model.EsaleUserMenu;
import com.tj720.service.EsaleSysMenuService;
import com.tj720.utils.Tools;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 系统菜单service impl
 * @author 杜昶
 */
@Service
public class EsaleSysMenuServiceImpl implements EsaleSysMenuService {

    @Autowired
    private EsaleSysMenuMapper esaleSysMenuMapper;

    @Autowired
    private Config config;


    @Override
    public List<EsaleSysMenu> getMenuByUser(String pageType) {
        if (!"1".equals(pageType) && !"2".equals(pageType)) {
            return null;
        }
        Map<String, Object> param = new HashMap<String, Object>(2);
        param.put("pageType", pageType);
        param.put("userId", Tools.getUserId());
        List<EsaleSysMenu> menuList = esaleSysMenuMapper.getMenuByUser(param);
        List<EsaleSysMenu> menus = getChildPerms(menuList, "-1");
        if (config.getAdminId().equals(Tools.getUserId())) {
            EsaleSysMenu esaleSysMenu = new EsaleSysMenu();
            esaleSysMenu.setId("-2");
            esaleSysMenu.setParentId("-1");
            esaleSysMenu.setTitle("菜单管理");
            esaleSysMenu.setIcon("icon-text");
            esaleSysMenu.setHref("page/menu/menuList.html");
            esaleSysMenu.setType(2);
            esaleSysMenu.setSort(10);
            menus.add(esaleSysMenu);
        }
        return menus;
    }

    @Override
    public JsonResult getAllMenuByUser() {
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("userId", Tools.getUserId());
        List<EsaleSysMenu> menuList = esaleSysMenuMapper.getAllMenuByUser(param);
        return new JsonResult(1, menuList);
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<EsaleSysMenu> getChildPerms(List<EsaleSysMenu> list, String parentId)
    {
        List<EsaleSysMenu> returnList = new ArrayList<EsaleSysMenu>();
        for (Iterator<EsaleSysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            EsaleSysMenu t = (EsaleSysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId))
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<EsaleSysMenu> list, EsaleSysMenu t)
    {
        // 得到子节点列表
        List<EsaleSysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (EsaleSysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<EsaleSysMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    EsaleSysMenu n = (EsaleSysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<EsaleSysMenu> getChildList(List<EsaleSysMenu> list, EsaleSysMenu t)
    {
        List<EsaleSysMenu> tlist = new ArrayList<EsaleSysMenu>();
        Iterator<EsaleSysMenu> it = list.iterator();
        while (it.hasNext())
        {
            EsaleSysMenu n = (EsaleSysMenu) it.next();
            if (n.getParentId().equals(t.getId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<EsaleSysMenu> list, EsaleSysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    @Override
    public Map<String, Object> getMenuList() {
        List<EsaleSysMenu> sysMenuList = esaleSysMenuMapper.getMenuList();
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("code", 0);
        map.put("data", sysMenuList);
        return map;
    }

    @Override
    public Map<String, Object> getMenuList(String functionName, String type) {
        List<EsaleSysMenu> sysMenuList = esaleSysMenuMapper.getMenuListPlus(functionName,type);
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("code", 0);
        map.put("data", sysMenuList);
        return map;
    }

    private List<EsaleUserMenu> getMenuByPid(String roleId, String parentId, String pageType) {
        if (!"1".equals(pageType) && !"2".equals(pageType)) {
            return null;
        }
        Map<String, Object> param = new HashMap<String, Object>(3);
        param.put("roleId", roleId);
        param.put("parentId", parentId);
        param.put("pageType", pageType);
        List<EsaleUserMenu> sysMenuList = esaleSysMenuMapper.getMenuPermissionList(param);
        Map<String, Object> map;
        for (EsaleUserMenu userMenu : sysMenuList) {
            userMenu.setChildren(getMenuByPid(roleId, userMenu.getKey(), pageType));
        }
        return CollectionUtils.isEmpty(sysMenuList) ? null: sysMenuList;
    }

    @Override
    public JsonResult getMenuById(String id) {
        return new JsonResult(1, esaleSysMenuMapper.getMenuById(id));
    }

    @Override
    public JsonResult addMenu(EsaleSysMenu esaleSysMenu) {
        Integer count = esaleSysMenuMapper.insertSysMenu(esaleSysMenu);
        return new JsonResult(1);
    }

    @Override
    public JsonResult updateMenu(EsaleSysMenu esaleSysMenu) {
        esaleSysMenu.setUpdater(Tools.getUserId());
        esaleSysMenuMapper.updateSysMenu(esaleSysMenu);
        return new JsonResult(1);
    }

    @Override
    public JsonResult queryResListTreeByRole(String roleId,String parentId,String pageType) {
        List<EsaleUserMenu> sysMenuList = this.getMenuByPid(roleId, parentId, pageType);
        return new JsonResult(1, sysMenuList);
    }

    @Override
    public JsonResult batchUpdateResAuth(List<String> menuIds, String roleId, String pageType) {
        Map<String, Object> param = new HashMap<String, Object>(2);
        param.put("pageType", pageType);
        param.put("roleId", roleId);
        esaleSysMenuMapper.deleteMenuRole(param);
        param.put("menuIds", menuIds);
        if (CollectionUtils.isNotEmpty(menuIds)) {
            esaleSysMenuMapper.insertMenuRole(param);
        }
        return new JsonResult(1);
    }

    @Override
    public JsonResult deleteFunctionById(String functionId) {
        if (StringUtils.isEmpty(functionId)){
            return new JsonResult(0,null,"200208");
        }
        try {
            int count = esaleSysMenuMapper.deleteByPrimaryKey(functionId);
            if (count>0){
                return new JsonResult(1,null);
            }else {
                return new JsonResult(0,null,"200206");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,null,"200206");
        }
    }
}
