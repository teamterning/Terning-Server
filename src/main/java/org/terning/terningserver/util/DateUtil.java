package org.terning.terningserver.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {

    public static String convert(LocalDate deadline) {
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate currentDate = nowInKorea.toLocalDate();

        if (deadline.isEqual(currentDate)) {
            return "D-DAY";
        } else if (deadline.isBefore(currentDate)) {
            return "지원마감";
        } else {
            long daysUntilDeadline = currentDate.until(deadline).getDays();
            return "D-" + daysUntilDeadline;
        }
    }

    public static String convertDeadline(LocalDate deadline) {
        return deadline.getYear() + "년 "
                + deadline.getMonthValue() + "월 "
                + deadline.getDayOfYear() + "일";
    }
}
