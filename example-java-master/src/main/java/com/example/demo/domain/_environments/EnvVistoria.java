package com.example.demo.domain._environments;

import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.DateException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.repositories.ServicoRepository;
import com.example.demo.domain.services.VistoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;

@Component
public class EnvVistoria {
    @Autowired
    private EnvServico envServico;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private final VistoriaService vistoriaService;

    @Autowired
    public EnvVistoria(VistoriaService vistoriaService){
        this.vistoriaService = vistoriaService;
    }

    public void init() throws CpfException, RecordFoundException, ParseException, DateException {
        envServico.init();
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        vistoriaService.save("11/02/2020", servico);
    }

    public void addVistoria() throws CpfException, RecordFoundException, ParseException, DateException {
        envServico.addMoreServicoToCliente();
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        vistoriaService.save("12/02/2020", servico);
    }

    public void addMuchVistoria() throws ParseException, DateException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        vistoriaService.save("01/01/2020", servico);
        vistoriaService.save("02/03/2020", servico);
        vistoriaService.save("03/04/2020", servico);
        vistoriaService.save("04/05/2020", servico);
        vistoriaService.save("05/06/2020", servico);
        vistoriaService.save("06/07/2020", servico);
        vistoriaService.save("07/08/2020", servico);
        vistoriaService.save("08/09/2020", servico);
        vistoriaService.save("09/10/2020", servico);
        vistoriaService.save("10/11/2020", servico);
        vistoriaService.save("11/12/2020", servico);
    }



}
