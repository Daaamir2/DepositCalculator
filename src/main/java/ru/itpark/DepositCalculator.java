package ru.itpark;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DepositCalculator {
    public long calculate(double depositAmount, int termPlacementInMonths, double percent, String dateBegin) throws ParseException {
        int constantPercentToInt = 1_000;
        int millisInDay = 86_400_000;
        double result = 0;
        int daysInLeapYear = 366;
        int daysInNonLeapYear = 365;
        double percentToInt = percent * constantPercentToInt;

        Calendar dateOrigin = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        dateOrigin.setTime(sdf.parse(dateBegin));
        Calendar dateDepositEnd = GregorianCalendar.getInstance();
        dateDepositEnd.setTime(sdf.parse(dateBegin));

        dateDepositEnd.add(Calendar.MONTH, termPlacementInMonths);

        for (int i = dateOrigin.get(Calendar.YEAR); i <= dateDepositEnd.get(Calendar.YEAR); i++){
            GregorianCalendar beginDayPeriod = new GregorianCalendar();
            GregorianCalendar lastDayPeriod = new GregorianCalendar();

            if (i > dateOrigin.get(Calendar.YEAR)){
                beginDayPeriod.set(Calendar.YEAR, i);
                beginDayPeriod.set(Calendar.MONTH, Calendar.JANUARY);
                beginDayPeriod.set(Calendar.DAY_OF_MONTH, 1);

                if (i != dateDepositEnd.get(Calendar.YEAR)){
                    lastDayPeriod.set(Calendar.YEAR, i + 1);
                    lastDayPeriod.set(Calendar.MONTH, Calendar.JANUARY);
                    lastDayPeriod.set(Calendar.DAY_OF_MONTH, 1);
                } else {
                    lastDayPeriod.set(Calendar.YEAR, i);
                    lastDayPeriod.set(Calendar.MONTH, dateDepositEnd.get(Calendar.MONTH));
                    lastDayPeriod.set(Calendar.DAY_OF_MONTH, dateDepositEnd.get(Calendar.DAY_OF_MONTH));
                }
            } else {
                lastDayPeriod.set(Calendar.YEAR, i);
                lastDayPeriod.set(Calendar.MONTH, Calendar.DECEMBER);
                lastDayPeriod.set(Calendar.DAY_OF_MONTH, 31);

                beginDayPeriod.set(Calendar.YEAR, i);
                beginDayPeriod.set(Calendar.MONTH, dateOrigin.get(Calendar.MONTH));
                beginDayPeriod.set(Calendar.DAY_OF_MONTH, (dateOrigin.get(Calendar.DAY_OF_MONTH) - 1));
            }

            long restOfDay = (lastDayPeriod.getTimeInMillis() - beginDayPeriod.getTimeInMillis()) / millisInDay;
            double resultWithoutYear = depositAmount * percentToInt * restOfDay / 100;

            if (new GregorianCalendar().isLeapYear(i)) {
                result += resultWithoutYear / daysInLeapYear;
            } else {
                result += resultWithoutYear / daysInNonLeapYear;
            }
        }

        return (long) (result / constantPercentToInt  + depositAmount);
    }
}
