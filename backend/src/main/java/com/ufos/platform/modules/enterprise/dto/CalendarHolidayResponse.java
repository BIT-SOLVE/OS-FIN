package com.ufos.platform.modules.enterprise.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CalendarHolidayResponse {
    private UUID id;
    private UUID calendarId;
    private LocalDate holidayDate;
    private String name;
    private String holidayType;
}
