package com.example.demo.domain._environments;

import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvServico {
    @Autowired
    private final ServicoService servicoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnvCliente envCliente;

    @Autowired
    public EnvServico(ServicoService servicoService){
        this.servicoService = servicoService;
    }

    public void init() throws CpfException, RecordFoundException {
        envCliente.init();
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        servicoService.save(cliente);
    }

    public void addMoreServicoToCliente() throws CpfException, RecordFoundException {
        envCliente.addOneMoreCliente();
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        servicoService.save(cliente);
    }

}
