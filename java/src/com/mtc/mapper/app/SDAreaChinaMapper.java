package com.mtc.mapper.app;

import com.mtc.entity.app.SDAreaChina;
import java.util.List;

public interface SDAreaChinaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_area_china
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_area_china
     *
     * @mbggenerated
     */
    int insert(SDAreaChina record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_area_china
     *
     * @mbggenerated
     */
    SDAreaChina selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_area_china
     *
     * @mbggenerated
     */
    List<SDAreaChina> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_area_china
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SDAreaChina record);
}