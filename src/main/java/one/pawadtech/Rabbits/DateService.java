package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

@Service
public class DateService {

    private static final Logger logger = LoggerFactory.getLogger(DateService.class);

    public Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EAT"));

        try {
            logger.info("Parsing date string: {}", dateString);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("Failed to parse date string: {}", dateString, e);
            return null;
        }
    }

    public Duration durationFrom(Date pastDate) {
        Date dateStr = parseDateString(pastDate.toString());
        if (dateStr == null) {
            logger.warn("Failed to parse past date: {}", pastDate);
            return Duration.ZERO;
        }
        Instant date = dateStr.toInstant();
        logger.info("Calculating duration from: {}", date);
        System.out.println("DOB ::: " + date + "  Now::: " + Instant.now());
        return Duration.between(date, Instant.now());
    }

    public Duration StrdurationFrom(String pastDate) {
        Date dateStr = parseDateString(pastDate);
        if (dateStr == null) {
            logger.warn("Failed to parse past date string: {}", pastDate);
            return Duration.ZERO;
        }
        Instant date = dateStr.toInstant();
        logger.info("Calculating duration from string date: {}", date);
        System.out.println("DOB ::: " + date + "  Now::: " + Instant.now());
        return Duration.between(date, Instant.now());
    }

    public String getTodayDateString() {
        Date currentDate = new Date();
        logger.info("Getting today's date string for date: {}", currentDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Today's Date ::: " + dateFormat.format(currentDate));
        return dateFormat.format(currentDate);
    }

    public String concatTodayDateString() {
        Date currentDate = new Date();
        logger.info("Concatenating today's date string for date: {}", currentDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String[] re = dateFormat.format(currentDate).split("/");
        return re[0].trim() + re[1].trim() + re[2].trim();
    }

    public Date convertDateStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            logger.info("Converting date string to Date object: {}", dateString);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("Failed to convert date string: {}", dateString, e);
            return null;
        }
    }
}
