package com.fuchen.travel.service.impl;

import com.fuchen.travel.entity.Scenic;
import com.fuchen.travel.mapper.ScenicMapper;
import com.fuchen.travel.service.ScenicService;
import com.fuchen.travel.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fu chen
 * @date 2022/11/27
 * 景点-service层-实现类
 */
@Service
public class ScenicServiceImpl implements ScenicService {

    private final ScenicMapper scenicMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public ScenicServiceImpl(ScenicMapper scenicMapper) {
        this.scenicMapper = scenicMapper;
    }

    /**
     * 查找景点信息
     * @param offset 分页起始行
     * @param limit 检索条数
     * @return 景点列表
     */
    @Override
    public List<Scenic> findAllScenic(Integer offset, Integer limit) {
        return scenicMapper.selectAllScenic(offset, limit);
    }

    /**
     * 根据景点id查询景点信息
     * @param id 景点id
     * @return 景点信息
     */
    @Override
    public Scenic findScenicById(Integer id) {
        //查询redis的该景点信息
        String redisKey = RedisKeyUtil.getScenic(id);
        Scenic scenic = (Scenic) redisTemplate.opsForValue().get(redisKey);
        //如果该景点为null，说明redis中没有该景点信息
        if (scenic == null) {
            //从数据库中查询该景点信息
            scenic = scenicMapper.selectScenicById(id);
            //将该景点信息放入redis中
            redisTemplate.opsForValue().set(redisKey, scenic);
        }
        //返回该景点信息
        return scenic;
    }

    /**
     * 查询景点个数
     * @return 返回景点个数
     */
    @Override
    public Integer findScenicCountAll() {
        return scenicMapper.selectScenicCountAll();
    }

    /**
     * 获取推荐的景点
     * @return 返回推荐的景点集合
     */
    @Override
    public List<Scenic> getScenicRe() {
        return scenicMapper.selectScenicRe();
    }
}
