package classscheduler.models;

import classscheduler.models.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DayTime {

    private Day day;

    private List<Integer> times;

    public void addTime(int time) {
        times.add(time);
    }
}
