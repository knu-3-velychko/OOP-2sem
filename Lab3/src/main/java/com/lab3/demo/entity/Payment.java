package com.lab3.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payments")
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(generator = "payments_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "payments_id_seq", sequenceName = "payments_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_from", updatable = false)
    private Card from;

    @ManyToOne
    @JoinColumn(name = "card_to", updatable = false, nullable = false)
    private Card to;

    @Column(name = "amount")
    private int amount;
}
