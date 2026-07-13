package com.coworking.service;

import java.time.LocalDate;
import java.util.List;

import com.coworking.dto.report.OccupancyReportResponse;

public interface ReportService {
	
	List<OccupancyReportResponse> getOccupancy(
            LocalDate start,
            LocalDate end);
	
}
