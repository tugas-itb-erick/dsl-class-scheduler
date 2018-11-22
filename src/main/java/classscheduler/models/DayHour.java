package classscheduler.models;

import classscheduler.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DayHour {

    private Day day;

    private int time;
}
