package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.Scenic;
import com.fuchen.travel.background.mapper.ScenicMapper;
import com.fuchen.travel.background.service.ScenicService;
import com.fuchen.travel.background.util.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 * 景点-service层-实现类
 */
@Service
public class ScenicServiceImpl implements ScenicService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ScenicMapper scenicMapper;

    /**
     * 腾讯云存储地区
     */
    @Value("${qcloud.cosRegion}")
    private String cosRegion;

    /**
     * 腾讯云密钥id
     */
    @Value("${qcloud.key.secretId}")
    private String secretId;

    /**
     * 腾讯云密钥key
     */
    @Value("${qcloud.key.secretKey}")
    private String secretKey;

    /**
     * 腾讯云对象存储桶名
     */
    @Value("${qcloud.bucket.scenic.name}")
    private String bucketName;

    /**
     * 腾讯云对象存储访问路径
     */
    @Value("${qcloud.bucket.scenic.url}")
    private String qCloudUrl;

    /**
     * 腾讯云工具类对象
     */
    @Autowired
    private QCloudUtil qCloudUtil;;

    /**
     * 获取推荐景点信息
     * @return
     */
    @Override
    public List<Scenic> findRecommendScenic() {

        List<Scenic> list = scenicMapper.selectRecommendScenic();
        //将推荐的景点数量放入redis中
        redisTemplate.opsForValue().set("scenicRecommendCount", list.size());

        return list;
    }

    /**
     * 获取景点总数
     * @return
     */
    @Override
    public Integer getScenicCount() {
        //从redis中取出景点总数
        Integer scenicCount = (Integer) redisTemplate.opsForValue().get("scenicCount");
        //如果景点数量不为空，则返回景点数量,否则重新进入数据库查询
        if (scenicCount != null) {
            return scenicCount;
        }
        //获取景点数量
        scenicCount = scenicMapper.selectScenicCount();
        //将景点数量放入redis中
        redisTemplate.opsForValue().set("scenicCount", scenicCount);
        return scenicCount;
    }

    /**
     * 分页查询全部景点
     * @param offset 检索起始行
     * @param limit 简述条数
     * @return
     */
    @Override
    public List<Scenic> getScenic(Integer offset, Integer limit) {
        return scenicMapper.selectScenic(offset, limit);
    }

    /**
     * 推荐景点
     * @param recommendScenic 推荐景点
     */
    @Override
    public void recommend(String recommendScenic) {
        //将字符串转换为Integer
        Integer scenicId = Integer.parseInt(recommendScenic);
        //推荐景点
        scenicMapper.recommend(scenicId);
        //清除redis中的推荐景点数量
        redisTemplate.delete("scenicRecommendCount");
    }

    /**
     * 移出推荐景点
     * @param recommendScenic 景点id
     */
    @Override
    public void removeRecommend(String recommendScenic) {
        //将字符串转换为Integer
        Integer scenicId = Integer.parseInt(recommendScenic);
        //移出推荐景点
        scenicMapper.removeRecommend(scenicId);
        //清除redis中的推荐景点数量
        redisTemplate.delete("scenicRecommendCount");
    }

    /**
     * 查询推荐的景点数量
     * @return
     */
    @Override
    public Integer getScenicRecommendCount() {
        //从redis中取出推荐的景点数量
        Integer scenicRecommendCount = (Integer) redisTemplate.opsForValue().get("scenicRecommendCount");
        //如果数量不为空，则直接返回景点数量,否则进入数据库重新查询
        if (scenicRecommendCount != null) {
            return scenicRecommendCount;
        }
        scenicRecommendCount = scenicMapper.selectScenicRecommendCount();
        redisTemplate.opsForValue().set("scenicRecommendCount", scenicRecommendCount);
        return scenicRecommendCount;
    }

    /**
     * 判断指定景点是否存在
     * @param scenicName 景点名称
     * @return
     */
    @Override
    public Integer isScenicName(String scenicName) {
        //查询当前景点是否存在
        Scenic scenic = scenicMapper.selectScenicExist(scenicName);
        //存在说明scenic不为空，则将scenic的id返回，否则返回空
        if(scenic != null) {
            return scenic.getId();
        }
        return null;
    }

    /**
     * 添加景点
     * @param scenic 景点对象
     * @param scenicImg 景点图片
     * @param filename 景点图片名称
     * @param suffix 后缀
     */
    @Override
    public void addScenic(Scenic scenic, MultipartFile scenicImg,  String filename, String suffix) {

        //上传腾讯云
        qCloudUtil.uploadFile(bucketName, filename, scenicImg , cosRegion, secretId, secretKey);

        //更新景点图片路径
        String scenicUrl = qCloudUrl + "/" +  filename ;
        scenic.setImageUrl(scenicUrl);
        scenic.setCreateTime(new Date());

        //如果景点id为空说明已经不存在该景点，应该添加当前景点，否则为修改景点
        if (scenic.getId() == null) {
            //添加景点信息
            scenicMapper.insertScenic(scenic);
        } else {
            //修改景点信息
            scenicMapper.updateScenic(scenic);
        }
        //清除redis中景点数量
        redisTemplate.delete("scenicCount");
    }

    /**
     * 移出景点
     * @param list
     */
    @Override
    public void removeScenic(List<String> list) {
        //修改景点状态
        List<String> removeList = new ArrayList<>();
        if (list.get(0).equals("on")) {
            list.remove(0);
        }
        scenicMapper.updateScenicById(list);
        //清除redis中景点数量
        redisTemplate.delete("scenicCount");
    }

    /**
     * 查询指定名称景点数量
     * @param keyword
     * @return
     */
    @Override
    public Integer getScenicCountSearch(String keyword) {
        return scenicMapper.selectCountByKeyword(keyword);
    }

    /**
     * 通过指定名称获取景点信息
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<Scenic> getScenicSearch(String keyword, Integer offset, Integer limit) {
        return scenicMapper.selectScenicByKeyword(keyword, offset, limit);
    }

    @Override
    public List<Scenic> getScenicList() {
        return scenicMapper.selectScenicList();
    }

    @Override
    public List<Scenic> getRecommendScenicList() {
        return scenicMapper.selectRecommendScenicList();
    }
}
