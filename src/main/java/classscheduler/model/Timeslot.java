package classscheduler.model;

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
