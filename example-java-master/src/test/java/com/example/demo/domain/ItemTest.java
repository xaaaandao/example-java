package com.example.demo.domain;

import com.example.demo.config.FunctionalTest;
import com.example.demo.config.exceptions.*;
import com.example.demo.domain._environments.EnvItem;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Item;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.repositories.ServicoRepository;
import com.example.demo.domain.services.ItemService;
import com.example.demo.domain.utils.CnpjCpf;
import com.example.demo.domain.utils.DescricaoStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

@FunctionalTest
public class ItemTest {
    @Autowired
    private ItemService service;

    @Autowired
    private EnvItem envItem;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @BeforeEach
    void setup() throws RecordFoundException, CpfException, DescricaoException, StatusException {
        envItem.init();
    }

    /* VERIFICA REGISTROS */
    @Test
    void checkIfHasTwoItem() {
        assertEquals(2, service.find().size());
    }

    @Test
    void checkIfDescricaoValid() {
        Item item = Item.builder().descricao("O porcelanato do chão está solto!").build();
        assertTrue(DescricaoStatus.validateDescricao(item.getDescricao()));
    }

    @Test
    void checkIfStatusValid() {
        Item item = Item.builder().status("Procedente").build();
        assertFalse(DescricaoStatus.validateStatus(item.getStatus()));
    }

    @Test
    void checkIfClienteHasCpfValid() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Item item = Item.builder().servico(servico).build();
        assertTrue(CnpjCpf.validate(item.getServico().getCliente().getCpf()));
    }

    /* ADICIONA REGISTRO */
    @Test
    void shouldAddItem() throws DescricaoException, StatusException, RecordFoundException {
        envItem.addItem();
        assertEquals(3, service.find().size());
    }

    @Test
    void shouldNotAddSameItem() throws DescricaoException, StatusException, RecordFoundException {
        envItem.addItem();
        assertEquals(3, service.find().size());
        Assertions.assertThrows(RecordFoundException.class, () -> {
            envItem.addItem();
        });
    }

    @Test
    void shouldAddItemDescriptionNumericAndCharacteres() throws DescricaoException, StatusException, RecordFoundException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        service.save("1-Piso do banheiro quebrado; 2-A porta da cozinha está caindo.", "ABERTO", servico);
        assertEquals(3, service.find().size());
    }

    @Test
    void shouldNotAddItemDescriptionEmpty() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DescricaoException.class, () -> {
            service.save(" ", "ABERTO", servico);
        });
    }

    @Test
    void shouldNotAddItemDescriptionNull() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DescricaoException.class, () -> {
            service.save(null, "ABERTO", servico);
        });
    }

    @Test
    void shouldNotAddItemDescriptionOnlySpecialCharacters() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DescricaoException.class, () -> {
            service.save("!@#", "ABERTO", servico);
        });
    }

    @Test
    void shouldNotAddItemDescricaoIsNumber() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DescricaoException.class, () -> {
            service.save("777", "ABERTO", servico);
        });
    }

    @Test
    void shouldNotAddItemStatusEmpty() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(StatusException.class, () -> {
            service.save("Está com cheiro de gás muito forte no meu apartamento", " ", servico);
        });
    }

    @Test
    void shouldNotAddItemStatusNotValid() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(StatusException.class, () -> {
            service.save("A torneira da máquina não funciona", "Improcedente", servico);
        });
    }

    @Test
    void shouldNotAddItemStatusOnlySpecialCharacters() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(StatusException.class, () -> {
            service.save("O vidro do banheiro está trincado", "!@#", servico);
        });
    }

    @Test
    void shouldNotAddItemStatusNull() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(StatusException.class, () -> {
            service.save("O box do banheiro está manchado", null, servico);
        });
    }

    @Test
    void shouldNotAddItemStatusIsNumber() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(StatusException.class, () -> {
            service.save("O box do banheiro está manchado", "777", servico);
        });
    }

    @Test
    void shouldNotAddItemStatusNumericAndCharacteres() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(StatusException.class, () -> {
            service.save("1-Piso do banheiro quebrado; 2-A porta da cozinha está caindo.", "1-ABERTO 2-FECHADO", servico);
        });
    }

    /* ATUALIZA REGISTROS */
    @Test
    void shouldUpdateDescricao() throws DescricaoException, StatusException, RecordFoundException {
        Item item = service.find("A luz de casa não está funcionando");
        service.updateDescricao(item, "A luz de casa não está funcionando!");
        assertEquals("A luz de casa não está funcionando!", item.getDescricao());
    }

    @Test
    void shouldNotUpdateDescricao() {
        Item item = service.find("A luz de casa não está funcionando");
        Assertions.assertThrows(DescricaoException.class, () -> {
            service.updateDescricao(item, " ");
        });
    }

    @Test
    void shouldNotUpdateDescricaoExist() {
        Item item = service.find("A luz de casa não está funcionando");
        Assertions.assertThrows(RecordFoundException.class, () -> {
            service.updateDescricao(item, "A luz de casa não está funcionando");
        });
    }

    @Test
    void shouldUpdateStatus() throws DescricaoException, StatusException, RecordFoundException {
        Item item = service.findByStatus("ABERTO");
        service.updateStatus(item, "FECHADO");
        assertEquals("FECHADO", item.getStatus());
    }

    @Test
    void shouldNotUpdateStatus() {
        Item item = service.findByStatus("ABERTO");
        Assertions.assertThrows(StatusException.class, () -> {
            service.updateStatus(item, "!@#");
        });
    }

    /* PROCURA REGISTROS */
    @Test
    void shouldFindStatusAberto() {
        assertEquals(1, service.findAllByStatus("ABERTO").size());
    }

    @Test
    void shouldFindStatusFechado() {
        assertEquals(1, service.findAllByStatus("FECHADO").size());
    }

    @Test
    void shouldFindStatusInvalid() {
        assertEquals(0, service.findAllByStatus(" ").size());
    }

    @Test
    void shouldFindStatusSpecialCharacteres() {
        assertEquals(0, service.findAllByStatus("!@#").size());
    }

    @Test
    void shouldFindDescricao() {
        assertEquals(1, service.findAllByDescriptionContaining("luz").size());
    }

    @Test
    void shouldNotFoundDescricao() {
        assertEquals(0, service.findAllByDescriptionContaining("cano").size());
    }

    @Test
    void shouldNotFoundDescricaoSpecialCharacteres(){ assertEquals(0, service.findAllByDescriptionContaining("!@#").size()); }

    @Test
    void shouldNotFoundDescricaoEmpty(){ assertEquals(2, service.findAllByDescriptionContaining(" ").size()); }

    @Test
    void shouldFindByCpf() throws DescricaoException, StatusException, CpfException, RecordFoundException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        List<Item> itensCliente = service.find(servico);
        assertEquals(2, itensCliente.size());

        envItem.addMoreItem();
        cliente = clienteRepository.findByCpf("15168201008");
        servico = servicoRepository.findByCliente(cliente);
        itensCliente = service.find(servico);
        assertEquals(1, itensCliente.size());
    }

    /* REMOVE REGISTROS */
    @Test
    void shouldDeleteItemDescricao() throws RecordNotFoundException, DescricaoException, StatusException, RecordFoundException {
        envItem.addItem();
        assertEquals(3, service.find().size());
        service.deleteByDescricao("A luz de casa não está funcionando");
        assertEquals(2, service.find().size());
    }

    @Test
    void shouldDeleteItemDescricaoNotExist() throws DescricaoException, StatusException, RecordFoundException {
        envItem.addItem();
        assertEquals(3, service.find().size());
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            service.deleteByDescricao("O vidro da minha casa está trincado!");
        });
    }

    @Test
    void shouldDeleteItemStatusOpen() throws RecordNotFoundException{
        service.deleteByStatus("ABERTO");
        assertEquals(1, service.find().size());
    }

    @Test
    void shouldDeleteItemStatusClose() throws RecordNotFoundException{
        service.deleteByStatus("FECHADO");
        assertEquals(1, service.find().size());
    }

    @Test
    void shouldDeleteItemStatusInvalid() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            service.deleteByStatus("Improcedente");
        });
    }
}
