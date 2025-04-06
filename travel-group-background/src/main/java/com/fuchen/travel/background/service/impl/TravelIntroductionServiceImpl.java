package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.TravelIntroduction;
import com.fuchen.travel.background.mapper.TravelIntroductionMapper;
import com.fuchen.travel.background.service.TravelIntroductionService;
import com.fuchen.travel.background.util.QCloudUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TravelIntroductionServiceImpl implements TravelIntroductionService {

    @Resource
    private TravelIntroductionMapper travelIntroductionMapper;

    @Resource
    private QCloudUtil qCloudUtil;

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
    @Value("${qcloud.bucket.introduction.name}")
    private String bucketName;

    /**
     * 腾讯云对象存储访问路径
     */
    @Value("${qcloud.bucket.introduction.url}")
    private String qCloudUrl;

    @Override
    public Integer saveIntroduction(String title, String parameter, MultipartFile introductionImg) {
        //判断文件后缀
        String filename = introductionImg.getOriginalFilename();
        qCloudUtil.uploadFile(bucketName, filename, introductionImg, cosRegion, secretId, secretKey);
        //更新景点图片路径
        String introductionUrl = qCloudUrl + "/" +  filename ;
        TravelIntroduction travelIntroduction = new TravelIntroduction();
        travelIntroduction.setTitle(title);
        travelIntroduction.setFontImage(introductionUrl);
        travelIntroduction.setContent(parameter);
        travelIntroduction.setCreateTime(new Date());
        //如果景点id为空说明已经不存在该景点，应该添加当前景点，否则为修改景点
        return travelIntroductionMapper.insertIntroduction(travelIntroduction);
    }

    /**
     * 获取攻略列表
     * @param offset 分页起始行
     * @param limit 分页显示条数
     * @return 攻略集合
     */
    @Override
    public List<TravelIntroduction> getIntroductionList(Integer offset, Integer limit) {
        return travelIntroductionMapper.selectIntroductionList(offset, limit);
    }

    /**
     * 获取攻略数量
     * @return 数量
     */
    @Override
    public Integer getIntroductionCount() {
        return travelIntroductionMapper.selectIntroductionCount();
    }

    /**
     * 获取攻略详情
     * @param id 攻略id
     * @return 攻略实体
     */
    @Override
    public TravelIntroduction getIntroductionDetails(Integer id) {
        return travelIntroductionMapper.selectIntroductionDetails(id);
    }

    /**
     * 删除攻略
     * @param id 攻略id
     * @return 删除条数
     */
    @Override
    public Integer deleteById(Integer id) {
        return travelIntroductionMapper.updateById(id);
    }


}
