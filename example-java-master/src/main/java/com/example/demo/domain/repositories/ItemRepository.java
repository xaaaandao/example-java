package com.example.demo.domain.repositories;

import com.example.demo.domain.model.Item;
import com.example.demo.domain.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByStatus(String status);
    List<Item> findByDescricaoContaining(String description);
    Item findByDescricao(String descricao);
    Item findByStatus(String status);
    List<Item> findByServico(Servico servico);
}
