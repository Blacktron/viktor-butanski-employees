package interview.butanski.viktor.solutions.sirma.employeespairfx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String[] dateSeparators = {"/", "-", ".", " "};
    private static final String NULL_STR = "null";

    private static final String DMY_FORMAT = "dd{separator}MM{separator}yyyy";
    private static final String YMD_FORMAT = "yyyy{separator}MM{separator}dd";
    private static final String MDY_FORMAT = "M{separator}d{separator}y";
    private static final String MY_FORMAT = "M{separator}y";

    private static final String YMD_REGEXP = "\\d{4}{separator}\\d{2}{separator}\\d{2}";
    private static final String DMY_REGEXP = "\\d{2}{separator}\\d{2}{separator}\\d{4}";
    private static final String MDY_REGEXP = "\\d{1,2}{separator}\\d{1,2}{separator}\\d{4}";
    private static final String MY_REGEXP = "\\d{1,2}{separator}\\d{2}(?:\\d{2})?";

    public static Date convertStringToDate(String dateStr) {
        if (dateStr.equalsIgnoreCase(NULL_STR)) {
            return new Date();
        }

        Date date;
        String dateFormat = getDateFormat(dateStr);
        if (dateFormat == null) {
            throw new IllegalArgumentException("Date is not in an accepted format " + dateStr);
        }

        for (String sep : dateSeparators) {
            String actualDateFormat = getPatternForSeparator(dateFormat, sep);

            date = parseDate(dateStr, actualDateFormat);
            if (date != null) {
                return date;
            }
        }

        return null;
    }

    private static String getDateFormat(String date) {
        for (String separator : dateSeparators) {
            String ymdPattern = getPatternForSeparator(YMD_REGEXP, separator);
            String dmyPattern = getPatternForSeparator(DMY_REGEXP, separator);
            String mdyPattern = getPatternForSeparator(MDY_REGEXP, separator);
            String myPattern = getPatternForSeparator(MY_REGEXP, separator);

            if (date.matches(ymdPattern)) {
                return YMD_FORMAT;
            }

            if (date.matches(dmyPattern)) {
                return DMY_FORMAT;
            }

            if (date.matches(mdyPattern)) {
                return MDY_FORMAT;
            }

            if (date.matches(myPattern)) {
                return MY_FORMAT;
            }
        }

        return null;
    }

    private static String getPatternForSeparator(String dateFormat, String separator) {
        return dateFormat.replace("{separator}", separator);
    }

    private static Date parseDate(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException ignored) {}

        return null;
    }
}
