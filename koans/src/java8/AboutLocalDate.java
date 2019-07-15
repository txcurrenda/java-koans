package java8;

import com.sandwich.koan.Koan;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;
import static com.sandwich.util.Assert.assertTrue;

public class AboutLocalDate {

    /**
     * java.util.Date and java.util.Calendar have not been replaced, but a new
     * package (java.time.*) has been introduced to give us more precise classes
     * when programming with dates and times.
     *
     * First, let's look at dates. Do you care about both the date and the time
     * (like a blog posted 3 minutes ago)?  Or do you only care about the date
     * (like how taxes are due at the end of a day) or just the time (like a
     * repeating appointment at 11:00am)?  There are now different classes to
     * represent each of these concepts.
     *
     * The biggest change is that the new classes are immutable!
     *
     * This test will compare the calendar API to the LocalTime and LocalDate
     * API. There is a LocalDate.now() method but that makes writing
     * tests for this koan a lot harder to code for.
     */
    @Koan
    public void _1_dateReplacedWithLocals() {

        /*
         * Here's the equivalent using LocalDate.  Note that a LocalDate is not
         * in any time zone -- hence "local".
         */
        LocalDate groundhogDay = LocalDate.of(2016, Month.FEBRUARY, 2);
        int dayOfYear = groundhogDay.get(ChronoField.DAY_OF_YEAR);

        assertEquals(dayOfYear, 33);

        /*
         * Here's the equivalent with LocalTime.  Note that LocalTime has no
         * date associated with it.  It just represents a time.
         *
         * Notice that unlike Calendar, LocalTime has a nice API using actual
         * methods, rather than a single "get" method where you pass constants.
         */
        LocalTime time = LocalTime.of(2, 30, 45);
        assertEquals(time.getHour(), 2);
        assertEquals(time.getMinute(), 30);
        assertEquals(time.getSecond(), 45);
    }

    /**
     * Doing "arithmetic" with dates is also a lot easier now! Imagine we know
     * when Spring Break begins, and we know that it's 7 days long. We can
     * quickly find out when it ends.
     *
     * There are really simple minus and add methods, but you will need to
     * remember that we are dealing with immutable objects. So when you perform
     * arithmetic, you will need to capture the returned value, since the
     * original value will not be modified.
     */
    @Koan
    public void _3_math() {

        LocalDate springBreakStart = LocalDate.of(2016, Month.MARCH, 12);
        LocalDate springBreakEnd = springBreakStart.plusDays(7);

        assertEquals(springBreakStart.getDayOfMonth(), 12);
        assertEquals(springBreakEnd.getDayOfMonth(), 19);
    }

    /**
     * One of the most common things we do as humans is create recurring events.
     * For example, if you have a payroll system that pays the employees every
     * two weeks, creating a calendar of pay days can be tedious. Java 8 has
     * introduced something called Periods that represent a symbolic chunk of
     * time in days, weeks or months.
     */
    @Koan
    public void _4_periods() {

        Period quarter = Period.ofMonths(3);
        LocalDate quarterlyMeeting = LocalDate.of(2016, Month.FEBRUARY, 2);
        LocalDate nextMeeting = quarterlyMeeting.plus(quarter);

        assertEquals(nextMeeting.getDayOfMonth(), 2);
    }

    /**
     * Sometimes we want to know how long it has been between two time stamps.
     * Caching and analytics are two prime candidates that will love this new
     * class called {@link Duration}. You can create a duration given two
     * dates or times.
     *
     * The difference between a Period and a Duration is that Duration is a
     * concrete, specific amount of time, accurate down to the nanosecond.  A
     * Period, on the other hand, represents a symbolic, calendar-based time
     * period (days, weeks, months, or years).  February 1 to March 1 is always
     * a Period of 1 month, but the Duration is different during leap years.
     */
    @Koan
    public void _5_duration() {

        Period oneYearPeriod = Period.ofYears(1);
        Duration oneYearDuration = Duration.ofDays(365);

        LocalDateTime groundhogDay2016 = LocalDateTime.of(2016, Month.FEBRUARY, 2, 1, 0); // 2-2-2016 1:00am
        LocalDateTime groundhogDay2017 = groundhogDay2016.plus(oneYearPeriod);
        LocalDateTime notGroundhogDay2017 = groundhogDay2016.plus(oneYearDuration);
        Duration leapYearDuration = Duration.between(groundhogDay2016, groundhogDay2017);

        assertEquals(groundhogDay2017.getDayOfMonth(), 2);
        assertEquals(notGroundhogDay2017.getDayOfMonth(), 1);

        long leapYearDays = leapYearDuration.toDays();
        assertEquals(leapYearDays, 366L);
        assertTrue(leapYearDays != oneYearDuration.toDays());
    }

    /**
     * One of the most common things that we do with the old Dates is to show
     * them to people.  Happily, we can format our new date classes in a
     * similar way.
     */
    @Koan
    public void _6_formatting() {

        /**
         * Notice no "new SimpleDateFormat(pattern)".  That's because we're
         * formatting LocalDateTimes, not Dates.
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        LocalDateTime july4th = LocalDateTime.of(2016, Month.JULY, 4, 2, 33); // 7-4-2016 2:33

        assertEquals(formatter.format(july4th), "07/04/2016 02:33");
    }

    @Koan
    public void localTime() {
        LocalTime t1 = LocalTime.of(7, 30);
        assertEquals(t1, LocalTime.parse("07:30"));
    }

    @Koan
    public void localTimeMinus() {
        LocalTime t1 = LocalTime.parse("10:30");
        LocalTime t2 = t1.minus(2, ChronoUnit.HOURS);
        assertEquals(t2, LocalTime.parse("08:30"));
    }
}
