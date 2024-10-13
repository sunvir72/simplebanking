package com.brainridge.simplebanking.model;

import com.brainridge.simplebanking.constants.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private String counterPartyEmail;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Transaction(Long accountId, TransactionType transactionType, BigDecimal amount,
                       BigDecimal balance, String counterPartyEmail) {
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
        this.transactionType = transactionType;
        this.counterPartyEmail = counterPartyEmail;
        this.createdAt = LocalDateTime.now();
    }

}