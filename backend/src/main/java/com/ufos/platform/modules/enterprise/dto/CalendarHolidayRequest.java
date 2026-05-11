package com.ufos.platform.modules.enterprise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarHolidayRequest {
    @NotNull
    private LocalDate holidayDate;
    @NotBlank
    private String name;
    private String holidayType;
}
