package com.example.greendatatest.controller;

import com.example.greendatatest.entity.Bank;
import com.example.greendatatest.repository.Filter;
import com.example.greendatatest.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;


    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }
    @GetMapping("/filters")
    public ResponseEntity<List<Bank>> getFilteredBanks(
            @RequestParam List<Filter> filters,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Bank> banks = bankService.getFilteredBanks(filters, sortBy, sortDirection);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        Bank bank = bankService.getBankById(id);
        if (bank != null) {
            return new ResponseEntity<>(bank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank createdBank = bankService.createBank(bank);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        Bank updatedBank = bankService.updateBank(id, bank);
        if (updatedBank != null) {
            return new ResponseEntity<>(updatedBank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
