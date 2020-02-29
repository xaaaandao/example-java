package com.example.demo.domain.services;

import com.example.demo.config.exceptions.DescricaoException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.config.exceptions.StatusException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.model.Item;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    @Autowired
    private ItemValidateService validateService;

    @Transactional
    public Item save(String descricao, String status, Servico servico) throws DescricaoException, StatusException, RecordFoundException {
        validateService.validateDescricao(descricao);
        validateService.validateStatus(status);
        Item item = Item.builder()
                .descricao(descricao)
                .status(status)
                .servico(servico)
                .build();
        return repository.saveAndFlush(item);
    }

    public Item updateDescricao(Item item, String descricao) throws DescricaoException, StatusException, RecordFoundException {
        validateService.validateDescricao(descricao);
        item.setDescricao(descricao);
        return repository.save(item);
    }

    public Item updateStatus(Item item, String status) throws DescricaoException, StatusException {
        validateService.validateStatus(status);
        item.setStatus(status);
        return repository.save(item);
    }

    public List<Item> find() {
        return repository.findAll();
    }

    public List<Item> find(Servico servico) {
        return repository.findByServico(servico);
    }

    public List<Item> findAllByStatus(String status) {
        return repository.findAllByStatus(status);
    }

    public List<Item> findAllByDescriptionContaining(String description) {
        return repository.findByDescricaoContaining(description);
    }

    public Item find(String descricao) {
        return repository.findByDescricao(descricao);
    }

    public Item findByStatus(String status) {
        return repository.findByStatus(status);
    }

    public void deleteByDescricao(String descricao) throws RecordNotFoundException {
        Item item = find(descricao);
        if (Objects.isNull(item))
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getMessage());
        repository.delete(item);
    }

    public void deleteByStatus(String status) throws RecordNotFoundException {
        Item item = findByStatus(status);
        if (Objects.isNull(item))
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getMessage());
        repository.delete(item);
    }
}
