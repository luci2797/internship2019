import java.util.List;

public class ToWrite {
    private String errors;
    private List<Year> holidayRightsPerYearList;

    public ToWrite(String errors, List<Year> years){
        this.errors=errors;
        this.holidayRightsPerYearList = years;
    }
}
