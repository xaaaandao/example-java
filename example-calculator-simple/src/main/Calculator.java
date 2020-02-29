package main;

public class Calculator {

    private double resultado = -1;

    public double getResultado() {
        return resultado;
    }

    public void soma(double valor) {
        if (resultado == -1)
            resultado = 0;
        resultado += valor;
    }

    public void subtracao(double valor) {
        if (valor >= 0 && resultado == -1) {
            resultado = 0;
            resultado = resultado - (valor * -1);
        } else
            resultado = resultado - valor;
    }


    public void multiplicacao(double valor) {
        if (resultado == -1)
            resultado = 1;
        resultado *= valor;
    }

    public void divisao(double valor) {
        if (resultado == -1)
            resultado = valor;
        else {
            if (valor == 0)
                resultado = -1;
            else
                resultado /= valor;
        }
    }
}
