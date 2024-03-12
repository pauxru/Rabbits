package one.pawadtech.Rabbits;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

@Service
public class DateService {
    public Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EAT"));

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Duration durationFrom(Date pastDate){
        //LocalDate parsedDate = parseDateString(dateStringFromDB);
        Date dateStr = parseDateString(pastDate.toString());
        Instant date = dateStr.toInstant();
        System.out.println("DOB ::: "+date +"  Now::: "+Instant.now());
        // Calculate the duration between the two date-time values
        return Duration.between(date, Instant.now());
    }

    public String getTodayDateString() {
        // Create a Date object representing the current date
        Date currentDate = new Date();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Format the Date object and return the result
        return dateFormat.format(currentDate);
    }

    public String concatTodayDateString() {
        // Create a Date object representing the current date
        Date currentDate = new Date();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Format the Date object and return the result
        String[] re = dateFormat.format(currentDate).split("/");
        return re[0].trim() + re[1].trim() + re[2].trim();


    }

    public Date convertDateStringToDate(String dateString) {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Parse the input String to a Date object
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }
}
