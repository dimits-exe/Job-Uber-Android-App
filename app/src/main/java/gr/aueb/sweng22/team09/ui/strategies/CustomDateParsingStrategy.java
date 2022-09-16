package gr.aueb.sweng22.team09.ui.strategies;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A strategy using Regex to extract the LocalDate fields from the string.
 *
 * @author Alex Mandelias
 */
public class CustomDateParsingStrategy implements IDateParsingStrategy {

    @Override
    @SuppressWarnings("ConstantConditions")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate parseDate(String localDate){
        LocalDate date = null;
        Pattern p = Pattern.compile("(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{4})");
        Matcher m = p.matcher(localDate);

        if (m.find()) {
            try{
                date = LocalDate.of(Integer.parseInt(m.group("year")),
                        Integer.parseInt(m.group("month")),
                        Integer.parseInt(m.group("day")));
            } catch (RuntimeException re) {
                return null;
            }
        }
        return date;
    }
}
