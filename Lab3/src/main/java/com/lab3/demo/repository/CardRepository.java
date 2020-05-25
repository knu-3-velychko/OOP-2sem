package com.lab3.demo.repository;

import com.lab3.demo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUser(String email);
}
