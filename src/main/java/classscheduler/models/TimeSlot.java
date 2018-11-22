package classscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class TimeSlot {

    private String courseId;

    private String className;

    private DayTime dayTime;
}
