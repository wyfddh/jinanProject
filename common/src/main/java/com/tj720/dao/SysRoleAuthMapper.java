package com.tj720.dao;

import com.tj720.model.common.wf.SysRoleAuth;
import com.tj720.model.common.wf.SysRoleAuthExample;
import com.tj720.model.common.wf.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int countByExample(SysRoleAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int deleteByExample(SysRoleAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int insert(SysRoleAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int insertSelective(SysRoleAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    List<SysRoleAuth> selectByExample(SysRoleAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    SysRoleAuth selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysRoleAuth record, @Param("example") SysRoleAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int updateByExample(@Param("record") SysRoleAuth record, @Param("example") SysRoleAuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int updateByPrimaryKeySelective(SysRoleAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_auth
     *
     * @mbggenerated Tue Oct 09 17:21:16 CST 2018
     */
    int updateByPrimaryKey(SysRoleAuth record);

    /**
     * 统计关联对象数
     * @param roleId 角色id
     * @param partyType 关联类型
     * @return
     */
    Integer countRoleAuth(@Param("roleId") String roleId, @Param("partyType") String partyType);

    /**
     * 查询关联对象
     * @param roleId 角色id
     * @param partyType 关联类型
     * @return
     */
    List<SysRoleAuth> getRoleAuthByCondition(@Param("roleId") String roleId, @Param("partyType") String partyType);

    /**
     * 根据id查询单个用户
     * @param userId 用户ID
     * @param isDelete 用户状态
     * @return
     */
    SysUser getUserByUserId(@Param("userId") String userId, @Param("isDelete") String isDelete);
    /**
     * 根据名称查询用户列表(模糊匹配)
     * @param userName 用户名
     * @param isDelete 用户状态
     * @return
     */
    List<SysUser> getUserListByName(@Param("userName") String userName, @Param("isDelete") String isDelete);


    List<SysRoleAuth> getAllRoleAuthInfo();


    List<SysUser> getUserListByRoleCode(@Param("roleCode") String roleCode);
}