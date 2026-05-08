package com.ufos.platform.modules.enterprise.api;

import com.ufos.platform.modules.enterprise.dto.BusinessCalendarRequest;
import com.ufos.platform.modules.enterprise.dto.BusinessCalendarResponse;
import com.ufos.platform.modules.enterprise.dto.CalendarHolidayRequest;
import com.ufos.platform.modules.enterprise.dto.CalendarHolidayResponse;
import com.ufos.platform.modules.enterprise.service.BusinessCalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enterprise/calendars")
@RequiredArgsConstructor
public class BusinessCalendarController {

    private final BusinessCalendarService calendarService;

    @PostMapping
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public BusinessCalendarResponse createCalendar(@Valid @RequestBody BusinessCalendarRequest request) {
        return calendarService.createCalendar(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<BusinessCalendarResponse> getAllCalendars() {
        return calendarService.getAllCalendars();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public BusinessCalendarResponse getCalendar(@PathVariable UUID id) {
        return calendarService.getCalendar(id);
    }

    @PostMapping("/{id}/holidays")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public CalendarHolidayResponse addHoliday(@PathVariable UUID id, @Valid @RequestBody CalendarHolidayRequest request) {
        return calendarService.addHoliday(id, request);
    }

    @GetMapping("/{id}/holidays")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<CalendarHolidayResponse> getHolidays(@PathVariable UUID id) {
        return calendarService.getHolidays(id);
    }
}
