package com.lab3.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cards")
@NoArgsConstructor
public class Card {
    @Id
    @Column(name = "card_number", nullable = false)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "account_id", updatable = false, nullable = false)
    private Account account;
}
