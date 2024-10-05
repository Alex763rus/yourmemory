package com.example.yourmemory.model.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BirthdayCardRepository extends CrudRepository<BirthdayCard, Long> {

    public List<BirthdayCard> findAllByUserOrderByBirthdayAsc(User user);

}
