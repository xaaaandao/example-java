package com.example.demo.domain.services;

import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteValidateService validateService;

    @Transactional
    public Cliente save(String cpf) throws RecordFoundException, CpfException {
        validateService.validate(cpf);
        Cliente cliente = Cliente.builder().cpf(cpf).build();
        return repository.saveAndFlush(cliente);
    }

    public Cliente update(Cliente cliente, String novoCpf) throws CpfException, RecordFoundException {
        validateService.validate(novoCpf);
        cliente.setCpf(novoCpf);
        return repository.save(cliente);
    }

    public List<Cliente> find() {
        return repository.findAll();
    }

    public Cliente find(String cpf) {
        return repository.findByCpf(cpf);
    }

    public void delete(String cpf) throws RecordNotFoundException {
        Cliente cliente = find(cpf);
        if (Objects.isNull(cliente))
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getMessage());
        repository.delete(cliente);
    }

}
