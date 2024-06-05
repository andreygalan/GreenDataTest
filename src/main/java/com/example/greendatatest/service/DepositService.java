package com.example.greendatatest.service;

import com.example.greendatatest.entity.Deposit;

import java.util.List;

public interface DepositService {
    List<Deposit> getAllDeposits();
    Deposit getDepositById(Long id);
    Deposit createDeposit(Deposit deposit);
    Deposit updateDeposit(Long id, Deposit deposit);
    void deleteDeposit(Long id);
}
