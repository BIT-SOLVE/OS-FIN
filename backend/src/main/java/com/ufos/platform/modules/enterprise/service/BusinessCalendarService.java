package com.ufos.platform.modules.enterprise.service;

import com.ufos.platform.modules.enterprise.domain.BusinessCalendarEntity;
import com.ufos.platform.modules.enterprise.domain.CalendarHolidayEntity;
import com.ufos.platform.modules.enterprise.dto.BusinessCalendarRequest;
import com.ufos.platform.modules.enterprise.dto.BusinessCalendarResponse;
import com.ufos.platform.modules.enterprise.dto.CalendarHolidayRequest;
import com.ufos.platform.modules.enterprise.dto.CalendarHolidayResponse;
import com.ufos.platform.modules.enterprise.repository.BusinessCalendarRepository;
import com.ufos.platform.modules.enterprise.repository.CalendarHolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessCalendarService {

    private final BusinessCalendarRepository calendarRepository;
    private final CalendarHolidayRepository holidayRepository;

    @Transactional
    public BusinessCalendarResponse createCalendar(BusinessCalendarRequest request) {
        if (calendarRepository.findByCode(request.getCode().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Calendar with code " + request.getCode() + " already exists");
        }

        BusinessCalendarEntity entity = BusinessCalendarEntity.builder()
                .code(request.getCode().toUpperCase())
                .name(request.getName())
                .countryCode(request.getCountryCode() != null ? request.getCountryCode().toUpperCase() : null)
                .currencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode().toUpperCase() : null)
                .description(request.getDescription())
                .status(request.getStatus())
                .build();

        BusinessCalendarEntity saved = calendarRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public BusinessCalendarResponse getCalendar(UUID id) {
        return calendarRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Calendar not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<BusinessCalendarResponse> getAllCalendars() {
        return calendarRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CalendarHolidayResponse addHoliday(UUID calendarId, CalendarHolidayRequest request) {
        if (!calendarRepository.existsById(calendarId)) {
            throw new IllegalArgumentException("Calendar not found with id " + calendarId);
        }

        CalendarHolidayEntity entity = CalendarHolidayEntity.builder()
                .calendarId(calendarId)
                .holidayDate(request.getHolidayDate())
                .name(request.getName())
                .holidayType(request.getHolidayType())
                .build();

        CalendarHolidayEntity saved = holidayRepository.save(entity);
        return mapHolidayToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CalendarHolidayResponse> getHolidays(UUID calendarId) {
        return holidayRepository.findByCalendarId(calendarId).stream()
                .map(this::mapHolidayToResponse)
                .collect(Collectors.toList());
    }

    private BusinessCalendarResponse mapToResponse(BusinessCalendarEntity entity) {
        return BusinessCalendarResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .countryCode(entity.getCountryCode())
                .currencyCode(entity.getCurrencyCode())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private CalendarHolidayResponse mapHolidayToResponse(CalendarHolidayEntity entity) {
        return CalendarHolidayResponse.builder()
                .id(entity.getId())
                .calendarId(entity.getCalendarId())
                .holidayDate(entity.getHolidayDate())
                .name(entity.getName())
                .holidayType(entity.getHolidayType())
                .build();
    }
}
