package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.entity.Scenic;
import com.fuchen.travel.background.service.ScenicService;
import com.fuchen.travel.background.util.TravelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 伏辰
 * @Date 2023/1/5
 */
@Controller
@Slf4j
public class ScenicController {

    @Autowired
    private ScenicService scenicService;

    /**
     * 景点管理
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/scenic-control")
    public String scenicControl(Model model, Page page) {
        //获取景点数量
        Integer scenicCount = scenicService.getScenicCount();
        //设置分页数据
        page.setLimit(5);
        page.setPath("/scenic-control");
        page.setRows(scenicCount);
        //获取景点集合
        List<Scenic> scenic = scenicService.getScenic(page.getOffset(), page.getLimit());
        //用户存放景点数据
        List<Map<String, Scenic>> scenicList = new ArrayList<>(scenicCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < scenic.size(); i++) {
            Map<String, Scenic> map = new HashMap<>();
            map.put("scenic", scenic.get(i));
            scenicList.add(map);
        }

        model.addAttribute("scenicList", scenicList);

        return "/pages/scenic-control";
    }


    /**
     * 推荐景点
     * @param recommendScenic 景点id
     * @return
     */
    @PostMapping("/recommend")
    @ResponseBody
    public String recommendScenic(String recommendScenic) {
        //判断推荐景点数量是否已满
        if (scenicService.getScenicRecommendCount() == 6) {
            return TravelUtil.getJsonString(1, "推荐景点已满");
        }

        //判断景点id是否为空
        if (recommendScenic.isEmpty()) {
            return TravelUtil.getJsonString(1, "请填写景点id");
        }
        //推荐景点
        scenicService.recommend(recommendScenic);
        return TravelUtil.getJsonString(0);
    }

    @PostMapping("/removeRecommend")
    @ResponseBody
    public String removeRecommendScenic(String removeRecommend){
        //判断推荐景点数量是否已满
        if (scenicService.getScenicRecommendCount() == 0) {
            return TravelUtil.getJsonString(1, "推荐景点不足");
        }

        //判断景点id是否为空
        if (removeRecommend.isEmpty()) {
            return TravelUtil.getJsonString(1, "请填写景点id");
        }
        //移出推荐景点
        scenicService.removeRecommend(removeRecommend);
        return TravelUtil.getJsonString(0);
    }

    /**
     * 添加景点信息
     * @param scenicName 景点名称
     * @param merchant 经营者
     * @param price 价格
     * @param phone 电话
     * @param address 地址
     * @param scenicIntroduce 景点简介
     * @param scenicContent 景点说明
     * @param notice 旅游须知
     * @param scenicImg 景点图片
     * @param model 模板
     * @return
     */
    @PostMapping("/addScenic")
    public String addScenic(String scenicName, String merchant, String price,
                            String phone, String address, String scenicIntroduce,
                            String scenicContent, String notice, MultipartFile scenicImg, Model model){
        //查询当前景点是否存在
        Integer scenicId = scenicService.isScenicName(scenicName);
        //将数据赋值给scenic对象
        Scenic scenic = new Scenic();
        scenic.setId(scenicId);
        scenic.setScenicName(scenicName);
        scenic.setMerchant(merchant);
        scenic.setPrice(Double.parseDouble(price));
        scenic.setPhone(phone);
        scenic.setAddress(address);
        scenic.setIntroduce(scenicIntroduce);
        scenic.setContent(scenicContent);
        scenic.setNotice(notice);


        //判断文件后缀
        String filename = scenicImg.getOriginalFilename();
        //获得文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("headerImgMsg","图片格式错误！");
            return "/site/setting";
        }
        //添加景点
        scenicService.addScenic(scenic, scenicImg, filename, suffix);

        return "redirect:/scenic-control";
    }

    /**
     * 删除景点信息
     * @param list 需要删除的景点id集合
     * @return
     */
    @ResponseBody
    @PostMapping("/removeScenic")
    public String removeScenic(@RequestParam("list[]")List<String> list) {
        //判断集合是否为空
        if (list.size() == 0) {
            TravelUtil.getJsonString(1,"未选择景点！");
        }
        //删除景点信息
        scenicService.removeScenic(list);
        return TravelUtil.getJsonString(0,"景点删除成功！");
    }

    @GetMapping("/scenic/search")
    public String searchUser(String keyword, Model model, Page page){
        //判断关键字是否为空
        if (keyword.isEmpty()) {
            model.addAttribute("searchMsg", "请输入搜素内容！");
            return "/pages/scenic-control";
        }

        //获取用户数量
        Integer scenicCount = scenicService.getScenicCountSearch(keyword);

        //设置分页数据
        page.setLimit(5);
        page.setPath("/scenic/search?keyword=" + keyword);
        page.setRows(scenicCount);
        //分页查询景点集合
        List<Scenic> scenic = scenicService.getScenicSearch(keyword, page.getOffset(), page.getLimit());
        //创建景点集合存放景点数据
        List<Map<String, Scenic>> scenicList = new ArrayList<>(scenicCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < scenic.size(); i++) {
            Map<String, Scenic> map = new HashMap<>();
            map.put("scenic", scenic.get(i));
            scenicList.add(map);
        }

        model.addAttribute("scenicList", scenicList);

        return "/pages/scenic-control";
    }
}
