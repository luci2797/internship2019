import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {

            JSONObject a = (JSONObject) parser.parse(new FileReader(
                    "C:\\Users\\Luci\\Desktop\\input.json"));
            JSONObject employees = (JSONObject) a.get("employeeData");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date startDate = format.parse((String) employees.get("employmentStartDate"));
            Date endDate = format.parse((String) employees.get("employmentEndDate"));

            Day startDay = new Day(startDate);
            System.out.println(startDay.toString());
            Day endDay = new Day(endDate);
            System.out.println(endDay.toString());

            int holidayDays = 20;
            Long suspensionDays = 0L;

            System.out.println("holiday days:" + holidayDays);
            System.out.println(" ");

            List<Year> years = new ArrayList<>();
            int currentYear = startDay.getYear();
            String errors = "no errors";

            if(getDifferenceDays(startDate,endDate)>0){
                if(errors.equals("no errors")){errors = "incorrect employment interval";}
                else{errors+=" ,incorrect employment interval";}
            }

            JSONArray suspensions = (JSONArray) employees.get("suspensionPeriodList");
            for(Object o:suspensions){
                JSONObject suspension = (JSONObject) o;

                holidayDays = 20 + (currentYear - startDay.getYear());
                if(holidayDays >24){
                    holidayDays = 24;
                }

                Date start = format.parse((String) suspension.get("startDate"));
                Date end = format.parse((String) suspension.get("endDate"));
                Day suspStart = new Day(start);
                Day suspEnd = new Day(end);

                if(getDifferenceDays(start,end)>0){
                    if(errors.equals("no errors")){errors = "incorrect suspension interval";}
                    else{errors+=" ,incorrect suspension interval";}
                }

                if (suspStart.getYear()>currentYear){
                    Long workedDays = 365L-suspensionDays;
                    Long holidayDaysThisYear = (workedDays * holidayDays)/365;
                    Year y = new Year(currentYear,holidayDaysThisYear);
                    years.add(y);
                    currentYear = suspStart.getYear();

                }
                else {
                    suspensionDays += getDifferenceDays(start, end);
                }
                System.out.println("New Suspension");
                System.out.println("Start:" + start);
                System.out.println("End:" + end);
                System.out.println("Suspension Duration:" + getDifferenceDays(start,end) + " days");
                System.out.println(" ");
            }

            Long workedDays = 365L-suspensionDays;
            Long holidayDaysThisYear = (workedDays * holidayDays)/365;
            Year y = new Year(currentYear,holidayDaysThisYear);
            years.add(y);

            for(Year yr :years){
                System.out.println(yr.toString());
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            ToWrite toWrite = new ToWrite(errors,years);
            mapper.writeValue(new File("C:\\Users\\Luci\\Desktop\\output.json"), toWrite );


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
