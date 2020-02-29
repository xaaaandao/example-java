package com.example.demo.domain;

import com.example.demo.config.FunctionalTest;
import com.example.demo.config.exceptions.CpfException;
import com.example.demo.config.exceptions.DateException;
import com.example.demo.config.exceptions.RecordFoundException;
import com.example.demo.config.exceptions.RecordNotFoundException;
import com.example.demo.domain._environments.EnvServico;
import com.example.demo.domain._environments.EnvVistoria;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Servico;
import com.example.demo.domain.model.Vistoria;
import com.example.demo.domain.repositories.ClienteRepository;
import com.example.demo.domain.repositories.ServicoRepository;
import com.example.demo.domain.services.VistoriaService;
import com.example.demo.domain.utils.CnpjCpf;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.junit.Assert.assertEquals;

@FunctionalTest
public class VistoriaTest {

    @Autowired
    private VistoriaService service;

    @Autowired
    private EnvVistoria envVistoria;

    @Autowired
    private EnvServico envServico;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @BeforeEach
    void setup() throws RecordFoundException, CpfException, ParseException, DateException {
        envVistoria.init();
    }

    /* VERIFICA REGISTROS */
    @Test
    void checkIfHasOneVistoria() {
        assertEquals(1, service.find().size());
    }

    @Test
    void checkIfHasElevenVistoria() throws ParseException, DateException {
        envVistoria.addMuchVistoria();
        assertEquals(12, service.find().size());
    }

    @Test
    void checkIfDateIsValidOneWeek(){
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Vistoria vistoria = service.find(servico);
        Date date = vistoria.getData();
        LocalDate localDate = getDate(date);
        localDate = addDay(localDate, 7);
        String dataFormatada = dataFormatada(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        assertEquals("18/02/2020", dataFormatada);
    }

    @Test
    void checkIfDateIsValidOneMonth(){
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Vistoria vistoria = service.find(servico);
        Date date = vistoria.getData();
        LocalDate localDate = getDate(date);
        localDate = addMonth(localDate, 1);
        String dataFormatada = dataFormatada(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        assertEquals("11/03/2020", dataFormatada);
    }

    @Test
    void checkIfDateIsValidOneYear(){
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Vistoria vistoria = service.find(servico);
        Date date = vistoria.getData();
        LocalDate localDate = getDate(date);
        localDate = addYear(localDate, 1);
        String dataFormatada = dataFormatada(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        assertEquals("11/02/2021", dataFormatada);
    }

    @Test
    void checkIfClienteHasCpfValid() {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Vistoria vistoria = service.find(servico);
        Assert.assertTrue(CnpjCpf.validate(vistoria.getServico().getCliente().getCpf()));
    }

    /* ADICIONA REGISTROS */

    @Test
    void shouldAddOneVistoria() throws RecordFoundException, CpfException, ParseException, DateException {
        envVistoria.addVistoria();
        assertEquals(2, service.find().size());
    }

    @Test
    void shouldNotAddVistoriaDateNull() {
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save(null, servico);
        });
    }

    @Test
    void shouldNotAddVistoriaDateOnlySpecialCharacters() {
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("!@#", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaDateInvalid() throws RecordFoundException, CpfException {
        envServico.addMoreServicoToCliente();
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("30/21/2020", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaDateFormatInvalid() throws RecordFoundException, CpfException {
        envServico.addMoreServicoToCliente();
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("30-01-2020", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaDateBlank() throws RecordFoundException, CpfException {
        envServico.addMoreServicoToCliente();
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaDateInvalidFormatInvalid() throws RecordFoundException, CpfException {
        envServico.addMoreServicoToCliente();
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("30-30-2020", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaYearInvalid() throws RecordFoundException, CpfException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("01/01/1969", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaMonth30Days() throws RecordFoundException, CpfException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("32/04/2020", servico);
        });
    }

    @Test
    void shouldNotAddVistoriaMonth31Days() throws RecordFoundException, CpfException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("33/01/2020", servico);
        });
    }

    @Test
    void shouldAddVistoriaYearBissextus() throws ParseException, DateException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        service.save("29/02/2020", servico);
        assertEquals(2, service.find().size());
    }

    @Test
    void shouldAddVistoriaYearBissextusDayInvalid() throws ParseException, DateException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("43/02/2020", servico);
        });
    }

    @Test
    void shouldAddVistoriaNotYearBissextus() throws ParseException, DateException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        service.save("28/02/2019", servico);
        assertEquals(2, service.find().size());
    }

    @Test
    void shouldAddVistoriaNotYearBissextusDayInvalid() throws ParseException, DateException {
        Cliente cliente = clienteRepository.findByCpf("77755491064");
        Servico servico = servicoRepository.findByCliente(cliente);
        Assertions.assertThrows(DateException.class, () -> {
            service.save("41/02/2019", servico);
        });
    }


    /* ATUALIZA REGISTROS */
    @Test
    void shouldUpdateVistoria() throws ParseException, DateException {
        String data = "11/02/2020";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(data);
        Vistoria vistoria = service.find(date);
        String novaData = "17/02/2020";
        date = sdf.parse(novaData);
        service.update(vistoria, novaData);
        assertEquals(vistoria.getData().toString(), date.toString());
    }

    @Test
    void shouldNotUpdateVistoria() throws ParseException {
        String data = "11/02/2020";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(data);
        Vistoria vistoria = service.find(date);
        Assertions.assertThrows(DateException.class, () -> {
            service.update(vistoria, "31/31/2020");
        });
    }

    /* PROCURA REGISTROS */

    @Test
    void shouldFindVistoriaByDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("11/02/2020");
        assertEquals(1, service.findAllByData(date).size());
    }

    @Test
    void shouldNotFoundVistoriaByDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("20/02/2020");
        assertEquals(0, service.findAllByData(date).size());
    }

    @Test
    void shouldNotFoundVistoriaByDateNull() {
        Date date = null;
        assertEquals(0, service.findAllByData(date).size());
    }

    @Test
    void shouldNotFoundVistoriaByDateSpecialCharacteres() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Assertions.assertThrows(ParseException.class, () -> {
            Date date = sdf.parse("!!/@@/####");
            assertEquals(0, service.findAllByData(date).size());
        });
    }

    @Test
    void shouldNotFoundVistoriaByDateEmpty() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Assertions.assertThrows(ParseException.class, () -> {
            Date date = sdf.parse("  /  /    ");
            assertEquals(0, service.findAllByData(date).size());
        });
    }

    /* REMOVE REGISTROS */

    @Test
    void shouldDeleteVistoria() throws RecordFoundException, CpfException, RecordNotFoundException, ParseException, DateException {
        envVistoria.addVistoria();
        assertEquals(2, service.find().size());
        Cliente cliente = clienteRepository.findByCpf("58130802015");
        Servico servico = servicoRepository.findByCliente(cliente);
        service.delete(servico);
        assertEquals(1, service.find().size());
    }

    @Test
    void shouldDeleteVistoriaNotExist() throws RecordFoundException, CpfException, ParseException, DateException {
        envVistoria.addVistoria();
        assertEquals(2, service.find().size());
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            Cliente cliente = clienteRepository.findByCpf("77777777777");
            Servico servico = servicoRepository.findByCliente(cliente);
            service.delete(servico);
        });
    }

    @Test
    void shouldDeleteVistoriaByData() throws RecordNotFoundException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("11/02/2020");
        service.deleteByDate(date);
        assertEquals(0, service.find().size());
    }

    @Test
    void shouldDeleteVistoriaByDataInvalid() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("20/02/2020");
        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            service.deleteByDate(date);
        });
    }

    public static LocalDate getDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate addDay(LocalDate date, int nroDays){
        return date.plusDays(nroDays);
    }

    public static LocalDate addMonth(LocalDate date, int nroMonth){
        return date.plusMonths(nroMonth);
    }

    public static LocalDate addYear(LocalDate date, int nroYear){
        return date.plusYears(nroYear);
    }

    public static String dataFormatada(int day, int month, int year){
        if (month < 10) {
            return String.valueOf(day) + "/0" + String.valueOf(month) + "/" + String.valueOf(year);
        }
        return String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
    }
}
