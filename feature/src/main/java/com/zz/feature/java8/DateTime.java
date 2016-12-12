package com.zz.feature.java8;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTime {

    public void show() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        localDateTime.plusHours(1);

    }

    public void format() {
        LocalDateTime ldf = LocalDateTime.of(2016, 1, 10, 10, 2, 3);
        DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldf);
        LocalDateTime.parse("2016-10-10 10:10:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void duration() {
        Duration elapsed = Duration.between(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        elapsed.toHours();
        elapsed.getSeconds();

        Period period = Period.between(LocalDate.now().plusDays(-1), LocalDate.now());
        period.getDays();
    }
}
