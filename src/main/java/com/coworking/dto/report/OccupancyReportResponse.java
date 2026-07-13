package com.coworking.dto.report;

import java.math.BigDecimal;

public record OccupancyReportResponse(

        Long spaceId,

        String spaceName,

        BigDecimal occupancyPercentage

) {
}
