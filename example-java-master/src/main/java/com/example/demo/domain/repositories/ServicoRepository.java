package com.example.demo.domain.repositories;

import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Servico findByCliente(Cliente cliente);
}
