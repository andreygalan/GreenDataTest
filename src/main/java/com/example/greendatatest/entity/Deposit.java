package com.example.greendatatest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @Column(name = "bank_id")
    private Long bankId;

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @Column(name = "client_id")
    private Long clientId;

    private Instant openingDate;

    private double percent;

    private int termInMonths;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Bank bank;

}