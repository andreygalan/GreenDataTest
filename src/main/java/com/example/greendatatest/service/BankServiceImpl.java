package com.example.greendatatest.service;

import com.example.greendatatest.entity.Bank;
import com.example.greendatatest.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank getBankById(Long id) {

        return bankRepository.findById(id).orElse(null);
    }

    @Override
    public Bank createBank(Bank bank) {

        return bankRepository.save(bank);
    }

    @Override
    public Bank updateBank(Long id, Bank bank) {

        Bank existingBank = bankRepository.findById(id).orElse(null);
        if (existingBank != null) {
            existingBank.setName(bank.getName());
            existingBank.setBik(bank.getBik());
            return bankRepository.save(existingBank);
        }
        return null;
    }

    @Override
    public void deleteBank(Long id) {

        bankRepository.deleteById(id);
    }
}
