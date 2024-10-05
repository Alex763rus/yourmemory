package com.example.yourmemory.model.jpa;

import com.example.yourmemory.constant.ReminderStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReminderPlanRepository extends CrudRepository<ReminderPlan, Long> {

    List<ReminderPlan> findAllByReminderStatus(ReminderStatus reminderStatus);

    void deleteByBirthdayCard(BirthdayCard birthdayCard);
}
