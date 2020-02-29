package com.example.demo.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //Para corrigir CODE SMELL de necessidade de construtor privado
public final class Data {
    public static boolean dateValidator(String dateToValidate){
        return containsDayMonthYear(dateToValidate) && validateMonth(dateToValidate);
    }

    private static boolean containsDayMonthYear(String date) {
        if(date == null)
            return false;
        String[] parseDate = date.split("/");
        return parseDate.length == 3;
    }

    private static boolean monthContains30Days(int day) {
        return day > 0 && day < 31;
    }

    private static boolean monthContains31Days(int day) {
        return day > 0 && day < 32;
    }

    private static boolean validateYear(int year) {
        return year > 1970;
    }

    private static boolean dayOfYearBissextus(int day, int year) {
        if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
            return day > 0 && day < 30;
        }
        return day > 0 && day < 29;
    }

    private static boolean validateMonth(String date) {
        String[] parseDate = date.split("/");
        int day = Integer.parseInt(parseDate[0]);
        int month = Integer.parseInt(parseDate[1]);
        int year = Integer.parseInt(parseDate[2]);
        if(validateYear(year)) {
            switch(month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    return monthContains31Days(day);
                case 4:
                case 6:
                case 9:
                case 11:
                    return monthContains30Days(day);
                case 2:
                    return dayOfYearBissextus(day, year);
                default:
                    break;
            }
        }
        return false;
    }
}

