package com.mtc.mapper.app;

import com.mtc.entity.app.SDTtsTemplate;
import java.util.List;

public interface SDTtsTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_tts_template
     *
     * @mbggenerated Mon Jul 04 11:20:00 CST 2016
     */
    int deleteByPrimaryKey(String tempId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_tts_template
     *
     * @mbggenerated Mon Jul 04 11:20:00 CST 2016
     */
    int insert(SDTtsTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_tts_template
     *
     * @mbggenerated Mon Jul 04 11:20:00 CST 2016
     */
    SDTtsTemplate selectByPrimaryKey(String tempId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_tts_template
     *
     * @mbggenerated Mon Jul 04 11:20:00 CST 2016
     */
    List<SDTtsTemplate> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_d_tts_template
     *
     * @mbggenerated Mon Jul 04 11:20:00 CST 2016
     */
    int updateByPrimaryKey(SDTtsTemplate record);
}