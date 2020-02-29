package com.example.demo.domain.services;

import com.example.demo.config.exceptions.DateException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.config.exceptions.errors.ErrorMessages;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.model.Vistoria;
import com.example.demo.domain.repositories.VistoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class VistoriaService {
    @Autowired
    private VistoriaRepository repository;

    @Autowired
    private VistoriaValidateService validateService;

    @Transactional
    public Vistoria save(String data, Servico servico) throws DateException, ParseException {
        validateService.validate(data);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(data);
        Vistoria vistoria = Vistoria.builder()
                .data(date)
                .servico(servico)
                .build();
        return repository.saveAndFlush(vistoria);
    }

    public Vistoria update(Vistoria vistoria, String data) throws DateException, ParseException {
        validateService.validate(data);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate = sdf.parse(data);
        vistoria.setData(newDate);
        return repository.save(vistoria);
    }

    public List<Vistoria> find() {
        return repository.findAll();
    }

    public Vistoria find(Servico servico) {
        return repository.findByServico(servico);
    }

    public Vistoria find(Date data) {
        return repository.findByData(data);
    }

    public List<Vistoria> findAllByData(Date data) {
        return repository.findAllByData(data);
    }

    public void delete(Servico servico) throws RecordNotFoundException {
        Vistoria vistoria = find(servico);
        if (Objects.isNull(vistoria))
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getMessage());
        repository.delete(vistoria);
    }

    public void deleteByDate(Date data) throws RecordNotFoundException {
        Vistoria vistoria = find(data);
        if (Objects.isNull(vistoria))
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.getMessage());
        repository.delete(vistoria);
    }
}
