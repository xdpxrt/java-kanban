package project.util;

import java.time.format.DateTimeFormatter;

public class TaskTimeFormatter {
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public final static String ZERO_DATE = "00.00.00 00:00";
}
