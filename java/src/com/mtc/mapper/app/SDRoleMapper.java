package com.mtc.mapper.app;

import com.mtc.entity.app.SDRole;
import java.util.List;

public interface SDRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_role
     *
     * @mbggenerated
     */
    int insert(SDRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_role
     *
     * @mbggenerated
     */
    SDRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_role
     *
     * @mbggenerated
     */
    List<SDRole> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SDRole record);
}