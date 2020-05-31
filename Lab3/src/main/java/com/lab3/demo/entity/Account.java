package com.lab3.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(generator = "accounts_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "accounts_id_seq", sequenceName = "accounts_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "balance")
    private int balance;

    @Column(name = "blocked")
    private boolean blocked;

}
