package com.vonblum.vicabilling.app.service;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateTool {

    public static Date convertLocalDateToDate(LocalDate localDate) {
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String getDefaultLocaleDate(Date date) {
        Locale locale = new Locale("ca", "ES");
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return format.format(date);
    }

}
