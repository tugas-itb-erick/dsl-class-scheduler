package classscheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Course {

    private String id;

    private String name;

    private int credit;
}
