package org.terning.terningserver.common.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static String convert(LocalDate deadline) {
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate currentDate = nowInKorea.toLocalDate();

        if (deadline.isEqual(currentDate)) {
            return "D-DAY";
        } else if (deadline.isBefore(currentDate)) {
            return "지원마감";
        } else {
            long daysUntilDeadline = ChronoUnit.DAYS.between(currentDate, deadline);
            return "D-" + daysUntilDeadline;
        }
    }

    public static String convertDeadline(LocalDate deadline) {
        return deadline.getYear() + "년 "
                + deadline.getMonthValue() + "월 "
                + deadline.getDayOfMonth() + "일";
    }

}
