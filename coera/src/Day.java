import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Day {
    private int day;
    private int month;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Day(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.setYear(localDate.getYear());
        this.setMonth(localDate.getMonthValue());
        this.setDay(localDate.getDayOfMonth());
    }

    @Override
    public String toString() {
        return "Day{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
