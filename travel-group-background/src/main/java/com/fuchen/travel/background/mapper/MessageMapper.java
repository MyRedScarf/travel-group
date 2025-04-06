package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/11
 * 消息-mapper层
 */
@Mapper
public interface MessageMapper {

    /**
     * 根据userId查询消息数量
     * @return 返回消息数量
     * @param status 状态
     * @param userId 根据userId查询
     */
    Integer selectMessageCount(@Param("status") Integer status, @Param("userId") Integer userId);
    /**
     * 查询消息列表
     * @return 返回消息集合
     * @param offset 查询起始行
     * @param limit 查询条数
     * @param status 状态
     * @param userId 根据userId查询
     */
    List<Message> selectMessageList(@Param("status") Integer status, @Param("userId") Integer userId,
                                    @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 修改当前消息的状态
     * @param id 当前消息的id
     * @param status 需要修改的状态
     */
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
