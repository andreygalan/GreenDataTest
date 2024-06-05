package com.example.greendatatest.service;

import com.example.greendatatest.entity.Bank;
import com.example.greendatatest.entity.Client;
import com.example.greendatatest.entity.Deposit;
import com.example.greendatatest.repository.DepositRepository;
import com.example.greendatatest.repository.Filter;
import com.example.greendatatest.repository.SpecificationCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static com.example.greendatatest.entity.LegalForm.LLC;
import static com.example.greendatatest.repository.QueryOperator.GREATER_THAN;
import static com.example.greendatatest.repository.QueryOperator.LIKE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DepositServiceTest {

    @Mock
    DepositRepository depositRepository;

    @Mock
    SpecificationCreator<Deposit> specificationCreator;

    @InjectMocks
    DepositServiceImpl depositService;

    @Test
    void getAllDeposits(){

        List<Bank> banks = LongStream.range(1, 4)
                .mapToObj(i -> new Bank(i,"name%d".formatted(i),"bik%d".formatted(i)))
                .toList();

        List<Client> clients =LongStream.range(1, 4)
                .mapToObj(i -> new Client( i,"name%d".formatted(i),"sname%d".formatted(i),"adres%d".formatted(i),LLC))
                .toList();

        List<Deposit> deposits = LongStream.range(1, 4)
                .mapToObj(i -> new Deposit(i, i, i, Instant.parse("2024-06-05T18:22:33.243Z"),5*i,5,clients.get((int) i-1),banks.get((int) i-1)))
                .toList();

        doReturn(deposits).when(depositRepository).findAll();

        List<Deposit> result = depositService.getAllDeposits();


        assertEquals(deposits, result);
        verify(depositRepository).findAll();
    }

    @Test
    void getFilteredDeposits(){

        List<Bank> banks = LongStream.range(1, 4)
                .mapToObj(i -> new Bank(i,"name%d".formatted(i),"bik%d".formatted(i)))
                .toList();

        List<Client> clients =LongStream.range(1, 4)
                .mapToObj(i -> new Client( i,"name%d".formatted(i),"sname%d".formatted(i),"adres%d".formatted(i),LLC))
                .toList();

        List<Deposit> deposits = LongStream.range(1, 4)
                .mapToObj(i -> new Deposit(i, i, i, Instant.parse("2024-06-05T18:22:33.243Z"),5*i,5,clients.get((int) i-1),banks.get((int) i-1)))
                .toList();

        Specification<Deposit> spec = Specification.where(null);
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.builder().field("percent").value("4").operator(GREATER_THAN).build());

        doReturn(deposits).when(depositRepository).findAll(spec, sort);
        doReturn(spec).when(specificationCreator).getSpecificationFromFilters(filters);


        List<Deposit> result = depositService.getFilteredDeposits(filters,"name" ,"asc");

        assertEquals(deposits, result);
        verify(depositRepository).findAll(spec, sort);
        verify(specificationCreator).getSpecificationFromFilters(filters);

    }

    @Test
    void getDepositById(){

        Bank bank = new Bank(1L,"name","bik");
        Client client = new Client(1L,"name","sName","address",LLC);
        Deposit deposit = new Deposit(1L, 1L, 1L, Instant.parse("2024-06-05T18:22:33.243Z"),10,5,client,bank);
        doReturn(Optional.of(deposit)).when(depositRepository).findById(1L);

        Deposit result = depositService.getDepositById(1L);

        assertEquals(deposit,result);
        verify(depositRepository).findById(1L);

    }

    @Test
    void createDeposit(){

        Bank bank = new Bank(1L,"name","bik");
        Client client = new Client(1L,"name","sName","address",LLC);
        Deposit deposit = new Deposit(1L, 1L, 1L, Instant.parse("2024-06-05T18:22:33.243Z"),10,5,client,bank);
        doReturn(deposit).when(depositRepository).save(new Deposit(null, 1L, 1L, Instant.parse("2024-06-05T18:22:33.243Z"),10,5,client,bank));

        Deposit result = depositService.createDeposit(new Deposit(null, 1L, 1L, Instant.parse("2024-06-05T18:22:33.243Z"),10,5,client,bank));

        assertEquals(deposit,result);
        verify(depositRepository).save(new Deposit(null, 1L, 1L, Instant.parse("2024-06-05T18:22:33.243Z"),10,5,client,bank));

    }

    @Test
    void updateDeposit(){

        Bank bank = new Bank(1L,"name","bik");
        Client client = new Client(1L,"name","sName","address",LLC);
        Deposit deposit = new Deposit(1L, 1L, 1L, Instant.parse("2024-06-05T18:22:33.243Z"),10,5,client,bank);
        Deposit depositToUpdate = new Deposit(null, 1L, 1L, Instant.parse("2024-05-05T18:22:33.243Z"),15,5,client,bank);
        Deposit updatedDeposit = new Deposit(1L, 1L, 1L, Instant.parse("2024-05-05T18:22:33.243Z"),15,5,client,bank);

        doReturn(Optional.of(deposit)).when(depositRepository).findById(1L);
        doReturn(updatedDeposit).when(depositRepository).save(updatedDeposit);

        Deposit result = depositService.updateDeposit(1L, depositToUpdate);

        assertEquals(deposit,result);
        verify(depositRepository).save(updatedDeposit);
        verify(depositRepository).findById(1L);

    }

    @Test
    void deleteDeposit(){
        Long depositId = 1L;

        depositService.deleteDeposit(depositId);
        verify(depositRepository).deleteById(depositId);

    }


}

