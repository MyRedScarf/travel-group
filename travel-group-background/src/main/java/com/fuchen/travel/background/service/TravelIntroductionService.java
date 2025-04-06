package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.TravelIntroduction;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TravelIntroductionService {

    /**
     * 保存拼旅攻略信息
     * @param title 主题
     * @param parameter 内容参数
     * @param introductionImg 图片文件
     * @return 保存条数
     */
    Integer saveIntroduction(String title, String parameter, MultipartFile introductionImg);

    /**
     * 获取攻略列表
     * @param offset 分页起始行
     * @param limit 分页显示条数
     * @return 攻略集合
     */
    List<TravelIntroduction> getIntroductionList(Integer offset, Integer limit);

    /**
     * 获取攻略数量
     * @return 数量
     */
    Integer getIntroductionCount();

    /**
     * 获取攻略详情
     * @param id 攻略id
     * @return 攻略实体
     */
    TravelIntroduction getIntroductionDetails(Integer id);

    /**
     * 删除攻略
     * @param id 攻略id
     * @return 删除条数
     */
    Integer deleteById(@Param("id") Integer id);
}
