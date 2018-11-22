package classscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Timeslot {

    private String courseId;

    private String className;

    private int day;

    private int startTime;

    private int endTime;
}
