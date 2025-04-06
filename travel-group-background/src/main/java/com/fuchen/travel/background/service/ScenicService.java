package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.Scenic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 景点-service层
 */
public interface ScenicService {

    /**
     * 获取推荐景点信息
     * @return 推荐景点的list集合
     */
    List<Scenic> findRecommendScenic ();

    /**
     * 获取景点总数
     * @return 景点总数
     */
    Integer getScenicCount ();

    /**
     * 分页查询全部景点
     * @param offset 检索起始行
     * @param limit 简述条数
     * @return 景点的list集合
     */
    List<Scenic> getScenic(Integer offset, Integer limit);

    /**
     * 推荐景点
     * @param recommendScenic 景点id
     */
    void recommend(String recommendScenic);

    /**
     * 移出推荐景点
     * @param recommendScenic 景点id
     */
    void removeRecommend(String recommendScenic);

    /**
     * 查询推荐的景点数量
     * @return
     */
    Integer getScenicRecommendCount();

    /**
     * 判断指定景点是否存在
     * @param scenicName
     * @return
     */
    Integer isScenicName(String scenicName);

    /**
     * 添加景点
     * @param scenic
     */
    void addScenic(Scenic scenic, MultipartFile scenicImg, String filename, String suffix);

    /**
     * 移出景点
     * @param list
     */
    void removeScenic(List<String> list);

    /**
     * 查询指定名称景点数量
     * @param keyword
     * @return
     */
    Integer getScenicCountSearch(String keyword);

    /**
     * 通过指定名称获取景点信息
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    List<Scenic> getScenicSearch(String keyword, Integer offset, Integer limit);

    List<Scenic> getScenicList();

    List<Scenic> getRecommendScenicList();
}
