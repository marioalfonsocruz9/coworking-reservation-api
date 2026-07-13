package com.coworking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.dto.report.OccupancyReportResponse;
import com.coworking.service.ReportService;
import com.coworking.util.ApiPaths;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPaths.REPORTS)
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/occupancy")
    public List<OccupancyReportResponse> occupancy(

            @RequestParam LocalDate start,

            @RequestParam LocalDate end) {

        return reportService.getOccupancy(start, end);

    }

}
