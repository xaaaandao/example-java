package com.example.demo.domain.utils;

public final class CnpjCpf {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private CnpjCpf() {}

    public static boolean validate(String cnpjCpf) {

        if (cnpjCpf == null || cnpjCpf.trim().length() == 0 || sameNumbers(cnpjCpf))
            return false;

        if (cnpjCpf.length() == 11)
            return isValidCPF(cnpjCpf.trim());

        return false;
    }

    private static int calculateNumber(String str, int[] peso) {
        int sum = 0;
        for (int index = str.length() - 1, number; index >= 0; index--) {
            number = Integer.parseInt(str.substring(index,index+1));
            sum += number * peso[peso.length - str.length() + index];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    private static boolean isValidCPF(String cpf) {
        int number01 = calculateNumber(cpf.substring(0,9), pesoCPF);
        int number02 = calculateNumber(cpf.substring(0,9) + number01, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + number01 + number02);
    }

    private static boolean sameNumbers(String cpf) {
        String firstNumber = cpf.substring(0, 1);
        for (int i = 1; i < cpf.length(); i++) {
            String currentNumber = cpf.substring(i, i + 1);
            if (!firstNumber.equals(currentNumber))
                return false;
        }
        return true;
    }
}
