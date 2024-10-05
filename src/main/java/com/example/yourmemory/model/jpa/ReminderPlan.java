package com.example.yourmemory.model.jpa;

import com.example.yourmemory.constant.ReminderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "reminder_plan")
public class ReminderPlan {

    @Id
    @Column(name = "reminder_plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderPlanId;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "birthdayCardId")
    private BirthdayCard birthdayCard;

    @Column(name = "reminder_status")
    private ReminderStatus reminderStatus;

    @Column(name = "birthday")
    private LocalDateTime reminderDateTime;

    @Column(name = "turns_years_old")
    private Long turnsYearsOld;

}
