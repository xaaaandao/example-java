package test;

import main.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void shouldExistsACalculator() {
        assertTrue(calculator instanceof Calculator);
    }

    @Test
    void shouldResultZeroOfSum() {
        calculator.soma(0);
        calculator.soma(0);
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultOfSum() {
        calculator.soma(3);
        calculator.soma(2);
        assertEquals(5, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultTwoDigitsOfSum() {
        calculator.soma(10);
        calculator.soma(90);
        assertEquals(100, calculator.getResultado(), 0);
    }

    @Test
    void shouldSumAndClearScreen() {
        calculator.soma(10);
        calculator.soma(5);
        assertEquals(15, calculator.getResultado(), 0);

        calculator.limpaTela();
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultZeroOfSub() {
        calculator.subtracao(0);
        calculator.subtracao(0);
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void subtraendoMaiorQueMinuendo() {
        calculator.subtracao(2);
        calculator.subtracao(3);
        assertEquals(-1, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultOfSub() {
        calculator.subtracao(3);
        calculator.subtracao(2);
        assertEquals(1, calculator.getResultado(), 0);
    }

    @Test
    void shouldSubAndClearScreen() {
        calculator.subtracao(10);
        calculator.subtracao(5);
        assertEquals(5, calculator.getResultado(), 0);

        calculator.limpaTela();
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultZeroOfMult() {
        calculator.multiplicacao(0);
        calculator.multiplicacao(0);
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultOfMult() {
        calculator.multiplicacao(3);
        calculator.multiplicacao(2);
        assertEquals(6, calculator.getResultado(), 0);
    }

    @Test
    void multiplicandoMaiorMultiplicador() {
        calculator.multiplicacao(2);
        calculator.multiplicacao(10);
        assertEquals(20, calculator.getResultado(), 0);
    }

    @Test
    void shouldMultAndClearScreen() {
        calculator.multiplicacao(9);
        calculator.multiplicacao(5);
        assertEquals(45, calculator.getResultado(), 0);

        calculator.limpaTela();
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultZeroOfDiv() {
        calculator.divisao(0);
        calculator.divisao(0);
        assertEquals(-1, calculator.getResultado(), 0);
    }

    @Test
    void dividedGreaterThanDivisor() {
        calculator.divisao(2);
        calculator.divisao(4);
        assertEquals(0.5, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultDoubleOfDiv() {
        calculator.divisao(3);
        calculator.divisao(2);
        assertEquals(1.5, calculator.getResultado(), 0);
    }

    @Test
    void shouldResultOfDiv() {
        calculator.divisao(6);
        calculator.divisao(2);
        assertEquals(3, calculator.getResultado(), 0);
    }

    @Test
    void shouldDivAndClearScreen() {
        calculator.divisao(10);
        calculator.divisao(5);
        assertEquals(2, calculator.getResultado(), 0);

        calculator.limpaTela();
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void shouldClearResult() {
        calculator.divisao(6);
        calculator.limpaTela();
        assertEquals(0, calculator.getResultado(), 0);
    }

    @Test
    void calculoComplexo() {
        calculator.divisao(6);
        calculator.divisao(2);
        assertEquals(3, calculator.getResultado(), 0);

        calculator.multiplicacao(10);
        assertEquals(30, calculator.getResultado(), 0);

        calculator.limpaTela();
        assertEquals(0, calculator.getResultado(), 0);

        calculator.soma(77);
        assertEquals(77, calculator.getResultado(), 0);

        calculator.subtracao(66);
        assertEquals(11, calculator.getResultado(), 0);
    }

}