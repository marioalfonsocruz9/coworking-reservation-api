package com.coworking.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.dto.report.OccupancyReportResponse;
import com.coworking.repository.ReservationRepository;
import com.coworking.repository.SpaceRepository;
import com.coworking.service.ReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final SpaceRepository spaceRepository;

    private final ReservationRepository reservationRepository;

    @Override
    @Cacheable("occupancy-report")
    public List<OccupancyReportResponse> getOccupancy(
            LocalDate start,
            LocalDate end) {
    	
    	LOGGER.info("Generating occupancy report...");
    	
        long totalHours =
                ChronoUnit.HOURS.between(
                        start.atStartOfDay(),
                        end.plusDays(1).atStartOfDay());

        return spaceRepository.findAll()
                .stream()
                .map(space -> {

                    Double reservedSeconds =
                            reservationRepository.getReservedSeconds(
                                    space.getId(),
                                    start.atStartOfDay(),
                                    end.plusDays(1).atStartOfDay());

                    double reservedHours =
                            reservedSeconds / 3600.0;

                    BigDecimal percentage =
                            BigDecimal.valueOf(
                                    (reservedHours / totalHours) * 100)
                                    .setScale(2, RoundingMode.HALF_UP);

                    return new OccupancyReportResponse(
                            space.getId(),
                            space.getName(),
                            percentage);

                })
                .toList();

    }

}
