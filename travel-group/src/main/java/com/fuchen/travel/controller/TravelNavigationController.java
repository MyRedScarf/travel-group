package com.fuchen.travel.controller;


import com.fuchen.travel.entity.Page;
import com.fuchen.travel.entity.TravelIntroduction;
import com.fuchen.travel.service.TravelNavigationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Fu chen
 * @date 2023/4/1
 */
@Slf4j
@Controller
public class TravelNavigationController {

    @Resource
    private TravelNavigationService travelNavigationService;

    @GetMapping("/navigation")
    public String travelNavigation(Model model, Page page){
        Integer travelNavigationCount = travelNavigationService.getTravelNavigationCount();
        page.setLimit(20);
        page.setRows(travelNavigationCount);
        List<TravelIntroduction> travelIntroductionList = travelNavigationService.getTravelNavigationList(page.getOffset(), page.getLimit());
        model.addAttribute(travelIntroductionList);
        return "/site/travel-navigation";
    }

    @GetMapping("/navigation/introduction/{introductionId}")
    public String navigationIntroduction(@PathVariable("introductionId") String introductionId, Model model) {
        TravelIntroduction travelIntroduction = travelNavigationService.getTravelIntroduction(introductionId);
        model.addAttribute(travelIntroduction);
        return "/site/introduction-detail";
    }



}
