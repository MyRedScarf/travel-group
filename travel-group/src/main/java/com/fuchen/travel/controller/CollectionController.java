package com.fuchen.travel.controller;

import com.fuchen.travel.entity.Favorite;
import com.fuchen.travel.entity.User;
import com.fuchen.travel.service.FavoriteService;
import com.fuchen.travel.util.TravelUtil;
import com.fuchen.travel.util.HostHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Fu chen
 * @date 2022/12/4
 * 收藏-controller层
 */
@Controller
public class CollectionController {

    private final HostHolder hostHolder;

    private final FavoriteService favoriteService;

    public CollectionController(HostHolder hostHolder, FavoriteService favoriteService) {
        this.hostHolder = hostHolder;
        this.favoriteService = favoriteService;
    }


    /**
     * 异步请求处理，用于处理收藏景点的请求,
     * @param scenicId 景点的id
     * @return json，最新收藏次数
     */
    @PostMapping("/collection")
    @ResponseBody
    public String collection(Integer scenicId){
        //获取用户id
        User user = hostHolder.getUser();
        //如果用户为空，说明还没有登录，返回json数据，提示错误信息
        if (user == null){
            return TravelUtil.getJsonString(403, "你还没有登录！");
        }

        //查询用户对该景点的收藏信息
        Favorite collection = favoriteService.findByUserIdAndScenicId(user.getId(), scenicId);

        //如果该查询到的收藏信息为空，说明用户没有收藏该景点，否则说明用户收藏了该景点
        //因此根据是否收藏调用不同的业务
        //用户未收藏，调用收藏业务
        //用户收藏，调用取消收藏业务
        if (collection == null) {
            //添加收藏
            favoriteService.addCollection(user.getId(), scenicId, new Date());
            //收藏成功，返回JSON数据，同时msg中填入最新的收藏次数
            return TravelUtil.getJsonString(0,favoriteService.getCollectionCount(scenicId).toString());
        } else {
            //取消收藏
            favoriteService.removeCollection(user.getId(), scenicId);
            //取消收藏成功，返回JSON数据，同时msg中填入最新的收藏次数
            return TravelUtil.getJsonString(1,favoriteService.getCollectionCount(scenicId).toString());
        }

    }

}
