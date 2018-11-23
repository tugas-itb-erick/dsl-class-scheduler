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
public class Course {

    private String id;

    private String name;

    private int credits;

    private List<String> facilities;

    private List<String> constraintCourse;
}
