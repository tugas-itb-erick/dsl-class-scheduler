package classscheduler.models;

import classscheduler.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DayHour {

    private Day day;

    private int time;

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return "DayHour{day:" + day.toString() + ",time:" + String.valueOf(time) + "}";
    }
}
