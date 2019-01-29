package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.wf.SysRoleAuth;
import com.tj720.service.RoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/9 17:37
 */

@RestController
@RequestMapping("/RoleAuth")
public class RoleAuthController {
    @Autowired
    RoleAuthService roleAuthService;

    /**
     * 新增角色关联
     * @param sysRoleAuth 角色关联对象
     * @return
     */
    @RequestMapping("/addRoleAuth")
    public JsonResult addRoleAuth(SysRoleAuth sysRoleAuth){
        JsonResult result = roleAuthService.addRoleAuth(sysRoleAuth);
        return result;
    }

    /**
     * 修改角色关联
     * @param sysRoleAuth 角色关联对象
     * @return
     */
    @RequestMapping("/updateRoleAuth")
    public JsonResult updateRoleAuth(SysRoleAuth sysRoleAuth){
        JsonResult result = roleAuthService.updateRoleAuth(sysRoleAuth);
        return result;
    }

    /**
     * 删除角色关联
     * @param authId
     * @return
     */
    @RequestMapping("/deleteRoleAuth")
    public JsonResult deleteRoleAuth(@RequestParam String authId){
        JsonResult result = roleAuthService.deleteRoleAuthById(authId);
        return result;
    }

    /**
     * 查询角色关联的对象
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping("/queryRoleAuthList")
    public JsonResult queryRoleAuthList(String roleId){
        List<SysRoleAuth> roleAuthListByCondition = roleAuthService.getRoleAuthListByCondition(roleId, null);
        return new JsonResult(1,roleAuthListByCondition);
    }

    /**
     * 添加角色关联
     * @return
     */
    @RequestMapping("/batchAddRoleAuth")
    public JsonResult batchAddRoleAuth(@RequestBody HashMap<String,Object> data){
        String roleId = data.get("roleId").toString();
        List<HashMap<String,String>> party = (List<HashMap<String,String>>)data.get("party");
        JsonResult jsonResult = roleAuthService.batchAddRoleAuth(roleId, party);
        return jsonResult;
    }

    /**
     * 修改角色关联
     * @param data
     * @return
     */
    @RequestMapping("/batchUpdateRoleAuth")
    public JsonResult batchUpdateRoleAuth(@RequestBody HashMap<String,Object> data){
        String roleId = data.get("roleId").toString();
        List<HashMap<String,String>> party = (List<HashMap<String,String>>)data.get("party");
        JsonResult jsonResult = roleAuthService.batchUpdateRoleAuth(roleId, party);
        return jsonResult;
    }
    @RequestMapping("/getUserByUserId")
    public JsonResult getUserByUserId(@RequestParam String userId){
        JsonResult userByUserId = roleAuthService.getUserByUserId(userId);
        return userByUserId;
    }

    @RequestMapping("/getUserListByRoleCode")
    public JsonResult getUserListByRoleCode(@RequestParam String roleCode){
        JsonResult users = roleAuthService.getUserListByRoleCode(roleCode);
        return users;
    }

    @RequestMapping("/getAllUserList")
    public JsonResult getAllUserList(){
        JsonResult users = roleAuthService.getUserList();
        return users;
    }

    @RequestMapping("/getAllOrgList")
    public JsonResult getAllOrgList(){
        JsonResult users = roleAuthService.getOrgList();
        return users;
    }

    @RequestMapping("getUserById")
    public JsonResult getUserById(@RequestParam String id){
        JsonResult jsonResult = roleAuthService.getUserById(id);
        return jsonResult;
    }

}
