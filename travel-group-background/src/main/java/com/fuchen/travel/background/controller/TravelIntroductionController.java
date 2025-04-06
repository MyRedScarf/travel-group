package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.entity.TravelIntroduction;
import com.fuchen.travel.background.service.TravelIntroductionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
public class TravelIntroductionController {

    @Resource
    private TravelIntroductionService travelIntroductionService;

    @GetMapping("/introduction")
    public String introduction(){
        return "/pages/travel-introduction";
    }


    @PostMapping("/addIntroduction")
    public String addIntroduction(String title, MultipartFile introductionImg, String editorText) {
        travelIntroductionService.saveIntroduction(title, editorText, introductionImg);
        return "/pages/travel-introduction";
    }

    @GetMapping("/introductionList")
    public String introductionList(Model model, Page page) {
        Integer introductionCount = travelIntroductionService.getIntroductionCount();
        page.setRows(introductionCount);
        page.setLimit(10);
        page.setPath("/introductionList");
        List<TravelIntroduction> introductionList = travelIntroductionService.getIntroductionList(page.getOffset(), page.getLimit());
        model.addAttribute("introductionList", introductionList);
        return "/pages/travel-introduction-list";
    }

    @GetMapping("/introductionDetails/{introductionId}")
    public String introductionDetails(@PathVariable("introductionId") Integer introductionId, Model model) {
        log.info("查看 -> 攻略详情id：" + introductionId);
        TravelIntroduction travelIntroduction = travelIntroductionService.getIntroductionDetails(introductionId);
        model.addAttribute(travelIntroduction);
        return "/pages/travel-introduction-details";
    }

    @GetMapping("/deleteIntroduction/{introductionId}")
    public String deleteIntroduction(@PathVariable("introductionId") Integer introductionId) {
        log.info("删除 -> 攻略id：" + introductionId);
        travelIntroductionService.deleteById(introductionId);
        return "redirect:/introductionList";
    }
}
