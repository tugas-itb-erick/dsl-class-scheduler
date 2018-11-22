package classscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Clazz {

    private String name;

    private String courseId;

    private int quota;

    private String lecturerId;
}
