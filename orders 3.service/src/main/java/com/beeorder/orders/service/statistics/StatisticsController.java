package com.beeorder.orders.service.statistics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController
{
    public StatisticsService statisticsService = new StatisticsService();

    @GetMapping("")
    public String checkout(){

        return statisticsService.getMax();
    }
}
