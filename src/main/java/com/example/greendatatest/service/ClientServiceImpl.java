package com.example.greendatatest.service;

import com.example.greendatatest.entity.Client;
import com.example.greendatatest.repository.ClientRepository;
import com.example.greendatatest.repository.Filter;
import com.example.greendatatest.repository.SpecificationCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final SpecificationCreator<Client> specificationCreator;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getFilteredClients(List<Filter> filters, String sortBy, String sortDirection) {

        Specification<Client> specification = specificationCreator.getSpecificationFromFilters(filters);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        return clientRepository.findAll(specification, sort);

    }

    @Override
    public Client getClientById(Long id) {

        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client createClient(Client client) {

        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client existingClient = clientRepository.findById(id).orElse(null);
        if (existingClient != null) {
            existingClient.setName(client.getName());
            existingClient.setShortName(client.getShortName());
            existingClient.setAddress(client.getAddress());
            existingClient.setLegalForm(client.getLegalForm());
            return clientRepository.save(existingClient);
        }
        return null;
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }


}
