package com.tj720.dao;

import java.util.List;
import java.util.Map;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.model.common.system.org.MipOrganization;
import com.tj720.model.common.system.org.MipOrganizationExample;
import org.apache.ibatis.annotations.Param;

public interface MipOrganizationMapper extends BaseDao<MipOrganization> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int countByExample(MipOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int deleteByExample(MipOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    @Override
    int insert(MipOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int insertSelective(MipOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    List<MipOrganization> selectByExample(MipOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    MipOrganization selectByPrimaryKey(Integer id);
    
    List<MipOrganization> selectAllOrganization(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int updateByExampleSelective(@Param("record") MipOrganization record, @Param("example") MipOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int updateByExample(@Param("record") MipOrganization record, @Param("example") MipOrganizationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    int updateByPrimaryKeySelective(MipOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mip_organization
     *
     * @mbggenerated Wed Aug 09 11:14:20 CST 2017
     */
    @Override
    int updateByPrimaryKey(MipOrganization record);

	List<MipOrganization> getListById(String orgId);

	List<MipOrganization> getListByArea(String area);
	//查 全部组织
	List<MipOrganization> getList();
	
	List<MipOrganization> getListByProvince(@Param("province") String province, @Param("city") String city, @Param("town") String town, @Param("orgTypeId") String orgTypeId, @Param("key") String key);
	//查 所有博物馆
	List<MipOrganization> getMuseumList();

	List<MipOrganization> getPageList(@Param("key") String key, @Param("orgTypeId") String orgTypeId, @Param("platformId") String platformId, @Param("startRow") int startRow, @Param("pageSize") Integer pageSize);

	int countPageList(@Param("key") String key, @Param("orgTypeId") String orgTypeId, @Param("platformId") String platformId);
	
	List<MipOrganization> getMuseumListByAreaId(String areaId);
	//18家直属馆id
	List<String> getOrgs();

    int countOrgList(Map<String, Object> map);

    List<MipOrganization> getOrgInfoList(Map<String, Object> map);
}