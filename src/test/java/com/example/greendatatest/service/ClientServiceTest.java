package com.example.greendatatest.service;


import com.example.greendatatest.entity.Client;
import com.example.greendatatest.repository.ClientRepository;
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
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.example.greendatatest.repository.QueryOperator.LIKE;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    SpecificationCreator<Client> specificationCreator;

    @InjectMocks

    ClientServiceImpl clientService;

    @Test
    void getAllClients(){
        List<Client> clients =LongStream.range(1, 4)
                .mapToObj(i -> new Client( i,"name%d".formatted(i),"sname%d".formatted(i),"adres%d".formatted(i),"lf%d".formatted(i)))
                .toList();

        doReturn(clients).when(clientRepository).findAll();

        List<Client> result = clientService.getAllClients();


        assertEquals(clients, result);
        verify(clientRepository).findAll();
    }

    @Test
    void getFilteredClients(){

        List<Client> clients = LongStream.range(1, 4)
                .mapToObj(i -> new Client(i,"name%d".formatted(i),"sname%d".formatted(i),"adres%d".formatted(i),"lf%d".formatted(i)))
                .toList();
        Specification<Client> spec = Specification.where(null);
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.builder().field("name").value("n").operator(LIKE).build());

        doReturn(clients).when(clientRepository).findAll(spec, sort);
        doReturn(spec).when(specificationCreator).getSpecificationFromFilters(filters);


        List<Client> result = clientService.getFilteredClients(filters,"name" ,"asc");

        assertEquals(clients, result);
        verify(clientRepository).findAll(spec, sort);
        verify(specificationCreator).getSpecificationFromFilters(filters);

    }

    @Test
    void getClientById(){

        Client client = new Client(1L,"name","sName","address","LF");
        doReturn(Optional.of(client)).when(clientRepository).findById(1L);

        Client result = clientService.getClientById(1L);

        assertEquals(client,result);
        verify(clientRepository).findById(1L);

    }

    @Test
    void createClient(){
        Client client = new Client(1L,"name","sName","address","LF");

        doReturn(client).when(clientRepository).save(new Client(null,"name","sName","address","LF"));

        Client result = clientService.createClient(new Client(null,"name","sName","address","LF"));

        assertEquals(client, result);
        verify(clientRepository).save(new Client(null,"name","sName","address","LF"));

    }

    @Test
    void updateClient(){
        Client client = new Client(1L,"name","sName","address","LF");
        Client clientToUpdate = new Client(null,"name1","sName1","address","LF");
        Client updatedClient = new Client(1L,"name1","sName1","address","LF");

        doReturn(Optional.of(client)).when(clientRepository).findById(1L);
        doReturn(updatedClient).when(clientRepository).save(updatedClient);

        Client result = clientService.updateClient(1L,clientToUpdate);
        assertEquals(client, result);

        verify(clientRepository).save(updatedClient);
        verify(clientRepository).findById(1L);

    }

    @Test
    void deleteClient(){
        Long clientId = 1L;

        clientService.deleteClient(clientId);
        verify(clientRepository).deleteById(clientId);

    }

}
