package com.example.demo.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DescricaoStatus {
    private static final String NUMEROS = "-?\\d+(\\.\\d+)?";
    private static final String CARACTERES_ESPECIAIS = "!@#%¨&";

    public static boolean validateDescricao(String descricao){
        return (descricao != null) && getResult(descricao);
    }

    public static boolean validateStatus(String status){
        return (status != null) && getResult(status) && isOpenOrClose(status);
    }

    private static boolean getResult(String descricao) {
        return (!isOnlyNumber(descricao)) && getIsOnlySpecialChars(descricao);
    }

    private static boolean getIsOnlySpecialChars(String descricao) {
        return !isOnlySpecialChars(descricao);
    }

    private static boolean isOnlySpecialChars(String descricao) {
        return isValid(descricao, CARACTERES_ESPECIAIS);
    }

    private static boolean isValid(String descricao, String caracteresEspeciais) {
        return (descricao.isBlank()) || (descricao.matches("[" + caracteresEspeciais + "]+"));
    }

    private static boolean isOnlyNumber(String descricao) {
        return descricao.matches(NUMEROS);
    }

    private static boolean isOpenOrClose(String status) {
        return status.equalsIgnoreCase("ABERTO") || status.equalsIgnoreCase("FECHADO");
    }

}
