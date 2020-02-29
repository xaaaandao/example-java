package com.example.demo.domain;

import com.example.demo.config.FunctionalTest;
import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.domain._environments.EnvCliente;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.services.ClienteService;
import com.example.demo.domain.utils.CnpjCpf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

@FunctionalTest
public class ClienteTest {
    @Autowired
    private ClienteService service;

    @Autowired
    private EnvCliente envCliente;

    @Autowired
    private ClienteRepository repository;

    @BeforeEach
    void setup() throws RecordFoundException, CpfException {
        envCliente.init();
    }

    /* VERIFICA REGISTRO */
    @Test
    void checkIfHasTwoClientes() {
        assertEquals(2, service.find().size());
    }

    @Test
    void shouldCpfHasInvalid() {
        Cliente clienteInvalid = Cliente.builder().cpf("77777777777").build();
        assertFalse(CnpjCpf.validate(clienteInvalid.getCpf()));
    }

    @Test
    void shouldCpfHasValid() {
        Cliente clienteInvalid = Cliente.builder().cpf("15168201008").build();
        assertTrue(CnpjCpf.validate(clienteInvalid.getCpf()));
    }

    /* ADICIONA REGISTRO */
    @Test
    void shouldAddOneCliente() throws RecordFoundException, CpfException {
        envCliente.addOneMoreCliente();
        assertEquals(3, service.find().size());
    }

    @Test
    void shouldNotAddSameCliente() throws RecordFoundException, CpfException {
        envCliente.addOneMoreCliente();
        assertEquals(3, service.find().size());
        Assertions.assertThrows(RecordFoundException.class, () -> {
            envCliente.addOneMoreCliente();
        });
    }

    @Test
    void shouldNotAddOneCliente() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save("77777777777");
        });
    }

    @Test
    void shouldNotAddOneClienteNull() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save(null);
        });
    }

    @Test
    void shouldNotAddOneClienteCpfIncomplete() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save("9685012903");
        });
    }

    @Test
    void shouldNotAddOneClienteCpfOnlyDigit() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save("         9");
        });
    }

    @Test
    void shouldNotAddOneClienteCpfOneMoreDigit() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save("968501290312");
        });
    }

    @Test
    void shouldNotAddOneClienteCPFEmpty() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save(" ");
        });
    }

    @Test
    void shouldNotAddOneClienteCPFSpecialCharacteres() {
        Assertions.assertThrows(CpfException.class, () -> {
            service.save("!@#");
        });
    }

    /* ATUALIZA REGISTRO */
    @Test
    void shouldUpdateCliente() throws RecordFoundException, CpfException {
        Cliente c = repository.findByCpf("77755491064");
        service.update(c, "48701793004");
        assertEquals("48701793004", c.getCpf());
    }

    @Test
    void shouldNotUpdateCliente() {
        Cliente c = repository.findByCpf("77755491064");
        Assertions.assertThrows(CpfException.class, () -> {
            service.update(c, "00000000000");
        });
    }

    @Test
    void shouldNotUpdateClienteExist() throws CpfException, RecordFoundException {
        Cliente c = repository.findByCpf("77755491064");
        envCliente.addOneMoreCliente();
        assertEquals(3, service.find().size());
        Assertions.assertThrows(RecordFoundException.class, () -> {
            service.update(c, "58130802015");
        });
    }

    /* PROCURA REGISTRO */
    @Test
    void shouldNotFoundCliente() {
        Cliente c = repository.findByCpf("99999999999");
        assertNull(c);
    }

    @Test
    void shouldNotFoundClienteCaracteres() {
        Cliente c = repository.findByCpf("abcdefghijkl");
        assertNull(c);
    }

    @Test
    void shouldFoundCliente() {
        Cliente c = repository.findByCpf("77755491064");
        assertNotNull(c);
    }

    @Test
    void shouldFoundClienteCpfOnlySpecialCharacteres() {
        Cliente c = repository.findByCpf("!@#");
        assertNull(c);
    }

    @Test
    void shouldFoundClienteCpfEmpty() {
        Cliente c = repository.findByCpf(" ");
        assertNull(c);
    }

    @Test
    void shouldFoundClienteNull() {
        Cliente c = repository.findByCpf(null);
        assertNull(c);
    }

    /* REMOVE REGISTRO */
    @Test
    void shouldDeleteOneCliente() throws RecordFoundException, RecordNotFoundException, CpfException {
        envCliente.addOneMoreCliente();
        assertEquals(3, service.find().size());
        envCliente.deleteOneCliente();
        assertEquals(2, service.find().size());
    }

    @Test
    void shouldNotDeleteCliente() {
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            service.delete("77777777777");
        });
    }
}
