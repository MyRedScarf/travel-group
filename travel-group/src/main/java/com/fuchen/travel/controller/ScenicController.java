package com.fuchen.travel.controller;

import com.fuchen.travel.entity.*;
import com.fuchen.travel.service.CommentService;
import com.fuchen.travel.service.FavoriteService;
import com.fuchen.travel.service.ScenicService;
import com.fuchen.travel.service.UserService;
import com.fuchen.travel.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fuchen.travel.util.TravelConstant.ENTITY_TYPE_SCENIC;

/**
 * @author Fu chen
 * @date 2022/11/10
 * 景点-controller层
 */
@Slf4j
@Controller
public class ScenicController {

    @Resource
    private HostHolder hostHolder;

    @Resource
    private ScenicService scenicService;

    @Resource
    private FavoriteService favoriteService;

    @Resource
    private CommentService commentService;

    @Resource
    private UserService userService;

    /**
     * 获取景区详情信息
     * @param scenicId 景区id
     * @param model 模型渲染
     * @return 返回景区详情页面
     */
    @GetMapping("/scenic-detail/{scenicId}")
    public String getScenicDetail (@PathVariable("scenicId") Integer scenicId, Model model, Page page){
        //获取点评总数
        Integer scenicCommentCount = commentService.findCountByEntity(ENTITY_TYPE_SCENIC, scenicId);
        //查询景区详情
        Scenic scenic = scenicService.findScenicById(scenicId);
        model.addAttribute("scenic", scenic);
        User user = hostHolder.getUser();
        if (ObjectUtils.allNotNull(user)) {
            //查询用户收藏信息
            Favorite collection = favoriteService.findByUserIdAndScenicId(user.getId(), scenic.getId());
            model.addAttribute("collection", collection);
            //查询景点收藏次数
            Integer collectionCount = favoriteService.getCollectionCount(scenicId);
            model.addAttribute("collectionCount", collectionCount);
            page.setLimit(10);
            page.setRows(scenicCommentCount);
            //查询景点点评列表
            List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_SCENIC, scenicId, page.getOffset(), page.getLimit());
            List<Map<String, Object>> result = new ArrayList<>();
            commentList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("user", userService.findById(item.getUserId()));
                map.put("comment", item);
                result.add(map);
            });
            model.addAttribute("commentList", result);
            model.addAttribute("scenicCommentCount", scenicCommentCount);
        } else {
            model.addAttribute("collectionCount",  favoriteService.getCollectionCount(scenicId));
        }
        return "/site/scenic-detail";
    }

    /**
     * 请求景点列表
     * @param model 模型渲染
     * @param page 分页信息
     * @return 景点列表页面
     */
    @GetMapping("/scenic-list")
    public String scenicList(Model model, Page page){
        Integer scenicCountAll = scenicService.findScenicCountAll();
        //分页信息
        page.setLimit(5);
        page.setPath("/scenic-list");
        page.setRows(scenicCountAll);

        //查询景区
        List<Scenic> allScenic = scenicService.findAllScenic(page.getOffset(),page.getLimit());

        List<Map<String, Object>> scenicList = new ArrayList<>();
        if (allScenic != null && allScenic.size()>0) {
            for (Scenic scenic : allScenic) {
                Map<String, Object> map = new HashMap<>();
                if (scenic.getIntroduce().length() > 48) {
                    scenic.setIntroduce(scenic.getIntroduce().substring(0, 47) + "...");
                }
                if (scenic.getContent().length() > 40) {
                    scenic.setContent(scenic.getContent().substring(0, 39) + "...");
                }
                map.put("scenic", scenic);
                scenicList.add(map);
            }
        }

        model.addAttribute("scenicList", scenicList);
        model.addAttribute("scenicCount", scenicCountAll);


        List<Scenic> scenicConllectionList = favoriteService.findCollectionByCount(0, 4);
        List<Map<String, Object>> scenicListByCount = new ArrayList<>();

        if (scenicConllectionList != null && scenicConllectionList.size() > 0) {
            for (Scenic scenic : scenicConllectionList) {
                Map<String, Object> map = new HashMap<>();
                map.put("scenic", scenic);
                scenicListByCount.add(map);
            }
        }
        model.addAttribute("scenicsHot", scenicListByCount);

        return "/site/scenic-list";
    }

    /**
     * 获取景区图片
     * @param imageName 景区图片的名称
     * @param response 响应体
     * 废弃
     */
    @Deprecated
    @GetMapping("/scenicImg/{imageName}")
    public void getScenicImage(@PathVariable("imageName") String imageName, HttpServletResponse response) {
        String scenicImage = "";
        //服务器存放路径
        imageName = scenicImage + "/" + imageName;
        //文件后缀
        String suffix = imageName.substring(imageName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);

        try (FileInputStream inputStream = new FileInputStream(imageName);) {

            OutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("读写图像失败！" + e.getMessage());
        }
    }
}
