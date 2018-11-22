package classscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Lecturer {

    private String id;

    private String name;

    private List<DayTime> not_preferred_times;

    private List<String> preferred_courses;
}
