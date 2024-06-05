package com.example.greendatatest.service;

import com.example.greendatatest.entity.Deposit;
import com.example.greendatatest.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {


    private final DepositRepository depositRepository;

    @Override
    public List<Deposit> getAllDeposits() {
        return depositRepository.findAll();
    }

    @Override
    public Deposit getDepositById(Long id) {

        return depositRepository.findById(id).orElse(null);
    }

    @Override
    public Deposit createDeposit(Deposit deposit) {

        return depositRepository.save(deposit);
    }

    @Override
    public Deposit updateDeposit(Long id, Deposit deposit) {

        Deposit existingDeposit = depositRepository.findById(id).orElse(null);
        if (existingDeposit != null) {
            existingDeposit.setClient(deposit.getClient());
            existingDeposit.setBank(deposit.getBank());
            existingDeposit.setOpeningDate(deposit.getOpeningDate());
            existingDeposit.setPercent(deposit.getPercent());
            existingDeposit.setTermInMonths(deposit.getTermInMonths());
            return depositRepository.save(existingDeposit);
        }
        return null;
    }

    @Override
    public void deleteDeposit(Long id) {
        depositRepository.deleteById(id);
    }
}
