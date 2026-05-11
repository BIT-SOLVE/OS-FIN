package com.ufos.platform.modules.enterprise.repository;

import com.ufos.platform.modules.enterprise.domain.CalendarHolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CalendarHolidayRepository extends JpaRepository<CalendarHolidayEntity, UUID> {
    List<CalendarHolidayEntity> findByCalendarId(UUID calendarId);
}
