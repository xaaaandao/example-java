package com.example.demo.domain.services;

import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    @Transactional
    public Servico save(Cliente cliente) {
        Servico servico = Servico.builder()
                .cliente(cliente)
                .build();
        return repository.saveAndFlush(servico);
    }

    @Transactional
    public Servico update(Servico servico, Cliente cliente) {
        servico.setCliente(cliente);
        return repository.saveAndFlush(servico);
    }

    public List<Servico> find() {
        return repository.findAll();
    }

    public Servico find(Cliente cliente) {
        return repository.findByCliente(cliente);
    }

    public void delete(Cliente cliente) throws RecordNotFoundException {
        Servico servico = find(cliente);
        if (Objects.isNull(servico))
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getMessage());
        repository.delete(servico);
    }
}
