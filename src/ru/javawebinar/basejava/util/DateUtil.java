package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String dateToString(LocalDate date) {
        return (date == null) ? "" : date.equals(NOW) ? "Сейчас" : date.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate stringToDate(String date) {
        if (date==null||date.trim().length()==0 || "Сейчас".equals(date)) return NOW;
        YearMonth yearMonth = YearMonth.parse(date, DATE_TIME_FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

    public static String fullDate(LocalDate startDate,LocalDate endDate) {
        return dateToString(startDate) + " - " + dateToString(endDate);
    }
}