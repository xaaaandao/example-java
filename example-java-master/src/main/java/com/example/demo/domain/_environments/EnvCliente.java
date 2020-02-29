package com.example.demo.domain._environments;

import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.domain.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvCliente {
    private final ClienteService clienteService;

    @Autowired
    public EnvCliente(ClienteService clienteService) {
            this.clienteService = clienteService;
        }

    public void init() throws RecordFoundException, CpfException {
        clienteService.save("77755491064");
        clienteService.save("15168201008");
    }

    public void addOneMoreCliente() throws RecordFoundException, CpfException {
        clienteService.save("58130802015");
    }

    public void deleteOneCliente() throws RecordNotFoundException {
        clienteService.delete("77755491064");
    }

}
