package com.example.demo.domain._environments;

import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.DescricaoException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.StatusException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.repositories.ServicoRepository;
import com.example.demo.domain.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvItem {
    private String statusAberto = "ABERTO";

    @Autowired
    private EnvServico envServico;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private final ItemService itemService;

    @Autowired
    public EnvItem(ItemService itemService){
        this.itemService = itemService;
    }

    public void init() throws CpfException, RecordFoundException, DescricaoException, StatusException {
        envServico.init();
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        itemService.save("A luz de casa não está funcionando", "FECHADO", servico);
        itemService.save("Tem uma infiltração na minha nova casa!", statusAberto, servico);
    }

    public void addItem() throws DescricaoException, StatusException, RecordFoundException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        itemService.save("A torneira está empanada!", statusAberto, servico);
    }

    public void addMoreItem() throws DescricaoException, StatusException, RecordFoundException {
        Cliente cliente = clienteRepository.findByCpf("15168201008");
        Servico servico = servicoRepository.findByCliente(cliente);
        itemService.save("A parede da sala está com uma bolha na parede!", statusAberto, servico);
    }

}
