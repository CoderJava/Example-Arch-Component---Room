package com.ysn.examplearchcomponentroom.utils;

/**
 * Created by root on 09/07/17.
 */

public class ConverterDateLibrary {

    private String[] monthArr = new String[]{
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    };

    public String formatToString(int year, int month, int dayOfMonth) {
        String format = dayOfMonth + "-" + monthArr[month] + "-" + year;
        return format;
    }

}
