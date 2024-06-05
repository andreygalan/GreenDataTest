package com.example.greendatatest.service;

import com.example.greendatatest.entity.Bank;

import java.util.List;

public interface BankService {

    List<Bank> getAllBanks();

    Bank getBankById(Long id);

    Bank createBank(Bank bank);

    Bank updateBank(Long id, Bank bank);

    void deleteBank(Long id);
}
