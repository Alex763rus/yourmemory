package com.example.yourmemory.model.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static org.example.tgcommons.constant.Constant.TextConstants.NEW_LINE;

@Getter
@Setter
@ToString
@Entity(name = "birthday_card")
public class BirthdayCard {

    @Id
    @Column(name = "birthday_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long birthdayCardId;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "chatId")
    private User user;

    @Column(name = "fio")
    private String fio;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "birthday")
    private LocalDate birthday;

    public String toString(String startMessage) {
        //todo перенести в функции
        val answer = new StringBuilder(startMessage);
        answer.append(NEW_LINE).append("*ФИО: *").append(getFio())
                .append(NEW_LINE).append("*Дата: *").append(getBirthday())
                .append(NEW_LINE).append("*Описание: *").append(getDescription())
                .append(NEW_LINE).append("*Сейчас лет: *").append(Period.between(getBirthday(), LocalDate.now()).getYears());
        return answer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthdayCard that = (BirthdayCard) o;
        return Objects.equals(birthdayCardId, that.birthdayCardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthdayCardId);
    }
}
