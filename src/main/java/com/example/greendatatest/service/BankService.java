package com.example.greendatatest.service;

import com.example.greendatatest.entity.Bank;
import com.example.greendatatest.repository.Filter;

import java.util.List;

public interface BankService {

    List<Bank> getAllBanks();

    List<Bank> getFilteredBanks(List<Filter> filters, String sortBy, String sortDirection);

    Bank getBankById(Long id);

    Bank createBank(Bank bank);

    Bank updateBank(Long id, Bank bank);

    void deleteBank(Long id);
}
