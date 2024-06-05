package com.example.greendatatest.service;


import com.example.greendatatest.entity.Bank;
import com.example.greendatatest.entity.Client;
import com.example.greendatatest.repository.BankRepository;
import com.example.greendatatest.repository.Filter;
import com.example.greendatatest.repository.SpecificationCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static com.example.greendatatest.repository.QueryOperator.LIKE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {
    @Mock
    BankRepository bankRepository;

    @Mock
    SpecificationCreator<Bank> specificationCreator;

    @InjectMocks

    BankServiceImpl bankService;

    @Test
    void getAllBanks(){
        List<Bank> banks = LongStream.range(1, 4)
                .mapToObj(i -> new Bank(i,"name%d".formatted(i),"bik%d".formatted(i)))
                .toList();

        doReturn(banks).when(bankRepository).findAll();

        List<Bank> result = bankService.getAllBanks();


        assertEquals(banks, result);
        verify(bankRepository).findAll();
    }

    @Test
    void getFilteredBanks(){

        List<Bank> banks = LongStream.range(1, 4)
                .mapToObj(i -> new Bank(i,"name%d".formatted(i),"bik%d".formatted(i)))
                .toList();
        Specification<Bank> spec = Specification.where(null);
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.builder().field("name").value("n").operator(LIKE).build());

        doReturn(banks).when(bankRepository).findAll(spec, sort);
        doReturn(spec).when(specificationCreator).getSpecificationFromFilters(filters);


        List<Bank> result = bankService.getFilteredBanks(filters,"name" ,"asc");

        assertEquals(banks, result);
        verify(bankRepository).findAll(spec, sort);
        verify(specificationCreator).getSpecificationFromFilters(filters);

    }

    @Test
    void getBankById(){

        Bank bank = new Bank(1L,"name","bik");
        doReturn(Optional.of(bank)).when(bankRepository).findById(1L);

        Bank result = bankService.getBankById(1L);

        assertEquals(bank,result);
        verify(bankRepository).findById(1L);

    }

    @Test
    void createBank(){
        Bank bank = new Bank(1L,"name","bik");

        doReturn(bank).when(bankRepository).save(new Bank(null,"name","bik"));

        Bank result = bankService.createBank(new Bank(null,"name","bik"));

        assertEquals(bank, result);
        verify(bankRepository).save(new Bank(null,"name","bik"));

    }

    @Test
    void updateBank(){
        Bank bank = new Bank(1L,"name","bik");
        Bank bankToUpdate = new Bank(null,"name1","bik1");
        Bank updatedBank = new Bank(1L,"name1","bik1");

        doReturn(Optional.of(bank)).when(bankRepository).findById(1L);
        doReturn(updatedBank).when(bankRepository).save(updatedBank);

        Bank result = bankService.updateBank(1L,bankToUpdate);
        assertEquals(bank, result);

        verify(bankRepository).save(updatedBank);
        verify(bankRepository).findById(1L);

    }

    @Test
    void deleteBank(){
        Long clientId = 1L;

        bankService.deleteBank(clientId);
        verify(bankRepository).deleteById(clientId);

    }
}
