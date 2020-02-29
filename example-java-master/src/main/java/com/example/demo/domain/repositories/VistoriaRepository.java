package com.example.demo.domain.repositories;

import com.example.demo.domain.model.Servico;
import com.example.demo.domain.model.Vistoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VistoriaRepository extends JpaRepository<Vistoria, Long> {
    Vistoria findByServico(Servico servico);
    List<Vistoria> findAllByData(Date data);
    Vistoria findByData(Date data);
}
