package com.example.greendatatest.controller;

import com.example.greendatatest.entity.Deposit;
import com.example.greendatatest.repository.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.greendatatest.service.DepositService;

import java.util.List;

@RestController
@RequestMapping("/api/deposits")
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @GetMapping
    public ResponseEntity<List<Deposit>> getAllDeposits() {
        List<Deposit> deposits = depositService.getAllDeposits();
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<Deposit>> getFilteredDeposits(
            @RequestParam List<Filter> filters,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Deposit> deposits = depositService.getFilteredDeposits(filters, sortBy, sortDirection);
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deposit> getDepositById(@PathVariable Long id) {
        Deposit deposit = depositService.getDepositById(id);
        if (deposit != null) {
            return new ResponseEntity<>(deposit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Deposit> createDeposit(@RequestBody Deposit deposit) {
        Deposit createdDeposit = depositService.createDeposit(deposit);
        return new ResponseEntity<>(createdDeposit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deposit> updateDeposit(@PathVariable Long id, @RequestBody Deposit deposit) {
        Deposit updatedDeposit = depositService.updateDeposit(id, deposit);
        if (updatedDeposit != null) {
            return new ResponseEntity<>(updatedDeposit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long id) {
        depositService.deleteDeposit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
