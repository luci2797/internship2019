public class Year {
    private int year;
    private Long holidayDays;

    public Year(int year, Long holidayDays){
        this.setYear(year);
        this.setHolidayDays(holidayDays);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(Long holidayDays) {
        this.holidayDays = holidayDays;
    }

    @Override
    public String toString() {
        return "Year{" +
                "year=" + year +
                ", holidayDays=" + holidayDays +
                '}';
    }
}
