package com.example.demo.domain;

import com.example.demo.config.FunctionalTest;
import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.domain._environments.EnvCliente;
import com.example.demo.domain._environments.EnvServico;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.repositories.ServicoRepository;
import com.example.demo.domain.services.ClienteService;
import com.example.demo.domain.services.ServicoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@FunctionalTest
public class ServicoTest {
    @Autowired
    private ServicoService service;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnvServico envServico;

    @Autowired
    private EnvCliente envCliente;

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() throws RecordFoundException, CpfException {
        envServico.init();
    }

    /* VERIFICA REGISTROS */
    @Test
    void checkIfHasOneServico() {
        assertEquals(1, service.find().size());
    }

    @Test
    void checkIfClienteHasTwoServico() throws RecordFoundException, CpfException {
        envServico.addMoreServicoToCliente();
        assertEquals(2, service.find().size());
    }

    /* ATUALIZA REGISTROS */
    @Test
    void shouldUpdateCliente() throws CpfException, RecordFoundException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = service.find(cliente);
        envCliente.addOneMoreCliente();

        Cliente novoCliente = clienteRepository.findByCpf("58130802015");
        service.update(servico, novoCliente);
        assertEquals(1, service.find().size());
        assertEquals("58130802015", service.find(novoCliente).getCliente().getCpf());
    }

    /* PROCURA REGISTROS */
    @Test
    void shouldNotFoundServicoClienteNull() {
        Cliente cliente = clienteService.find(null);
        assertNull(service.find(cliente));
    }

    @Test
    void shouldNotFoundServicoClienteSpecialCharacteres() {
        Cliente cliente = clienteService.find("!@#");
        assertNull(service.find(cliente));
    }

    @Test
    void shouldNotFoundServicoCliente() throws CpfException, RecordFoundException {
        clienteService.save("31036130088");
        Cliente cliente = clienteService.find("31036130088");
        assertNull(service.find(cliente));
    }

    @Test
    void shouldFoundServicoCliente() {
        Cliente cliente = clienteService.find("77755491064");
        assertNotNull(service.find(cliente));
    }

    /* REMOVE REGISTROS */
    @Test
    void shouldDeleteOneServicoCliente() throws RecordFoundException, CpfException, RecordNotFoundException {
        envServico.addMoreServicoToCliente();
        assertEquals(2, service.find().size());
        Cliente cliente = clienteService.find("77755491064");
        service.delete(cliente);
        assertEquals(1, service.find().size());
    }

    @Test
    void shouldNotDeleteOneServicoCliente() throws RecordFoundException, CpfException {
        envServico.addMoreServicoToCliente();
        assertEquals(2, service.find().size());
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            Cliente cliente = clienteService.find("77777777777");
            service.delete(cliente);
        });
    }
}
